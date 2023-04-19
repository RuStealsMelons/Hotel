package cn.xueden.hotel.service.impl;

import cn.xueden.exception.BadRequestException;
import cn.xueden.hotel.domain.*;
import cn.xueden.hotel.repository.HotelCheckinRepository;
import cn.xueden.hotel.repository.HotelMemberRepository;
import cn.xueden.hotel.repository.HotelReserveRepository;
import cn.xueden.hotel.repository.HotelRoomRepository;
import cn.xueden.hotel.service.IHotelReserveService;
import cn.xueden.hotel.service.dto.ReserveQueryCriteria;
import cn.xueden.hotel.vo.CountRoomModel;
import cn.xueden.hotel.vo.HotelReserveModel;
import cn.xueden.utils.PageUtil;
import cn.xueden.utils.QueryHelp;
import cn.xueden.utils.XuedenUtil;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author:梁志杰
 * @date:2023/3/13
 * @description:cn.xueden.hotel.service.impl
 * @version:1.0
 */
@Service
@Transactional(readOnly = true)
public class HotelReserveServiceImpl implements IHotelReserveService {

    private final HotelReserveRepository hotelReserveRepository;

    private final HotelRoomRepository hotelRoomRepository;

    private final HotelMemberRepository hotelMemberRepository;

    private final HotelCheckinRepository hotelCheckinRepository;

    public HotelReserveServiceImpl(HotelReserveRepository hotelReserveRepository, HotelRoomRepository hotelRoomRepository, HotelMemberRepository hotelMemberRepository, HotelCheckinRepository hotelCheckinRepository) {
        this.hotelReserveRepository = hotelReserveRepository;
        this.hotelRoomRepository = hotelRoomRepository;
        this.hotelMemberRepository = hotelMemberRepository;
        this.hotelCheckinRepository = hotelCheckinRepository;
    }

    /**
     * 会员预订房间
     * @param hotelReserve
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addReserve(HotelReserve hotelReserve) {

        // 算出金额
        //预定天数，默认1天
        Long reserveDays = 1L;
        reserveDays = XuedenUtil.LocalDateBetween(hotelReserve.getCheckinDate(),hotelReserve.getCheckoutDate());
        if (reserveDays.intValue()==0){
            throw new BadRequestException("预订失败，入住时间和退房时间不能同一天!");
        }else {

            // 预订状态,待支付
            hotelReserve.setStatus(0);

            // 预订天数
            hotelReserve.setReserveDays(reserveDays);
            // 获取房间信息
            HotelRoom hotelRoom = hotelRoomRepository.getReferenceById(hotelReserve.getRoomId());
            // 算出总费用
            int money = 0;
            money = reserveDays.intValue()*hotelRoom.getMemberPrice();
            hotelReserve.setAmountMoney(money);
            //生成订单号
            hotelReserve.setOrderNumber(XuedenUtil.createOrderNumber());
            hotelReserveRepository.save(hotelReserve);
        }

    }

    /**
     * 根据房间ID获取日期数据
     * @param id
     */
    @Override
    public Set<LocalDate> getDatesByRoomId(Long id) {
        Set<LocalDate> localDateSet = new HashSet<>();
        List<HotelReserve> list = hotelReserveRepository.findAllByRoomId(id);
        list.stream().forEach(item-> {
            XuedenUtil.getMiddleDate(item.getCheckinDate(), item.getCheckoutDate()).stream().forEach(localDate->{
                localDateSet.add(localDate);
            });
        });

        return  localDateSet;

    }

    /**
     * 获取我的预订列表数据
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @Override
    public Object getMyReserveList(ReserveQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelReserve> page = hotelReserveRepository.findAll((root, query, criteriaBuilder)->
                QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        setRoomToReserve(page.stream().toList());
        return PageUtil.toPage(page);
    }

    /**
     * 添加房间信息和会员信息到预订列表
     * @param hotelReserves
     * @return
     */
    private List<HotelReserve> setRoomToReserve(List<HotelReserve> hotelReserves){

        for(HotelReserve r:hotelReserves){

            // 房间信息
            if(r.getRoomId() != null && r.getRoomId() != 0){
                HotelRoom dbHotelRoom = hotelRoomRepository.findById(r.getRoomId()).orElseGet(HotelRoom::new);
                if(StringUtils.isNotBlank(dbHotelRoom.getRoomName())){
                    r.setRoom(dbHotelRoom);
                }
            }
            // 会员信息
            if(r.getCreateBy() != null && r.getCreateBy() != 0){
                HotelMember dbHotelMember = hotelMemberRepository.findById(r.getCreateBy()).orElseGet(HotelMember::new);
                if(StringUtils.isNotBlank(dbHotelMember.getNickname())){
                    r.setMember(dbHotelMember);
                }
            }

        }

        return hotelReserves;
    }

    /**
     * 订单付款
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payment(Long id) {

        // 根据订单ID获取订单信息
        HotelReserve dbHotelReserve = hotelReserveRepository.getReferenceById(id);
        // 根据创建者ID获取会员信息
        HotelMember dbHotelMember = hotelMemberRepository.getReferenceById(dbHotelReserve.getCreateBy());
        // 判断会员余额是否足够付款
        if(dbHotelMember.getBalance()>=dbHotelReserve.getAmountMoney()){
            dbHotelMember.setBalance(dbHotelMember.getBalance()-dbHotelReserve.getAmountMoney());
            dbHotelReserve.setStatus(1);
            hotelReserveRepository.save(dbHotelReserve);
            hotelMemberRepository.save(dbHotelMember);
        }else{
            throw new BadRequestException("付款失败，余额不足，请联系客服充值！");
        }

    }

    /**
     * 获取预订列表数据
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @Override
    public Object getList(ReserveQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelReserve> page = hotelReserveRepository.findAll((root, query, criteriaBuilder)->
                QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        setRoomToReserve(page.stream().toList());
        return PageUtil.toPage(page);
    }

    /**
     * 根据ID获取详情信息
     * @param id
     * @return
     */
    @Override
    public HotelReserve getById(Long id) {
        return hotelReserveRepository.findById(id).orElseGet(HotelReserve::new);
    }

    /**
     * 退订
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unsubscribe(Long id) {
        // 根据ID获取信息
        HotelReserve dbHotelReserve = hotelReserveRepository.findById(id).orElseGet(HotelReserve::new);
        if(dbHotelReserve.getStatus()!=0&&dbHotelReserve.getStatus()!=1){
            throw new BadRequestException("该订单已经完成，请勿重复提交");
        }else {
            //订单状态0表示已预订待付款，1表示已付款代入住,2表示已入住待退房,3表示已取消，4表示已完成5表示已换房
            dbHotelReserve.setStatus(3);
            hotelReserveRepository.save(dbHotelReserve);
        }
    }

    /**
     * 入住酒店
     * @param hotelCheckin
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkIn(HotelCheckin hotelCheckin) {
        // 根据订单号查找信息
        HotelReserve dbHotelReserve = hotelReserveRepository.findByOrderNumber(hotelCheckin.getOrderNumber());
        if(null==dbHotelReserve){
            throw new BadRequestException("订单不存在！");
        }

        // 更新订单状态
        dbHotelReserve.setStatus(2);
        hotelReserveRepository.save(dbHotelReserve);

        // 把记录插入到登记表
        hotelCheckin.setRoomId(dbHotelReserve.getRoomId());
        hotelCheckin.setStatus(0);
        hotelCheckin.setAmountMoney(dbHotelReserve.getAmountMoney());
        hotelCheckin.setCheckinDate(LocalDate.now());
        hotelCheckin.setRemarks("已经登记入住酒店");
        hotelCheckinRepository.save(hotelCheckin);
    }

    /**
     * 统计每天剩余房间数量
     * @return
     */
    @Override
    public List<Map<String,Object>> countReserveRoomByDay() {
        return hotelReserveRepository.countReserveRoomByDay();
    }

    /**
     * 后台预订房间
     * @param reserveModel
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMemberReserve(HotelReserveModel reserveModel) {
        // 根据手机号获取会员信息
        HotelMember dbHotelMember = hotelMemberRepository.findByPhone(reserveModel.getPhone());
        // 说明该手机号没有注册，新增会员信息
        if(dbHotelMember==null){
            dbHotelMember = new HotelMember();
            dbHotelMember.setPassword("123456");
            dbHotelMember.setNickname(reserveModel.getNickname());
            dbHotelMember.setPhone(reserveModel.getPhone());
            dbHotelMember.setRemarks("后台添加");
            dbHotelMember.setStatus(1);
            dbHotelMember = hotelMemberRepository.save(dbHotelMember);
        }
        // 保存记录到预订表
        HotelReserve tempHotelReserve =new HotelReserve();
        // 预订状态,待支付
        tempHotelReserve.setStatus(0);

        // 预订天数
        tempHotelReserve.setReserveDays(1L);

        for (String roomNo:reserveModel.getCheckRoomList()){
            // 获取房间信息
            HotelRoom hotelRoom = hotelRoomRepository.findByRoomNumber(roomNo);
            tempHotelReserve.setRoomId(hotelRoom.getId());
            // 备注
            tempHotelReserve.setRemarks(reserveModel.getRemarks());
            // 创建者id
            tempHotelReserve.setCreateBy(dbHotelMember.getId());
            // 算出总费用
            Long money = 0L;
            money = tempHotelReserve.getReserveDays()*hotelRoom.getStandardPrice();
            tempHotelReserve.setAmountMoney(money.intValue());
            //生成订单号
            tempHotelReserve.setOrderNumber(XuedenUtil.createOrderNumber());
            // 入住时间
            tempHotelReserve.setCheckinDate(reserveModel.getReserveDate());
            // 退房时间
            tempHotelReserve.setCheckoutDate(reserveModel.getReserveDate().plusDays(1));
            hotelReserveRepository.save(tempHotelReserve);
        }
    }

    /**
     * 今日预达人数
     * @return
     */
    @Override
    public long getArriveNums() {
        return hotelReserveRepository.getArriveNums();
    }

    /**
     * 统计今日订单数量
     * @return
     */
    @Override
    public long getOrderNums() {
        return hotelReserveRepository.getOrderNums();
    }

    /**
     * 统计今日营业额
     * @return
     */
    @Override
    public long getTradeNums() {
        return hotelReserveRepository.getTradeNums();
    }



}

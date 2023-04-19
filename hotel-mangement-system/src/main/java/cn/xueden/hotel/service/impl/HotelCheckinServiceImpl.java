package cn.xueden.hotel.service.impl;

import cn.xueden.hotel.domain.HotelCheckin;
import cn.xueden.hotel.domain.HotelMember;
import cn.xueden.hotel.domain.HotelReserve;
import cn.xueden.hotel.domain.HotelRoom;
import cn.xueden.hotel.repository.HotelCheckinRepository;
import cn.xueden.hotel.repository.HotelMemberRepository;
import cn.xueden.hotel.repository.HotelReserveRepository;
import cn.xueden.hotel.repository.HotelRoomRepository;
import cn.xueden.hotel.service.IHotelCheckinService;
import cn.xueden.hotel.service.dto.CheckinQueryCriteria;
import cn.xueden.hotel.vo.BarEchartsSeriesModel;
import cn.xueden.hotel.vo.HotelCheckinModel;
import cn.xueden.utils.PageUtil;
import cn.xueden.utils.QueryHelp;
import cn.xueden.utils.XuedenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**功能描述：入住管理接口实现类
 * @author:梁志杰
 * @date:2023/3/25
 * @description:cn.xueden.hotel.service.impl
 * @version:1.0
 */
@Service
@Transactional(readOnly = true)
public class HotelCheckinServiceImpl implements IHotelCheckinService {

    private final HotelMemberRepository hotelMemberRepository;

    private final HotelCheckinRepository hotelCheckinRepository;

    private final HotelRoomRepository hotelRoomRepository;

    private final HotelReserveRepository hotelReserveRepository;

    public HotelCheckinServiceImpl(HotelMemberRepository hotelMemberRepository, HotelCheckinRepository hotelCheckinRepository, HotelRoomRepository hotelRoomRepository, HotelReserveRepository hotelReserveRepository) {
        this.hotelMemberRepository = hotelMemberRepository;
        this.hotelCheckinRepository = hotelCheckinRepository;
        this.hotelRoomRepository = hotelRoomRepository;
        this.hotelReserveRepository = hotelReserveRepository;
    }

    /**
     * 后台入住登记
     * @param checkinModel
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCheckin(HotelCheckinModel checkinModel) {
        // 根据手机号获取会员信息
        HotelMember dbHotelMember = hotelMemberRepository.findByPhone(checkinModel.getPhone());
        // 说明该手机号没有注册，新增会员信息
        if(dbHotelMember==null){
            dbHotelMember = new HotelMember();
            dbHotelMember.setPassword("123456");
            dbHotelMember.setNickname(checkinModel.getMemberName());
            dbHotelMember.setRealName(checkinModel.getMemberName());
            dbHotelMember.setPhone(checkinModel.getPhone());
            dbHotelMember.setRemarks("后台添加");
            dbHotelMember.setStatus(1);
            dbHotelMember = hotelMemberRepository.save(dbHotelMember);
        }
        // 保存记录到入住表
        HotelCheckin tempHotelCheckin =new HotelCheckin();
        // 入住状态0表示已入住未退房，1表示已退房
        tempHotelCheckin.setStatus(0);
        // 入住人数
        tempHotelCheckin.setMemberNumber(checkinModel.getMemberNumber());

        // 保存记录到预订表
        HotelReserve tempHotelReserve =new HotelReserve();
        // 预订状态,2表示已入住待退房
        tempHotelReserve.setStatus(2);

        // 预订天数
        tempHotelReserve.setReserveDays(1L);

        for (String roomNo:checkinModel.getCheckRoomList()){
            // 订单号
            String OrderNumber =  XuedenUtil.createOrderNumber();
            // 获取房间信息
            HotelRoom hotelRoom = hotelRoomRepository.findByRoomNumber(roomNo);
            tempHotelCheckin.setRoomId(hotelRoom.getId());

            // 保存记录到入住表
            // 备注
            tempHotelCheckin.setRemarks(checkinModel.getRemarks());
            // 创建者id
            tempHotelCheckin.setCreateBy(dbHotelMember.getId());
            // 费用
            tempHotelCheckin.setAmountMoney(hotelRoom.getStandardPrice());
            //生成订单号
            tempHotelCheckin.setOrderNumber(OrderNumber);
            // 入住时间
            tempHotelCheckin.setCheckinDate(checkinModel.getCheckinDate());
            // 退房时间
            tempHotelCheckin.setCheckoutDate(checkinModel.getCheckinDate().plusDays(1));
            hotelCheckinRepository.save(tempHotelCheckin);

            // 保存记录到预订表
            tempHotelReserve.setRoomId(hotelRoom.getId());
            // 备注
            tempHotelReserve.setRemarks(checkinModel.getRemarks());
            // 创建者id
            tempHotelReserve.setCreateBy(dbHotelMember.getId());
            // 算出总费用
            tempHotelReserve.setAmountMoney(hotelRoom.getStandardPrice());
            //生成订单号
            tempHotelReserve.setOrderNumber(OrderNumber);
            // 入住时间
            tempHotelReserve.setCheckinDate(checkinModel.getCheckinDate());
            // 退房时间
            tempHotelReserve.setCheckoutDate(checkinModel.getCheckinDate().plusDays(1));
            hotelReserveRepository.save(tempHotelReserve);

        }

    }

    /**
     * 统计今日预离人数
     * @return
     */
    @Override
    public long getLeaveNums() {
        return hotelCheckinRepository.getLeaveNums();
    }

    /**
     * 近七天入住人数
     * @return
     */
    @Override
    public HashMap<String, Object> getSevenDaysOccupancy() {
        List<BarEchartsSeriesModel> barEchartsSeriesList = new ArrayList<>();
        // 横坐标
        List<String> categoryList = new ArrayList<>();
        LocalDate date = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            date.plusDays(-i);
            categoryList.add(date.plusDays(-i).toString());
            System.out.println("日期："+date.plusDays(-i));
        }

       // 入住人数
        List<Integer> checkinList = new ArrayList<>();
        List<Map<String,Object>> SevenDaysOccupancyMap = hotelCheckinRepository.getSevenDaysOccupancy();
        categoryList.forEach(k-> {
          List<Map<String,Object>> result =  SevenDaysOccupancyMap.stream().filter(v->{
                return k.equals(v.get("checkinDate"));
            }).collect(Collectors.toList());

          if(result.size()>0){
              BigDecimal memberNumber = (BigDecimal) result.get(0).get("memberNumber");
              checkinList.add(memberNumber.intValue());
          }else {
              checkinList.add(0);
          }


        });


        BarEchartsSeriesModel sumBarEchartsSeriesModel = new BarEchartsSeriesModel();
        sumBarEchartsSeriesModel.setData(checkinList);
        sumBarEchartsSeriesModel.setType("bar");
        sumBarEchartsSeriesModel.setName("入住人数");
        barEchartsSeriesList.add(sumBarEchartsSeriesModel);
        // 定义返回对象
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("categoryList",categoryList);
        resultMap.put("barEchartsSeriesList",barEchartsSeriesList);

        return resultMap;
    }

    /**
     * 获取入住列表数据
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @Override
    public Object getList(CheckinQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelCheckin> page = hotelCheckinRepository.findAll((root, query, criteriaBuilder)->
                QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        setRoomToCheckin(page.stream().toList());
        return PageUtil.toPage(page);
    }

    /**
     * 添加房间信息到入住列表
     * @param hotelCheckins
     * @return
     */
    private List<HotelCheckin> setRoomToCheckin(List<HotelCheckin> hotelCheckins){

        for(HotelCheckin r:hotelCheckins){

            // 房间信息
            if(r.getRoomId() != null && r.getRoomId() != 0){
                HotelRoom dbHotelRoom = hotelRoomRepository.findById(r.getRoomId()).orElseGet(HotelRoom::new);
                if(StringUtils.isNotBlank(dbHotelRoom.getRoomName())){
                    r.setRoom(dbHotelRoom);
                }
            }


        }

        return hotelCheckins;
    }

    /**
     * 退房
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkOut(Long id) {
        hotelRoomRepository.updateStatusById(id);
    }
}

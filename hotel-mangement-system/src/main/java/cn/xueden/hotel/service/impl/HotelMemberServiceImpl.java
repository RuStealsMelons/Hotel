package cn.xueden.hotel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.xueden.exception.BadRequestException;
import cn.xueden.hotel.domain.HotelMember;

import cn.xueden.hotel.domain.HotelRechargeRecord;
import cn.xueden.hotel.repository.HotelMemberRepository;
import cn.xueden.hotel.repository.HotelRechargeRecordRepository;
import cn.xueden.hotel.service.IHotelMemberService;
import cn.xueden.hotel.service.dto.MemberQueryCriteria;
import cn.xueden.hotel.vo.MemberPwdModel;
import cn.xueden.utils.PageUtil;
import cn.xueden.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**功能描述：会员接口实现类
 * @author:梁志杰
 * @date:2023/3/11
 * @description:cn.xueden.hotel.service.impl
 * @version:1.0
 */
@Service
@Transactional(readOnly = true)
public class HotelMemberServiceImpl implements IHotelMemberService {

    private final HotelMemberRepository hotelMemberRepository;

    private final HotelRechargeRecordRepository rechargeRecordRepository;

    public HotelMemberServiceImpl(HotelMemberRepository hotelMemberRepository, HotelRechargeRecordRepository rechargeRecordRepository) {
        this.hotelMemberRepository = hotelMemberRepository;
        this.rechargeRecordRepository = rechargeRecordRepository;
    }

    /**
     * 会员注册
     * @param hotelMember
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(HotelMember hotelMember) {
        // 判断该手机号是否已经注册
        HotelMember dbHotelMember = hotelMemberRepository.findByPhone(hotelMember.getPhone());
        if(dbHotelMember!=null){
            throw new BadRequestException("注册失败，该手机号已经注册过了");
        }else {
            // 初始化数据
            hotelMember.setStatus(1);
            hotelMember.setLoginTimes(0);
            hotelMemberRepository.save(hotelMember);
        }
    }

    /**
     * 会员登录
     * @param hotelMember
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HotelMember login(HotelMember hotelMember) {
        // 根据手机号获取会员信息
        HotelMember dbHotelMember = hotelMemberRepository.findByPhone(hotelMember.getPhone());
        if(dbHotelMember==null){
            throw new BadRequestException("登录失败，手机号:"+hotelMember.getPhone()+"未注册!");
        } else if (!dbHotelMember.getPassword().equals(hotelMember.getPassword())) {
            throw new BadRequestException("登录失败，输入密码不正确!");
        }
        // 更新登录次数
        dbHotelMember.setLoginTimes(dbHotelMember.getLoginTimes()+1);
        hotelMemberRepository.save(dbHotelMember);
        return dbHotelMember;
    }

    /**
     * 获取登录会员信息
     * @param memberId
     * @return
     */
    @Override
    public HotelMember getLoginMemberInfo(Long memberId) {
        return hotelMemberRepository.findById(memberId).orElseGet(HotelMember::new);
    }

    /**
     * 更新会员信息
     * @param hotelMember
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMember(HotelMember hotelMember) {
        HotelMember dbHotelMember = hotelMemberRepository.getReferenceById(hotelMember.getId());
        BeanUtil.copyProperties(hotelMember,dbHotelMember, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        hotelMemberRepository.save(dbHotelMember);
    }

    /**
     * 修改密码
     * @param memberPwdModel
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMemberPwd(MemberPwdModel memberPwdModel) {
        HotelMember dbHotelMember = hotelMemberRepository.getReferenceById(memberPwdModel.getMemberId());
        if(!dbHotelMember.getPassword().equals(memberPwdModel.getOldPassword())){
            throw new BadRequestException("修改密码失败，旧密码不正确");
        }else {
            dbHotelMember.setPassword(memberPwdModel.getNewPassword());
            hotelMemberRepository.save(dbHotelMember);
        }
    }

    /**
     * 获取会员列表数据
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @Override
    public Object getList(MemberQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelMember> page = hotelMemberRepository.findAll((root, query, criteriaBuilder)->
                QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page);
    }

    /**
     * 根据ID获取详情信息
     * @param id
     * @return
     */
    @Override
    public HotelMember getById(Long id) {
        return hotelMemberRepository.findById(id).orElseGet(HotelMember::new);
    }

    /**
     * 会员充值
     * @param member
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recharge(HotelMember member) {
        // 先获取会员信息
        HotelMember dbMember = hotelMemberRepository.getReferenceById(member.getId());
        if(dbMember==null){
            throw new BadRequestException("充值失败");
        }else{
            dbMember.setBalance(dbMember.getBalance()==null?member.getBalance():dbMember.getBalance()+member.getBalance());
            hotelMemberRepository.save(dbMember);
            // 保存到充值记录表
            HotelRechargeRecord tempRechargeRecord = new HotelRechargeRecord();
            tempRechargeRecord.setMoney(member.getBalance());
            tempRechargeRecord.setRemarks(member.getRemarks());
            tempRechargeRecord.setMemberId(member.getId());
            rechargeRecordRepository.save(tempRechargeRecord);

        }
    }

    /**
     * 根据ID删除会员信息
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        hotelMemberRepository.deleteById(id);
    }

}

package cn.xueden.hotel.service;

import cn.xueden.hotel.domain.HotelMember;
import cn.xueden.hotel.service.dto.MemberQueryCriteria;
import cn.xueden.hotel.vo.MemberPwdModel;
import org.springframework.data.domain.Pageable;

/**功能描述：会员接口
 * @author:梁志杰
 * @date:2023/3/11
 * @description:cn.xueden.hotel.service
 * @version:1.0
 */
public interface IHotelMemberService {

    /**
     * 会员注册
     * @param hotelMember
     */
    void register(HotelMember hotelMember);

    /**
     * 会员登录
     * @param hotelMember
     */
    HotelMember login(HotelMember hotelMember);

    /**
     * 获取登录会员信息
     * @param memberId
     * @return
     */
    HotelMember getLoginMemberInfo(Long memberId);

    /**
     * 更新会员信息
     * @param hotelMember
     */
    void updateMember(HotelMember hotelMember);

    /**
     * 修改会员密码
     * @param memberPwdModel
     */
    void updateMemberPwd(MemberPwdModel memberPwdModel);

    /**
     * 获取会员列表数据
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Object getList(MemberQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 根据ID获取详情信息
     * @param id
     * @return
     */
    HotelMember getById(Long id);

    /**
     * 会员充值
     * @param member
     * @return
     */
    void recharge(HotelMember member);

    /**
     * 根据ID删除会员信息
     * @param id
     */
    void deleteById(Long id);
}

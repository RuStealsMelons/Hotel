package cn.xueden.hotel.vo;

import lombok.Data;

/**功能描述：会员修改密码对象
 * @author:梁志杰
 * @date:2023/3/16
 * @description:cn.xueden.hotel.vo
 * @version:1.0
 */
@Data
public class MemberPwdModel {

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 会员ID
     */
    private Long memberId;
}

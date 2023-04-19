package cn.xueden.hotel.service.dto;

import cn.xueden.annotation.EnableXuedenQuery;
import lombok.Data;

/**功能描述：会员查询条件类
 * @author:梁志杰
 * @date:2023/3/18
 * @description:cn.xueden.hotel.service.dto
 * @version:1.0
 */
@Data
public class MemberQueryCriteria {

    /**
     * 根据会员邮箱、手机号、真实姓名、昵称模糊查询
     */
    @EnableXuedenQuery(blurry = "email,phone,realName,nickname")
    private String searchValue;
}

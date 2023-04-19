package cn.xueden.hotel.service.dto;

import cn.xueden.annotation.EnableXuedenQuery;
import lombok.Data;

/**功能描述：入住信息查询条件
 * @author:梁志杰
 * @date:2023/3/27
 * @description:cn.xueden.hotel.service.dto
 * @version:1.0
 */
@Data
public class CheckinQueryCriteria {

    /**
     * 根据入住姓名、订单号、手机号、身份证等模糊查询
     */
    @EnableXuedenQuery(blurry = "orderNumber,memberName,phone,idCard")
    private String searchValue;

    /**
     * 根据入住状态查询
     */
    @EnableXuedenQuery
    private Integer status;

}

package cn.xueden.hotel.service.dto;

import cn.xueden.annotation.EnableXuedenQuery;
import lombok.Data;

/**功能描述：我的预订查询条件
 * @author:梁志杰
 * @date:2023/3/15
 * @description:cn.xueden.hotel.service.dto
 * @version:1.0
 */
@Data
public class ReserveQueryCriteria {

    @EnableXuedenQuery
    private Long createBy;

    /**
     * 根据订单号模糊查询
     */
    @EnableXuedenQuery(blurry = "orderNumber")
    private String searchValue;

    /**
     * 根据订单状态查询
     */
    @EnableXuedenQuery
    private Integer status;
}

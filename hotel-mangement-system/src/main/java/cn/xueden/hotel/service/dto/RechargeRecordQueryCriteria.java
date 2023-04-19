package cn.xueden.hotel.service.dto;

import cn.xueden.annotation.EnableXuedenQuery;
import lombok.Data;

/**功能描述：充值记录查询条件
 * @author:梁志杰
 * @date:2023/3/28
 * @description:cn.xueden.hotel.service.dto
 * @version:1.0
 */
@Data
public class RechargeRecordQueryCriteria {

    @EnableXuedenQuery
    private Long memberId;
}

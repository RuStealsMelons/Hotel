package cn.xueden.hotel.service;

import cn.xueden.hotel.service.dto.RechargeRecordQueryCriteria;
import org.springframework.data.domain.Pageable;

/**功能描述：充值记录业务接口
 * @author:梁志杰
 * @date:2023/3/28
 * @description:cn.xueden.hotel.service
 * @version:1.0
 */
public interface IHotelRechargeRecordService {
    /**
     * 获取我的充值记录
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Object getMyRechargeRecordList(RechargeRecordQueryCriteria queryCriteria, Pageable pageable);
}

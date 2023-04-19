package cn.xueden.hotel.service;

import cn.xueden.hotel.service.dto.CheckinQueryCriteria;
import cn.xueden.hotel.vo.HotelCheckinModel;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

/**功能描述：入住管理接口
 * @author:梁志杰
 * @date:2023/3/25
 * @description:cn.xueden.hotel.service
 * @version:1.0
 */
public interface IHotelCheckinService {
    /**
     * 后台入住登记
     * @param checkinModel
     */
    void addCheckin(HotelCheckinModel checkinModel);

    /**
     * 今日预离人数
     * @return
     */
    long getLeaveNums();

    /**
     * 近七天入住人数
     * @return
     */
    HashMap<String, Object> getSevenDaysOccupancy();

    /**
     * 获取入住列表数据
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Object getList(CheckinQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 退房
     * @param id
     */
    void checkOut(Long id);
}

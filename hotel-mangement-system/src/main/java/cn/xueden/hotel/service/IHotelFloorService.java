package cn.xueden.hotel.service;

import cn.xueden.hotel.domain.HotelFloor;
import cn.xueden.hotel.service.dto.FloorQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**功能描述：楼层接口
 * @author:梁志杰
 * @date:2023/3/10
 * @description:cn.xueden.hotel.service
 * @version:1.0
 */
public interface IHotelFloorService {
    /**
     * 获取所有楼层列表
     * @return
     */
    List<HotelFloor> getAll();

    /**
     * 获取楼层列表数据
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Object getList(FloorQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 添加楼层信息
     * @param hotelFloor
     * @return
     */
    boolean addFloor(HotelFloor hotelFloor);

    /**
     * 根据ID获取详情信息
     * @param id
     * @return
     */
    HotelFloor getById(Long id);

    /**
     * 更新楼层信息
     * @param hotelFloor
     */
    void editFloor(HotelFloor hotelFloor);

    /**
     * 根据ID删除楼层信息
     * @param id
     */
    void deleteById(Long id);
}

package cn.xueden.hotel.service;

import cn.xueden.hotel.domain.HotelRoomType;
import cn.xueden.hotel.service.dto.RoomTypeQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**功能描述：房间类型接口
 * @author:梁志杰
 * @date:2023/3/10
 * @description:cn.xueden.hotel.service
 * @version:1.0
 */
public interface IHotelRoomTypeService {

    /**
     * 获取所有房间类型数据
     * @return
     */
    List<HotelRoomType> getAll();

    /**
     * 获取房间类型列表
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Object getList(RoomTypeQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 添加房间类型信息
     * @param hotelRoomType
     * @return
     */
    boolean addRoomType(HotelRoomType hotelRoomType);

    /**
     * 根据ID获取详情信息
     * @param id
     * @return
     */
    HotelRoomType getById(Long id);

    /**
     * 更新房间类型信息
     * @param hotelRoomType
     */
    void editRoomType(HotelRoomType hotelRoomType);

    /**
     * 删除房间类型信息
     * @param id
     */
    void deleteById(Long id);

}

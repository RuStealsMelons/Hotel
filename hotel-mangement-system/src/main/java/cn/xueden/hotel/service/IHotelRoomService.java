package cn.xueden.hotel.service;

import cn.xueden.hotel.domain.HotelRoom;
import cn.xueden.hotel.service.dto.RoomQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**功能描述：酒店房间接口
 * @author:梁志杰
 * @date:2023/3/10
 * @description:cn.xueden.hotel.service
 * @version:1.0
 */
public interface IHotelRoomService {

    /**
     * 根据楼层ID获取房间列表
     * @param id
     * @return
     */
    List<HotelRoom> getRoomListByfloorId(Long id,Integer num);

    /**
     * 根据房间ID获取房间信息
     * @param id
     * @return
     */
    HotelRoom getById(Long id);

    /**
     * 获取房间列表
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Object getList(RoomQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 添加房间信息
     * @param hotelRoom
     * @return
     */
    boolean addRoom(HotelRoom hotelRoom);

    /**
     * 更新房间信息
     * @param hotelRoom
     */
    void editRoom(HotelRoom hotelRoom);

    /**
     * 根据ID删除房间信息
     * @param id
     */
    void deleteById(Long id);

    /**
     * 统计房间数量
     * @return
     */
    long count();

    /**
     * 根据指定日期获取所有空闲房间
     * @param nowDate
     */
    List<Map<String,Object>> getRoomListByDate(String nowDate);
}

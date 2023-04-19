package cn.xueden.hotel.repository;

import cn.xueden.hotel.domain.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * 酒店房间持久层
 * @author Administrator
 */
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long>, JpaSpecificationExecutor<HotelRoom> {

    /**
     * 根据楼层ID获取房间列表
     * @param floorId
     *        楼层ID
     * @param num
     *        数量
     * @return
     */
    @Query(value="select * from hotel_room where floor_id = ?1 LIMIT ?2",nativeQuery = true)
    List<HotelRoom> getRoomListByfloorId(Long floorId,Integer num);

    /**
     * 根据房间号查询记录
     * @param roomNumber
     * @return
     */
    HotelRoom findByRoomNumber(String roomNumber);

    /**
     * 根据指定日期获取所有空闲房间
     * @param nowDate
     * @return
     */

    @Query(value = "SELECT room_number AS rommNumber,standard_price AS standardPrice FROM hotel_room WHERE id NOT IN (SELECT\n" +
            "\troom_id\n" +
            "FROM\n" +
            "\thotel_reserve\n" +
            "WHERE\n" +
            "   DATE_FORMAT(checkin_date,'%Y-%m-%d') = ?1)",nativeQuery = true)
    List<Map<String, Object>> getRoomListByDate(String nowDate);

    /**
     * 退房
     * @param id
     */
    @Query(value = "update HotelCheckin set status=1 where id=?1")
    @Modifying
    void updateStatusById(Long id);
}
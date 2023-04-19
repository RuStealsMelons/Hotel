package cn.xueden.hotel.repository;

import cn.xueden.hotel.domain.HotelReserve;
import cn.xueden.hotel.vo.CountRoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * 酒店约定持久层
 * @author Administrator
 */
public interface HotelReserveRepository extends JpaRepository<HotelReserve, Long>, JpaSpecificationExecutor<HotelReserve> {

    /**
     * 根据房间ID获取预订数据
     * @param id
     * @return
     */
    List<HotelReserve> findAllByRoomId(Long id);

    /**
     * 根据订单号查询信息
     * @param orderNumber
     * @return
     */
    HotelReserve findByOrderNumber(String orderNumber);

    /**
     * 统计每天剩余房间数量
     * @return
     */
    @Query(value = "SELECT ((SELECT count(*) FROM hotel_room) - COUNT(*)) AS 'countRoom',DATE(checkin_date) AS 'reserveDay' FROM hotel_reserve GROUP BY DATE(checkin_date)",nativeQuery = true)
    List<Map<String,Object>> countReserveRoomByDay();

    /**
     * 今日预达人数
     * @return
     */
    @Query(value = "SELECT count(*) FROM hotel_reserve WHERE status=1 and to_days(checkin_date) = to_days(now())",nativeQuery = true)
    long getArriveNums();

    /**
     * 统计今日订单数量
     * @return
     */
    @Query(value = "SELECT count(*) FROM hotel_reserve WHERE to_days(create_time) = to_days(now())",nativeQuery = true)
    long getOrderNums();

    /**
     * 统计今日营业额
     * @return
     */
    @Query(value = "SELECT IFNULL(SUM(amount_money),0)  as amountMoney FROM hotel_reserve WHERE status=1 and to_days(create_time) = to_days(now())",nativeQuery = true)
    long getTradeNums();
}
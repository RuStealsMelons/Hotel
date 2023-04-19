package cn.xueden.hotel.repository;

import cn.xueden.hotel.domain.HotelCheckin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登记入住持久层
 * @author Administrator
 */
public interface HotelCheckinRepository extends JpaRepository<HotelCheckin, Long>, JpaSpecificationExecutor<HotelCheckin> {

    /**
     * 今日预离人数
     * @return
     */
    @Query(value = "SELECT count(*) FROM hotel_checkin WHERE status=0 and to_days(checkout_date) = to_days(now()) ",nativeQuery = true)
    long getLeaveNums();

    /**
     * 近七天入住人数
     * @return
     */
    @Query(value = "SELECT DATE_FORMAT(checkin_date,'%Y-%m-%d') as checkinDate,IFNULL(sum(member_number),0) as memberNumber FROM hotel_checkin WHERE date_sub(curdate(), interval 7 day) <= checkin_date GROUP BY checkin_date",nativeQuery = true)
    List<Map<String, Object>> getSevenDaysOccupancy();
}
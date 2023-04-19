package cn.xueden.hotel.repository;

import cn.xueden.hotel.domain.HotelFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 功能描述：酒店楼层持久层
 * @author Administrator
 */
public interface HotelFloorRepository extends JpaRepository<HotelFloor, Long>, JpaSpecificationExecutor<HotelFloor> {
    /**
     * 根据楼层号查询楼层信息
     * @param floorNo
     * @return
     */
    HotelFloor findByFloorNo(Integer floorNo);
}
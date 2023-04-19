package cn.xueden.hotel.repository;

import cn.xueden.hotel.domain.HotelRoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 功能描述：房间类型持久层
 * @author Administrator
 */
public interface HotelRoomTypeRepository extends JpaRepository<HotelRoomType, Long>, JpaSpecificationExecutor<HotelRoomType> {
}
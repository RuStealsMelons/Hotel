package cn.xueden.hotel.repository;

import cn.xueden.hotel.domain.HotelRechargeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 功能描述：充值记录持久层
 * @author Administrator
 */
public interface HotelRechargeRecordRepository extends JpaRepository<HotelRechargeRecord, Long>, JpaSpecificationExecutor<HotelRechargeRecord> {
}
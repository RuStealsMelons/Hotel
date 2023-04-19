package cn.xueden.hotel.repository;

import cn.xueden.hotel.domain.HotelMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 酒店会员持久层
 * @author Administrator
 */
public interface HotelMemberRepository extends JpaRepository<HotelMember, Long>, JpaSpecificationExecutor<HotelMember> {
    /**
     * 根据手机号获取数据
     * @param phone
     * @return
     */
    HotelMember findByPhone(String phone);
}
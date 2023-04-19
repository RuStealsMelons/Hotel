package cn.xueden.hotel.domain;

import cn.xueden.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

/**功能描述：充值记录表
 * @author:梁志杰
 * @date:2023/3/18
 * @description:cn.xueden.hotel.domain
 * @version:1.0
 */
@Entity
@Data
@Table(name = "hotel_recharge_record")
@org.hibernate.annotations.Table(appliesTo = "hotel_recharge_record",comment = "充值记录表")
public class HotelRechargeRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /**
     * 会员ID
     */
    @Column(name = "member_id",nullable = false)
    private Long memberId;

    /**
     * 充值金额
     */
    @Column(name = "money",nullable = false)
    private Integer money;
}

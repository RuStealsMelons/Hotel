package cn.xueden.hotel.domain;

import cn.xueden.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


/**功能描述：酒店预订实体类
 * @author:梁志杰
 * @date:2023/3/10
 * @description:cn.xueden.hotel.domain
 * @version:1.0
 */
@Entity
@Data
@Table(name = "hotel_reserve")
@org.hibernate.annotations.Table(appliesTo = "hotel_reserve",comment = "酒店预订信息表")
public class HotelReserve extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /**
     * 订单号
     */
    @Column(name = "order_number",nullable = false)
    private String orderNumber;

    /**
     * 房间id
     */
    @Column(name = "room_id",nullable = false)
    private Long roomId;
    /**
     * 订单状态
     */
    @Column(name = "status",nullable = false)
    private Integer status;

    /**
     * 入住时间
     */
    @Column(name = "checkin_date")
    private LocalDate checkinDate;

    /**
     * 退房时间
     */
    @Column(name = "checkout_date")
    private LocalDate checkoutDate;

    /**
     * 预定天数
     */
    @Column(name = "reserve_days",nullable = false)
    private Long reserveDays;

    /**
     * 总金额
     */
    @Column(name = "amount_money",nullable = false)
    private Integer amountMoney;

    /**
     * 酒店房间
     */
    @Transient
    private HotelRoom room;

    /**
     * 会员信息
     */
    @Transient
    private HotelMember member;
}

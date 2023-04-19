package cn.xueden.hotel.domain;

import cn.xueden.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**功能描述：入住登记实体类
 * @author:梁志杰
 * @date:2023/3/10
 * @description:cn.xueden.hotel.domain
 * @version:1.0
 */
@Entity
@Data
@Table(name = "hotel_checkin")
@org.hibernate.annotations.Table(appliesTo = "hotel_checkin",comment = "入住登记信息表")
public class HotelCheckin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /**
     * 订单号
     */
    @Column(name = "order_number")
    private String orderNumber;

    /**
     * 房间ID
     */
    @Column(name = "room_id")
    private Long roomId;

    /**
     * 会员姓名
     */
    @Column(name = "member_name")
    private String memberName;

    /**
     * 身份证
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 入住人数
     */
    @Column(name = "member_number")
    private Integer memberNumber;

    /**
     * 出生日期
     */
    @Column(name = "birthday")
    private String birthday;

    /**
     * 性别
     */
    @Column(name = "gender")
    private String gender;

    /**
     * 省份
     */
    @Column(name = "province")
    private String province;

    /**
     * 城市
     */
    @Column(name = "city")
    private String city;

    /**
     * 家庭住址
     */
    @Column(name = "address")
    private String address;

    /**
     * 手机号
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 入住状态0表示已入住未退房，1表示已退房
     */
    @Column(name = "status")
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
     * 金额
     */
    @Column(name = "amount_money",nullable = false)
    private Integer amountMoney;

    /**
     * 酒店房间
     */
    @Transient
    private HotelRoom room;
}

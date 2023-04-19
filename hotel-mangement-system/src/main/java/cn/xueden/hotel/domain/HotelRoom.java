package cn.xueden.hotel.domain;

import cn.xueden.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

/**功能描述：酒店房间实体类
 * @author:梁志杰
 * @date:2023/3/10
 * @description:cn.xueden.hotel.domain
 * @version:1.0
 */
@Entity
@Data
@Table(name = "hotel_room")
@org.hibernate.annotations.Table(appliesTo = "hotel_room",comment = "酒店房间信息表")
public class HotelRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /**
     * 房间名称
     */
    @Column(name = "room_name",nullable = false)
    private String roomName;

    /**
     * 房间号
     */
    @Column(name = "room_number",nullable = false)
    private String roomNumber;

    /**
     * 床型
     */
    @Column(name = "bed_type",nullable = false)
    private String bedType;

    /**
     * 宽带
     */
    @Column(name = "broadband",nullable = false)
    private String broadband;

    /**
     * 标准价
     */
    @Column(name = "standard_price",nullable = false)
    private Integer standardPrice;

    /**
     * 会员价
     */
    @Column(name = "member_price",nullable = false)
    private Integer memberPrice;

    /**
     * 房间状态
     */
    @Column(name = "room_status",nullable = false)
    private Integer roomStatus;

    /**
     * 房间类型
     */
    @Column(name = "room_type_id",nullable = false)
    private Long roomTypeId;

    /**
     * 所属楼层
     */
    @Column(name = "floor_id",nullable = false)
    private Long floorId;

    /**
     * 房间封面图片
     */
    @Column(name = "cover_img",nullable = false)
    private String coverImg;

    /**
     * 楼层对象
     */
    @Transient
    private HotelFloor hotelFloor;

    /**
     * 房间类型对象
     */
    @Transient
    private HotelRoomType hotelRoomType;


}

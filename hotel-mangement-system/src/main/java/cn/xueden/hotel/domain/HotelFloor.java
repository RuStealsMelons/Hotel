package cn.xueden.hotel.domain;

import cn.xueden.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**功能描述：酒店楼层实体类
 * @author:梁志杰
 * @date:2023/3/10
 * @description:cn.xueden.hotel.domain
 * @version:1.0
 */
@Entity
@Data
@Table(name = "hotel_floor")
@org.hibernate.annotations.Table(appliesTo = "hotel_floor",comment = "酒店楼层信息表")
public class HotelFloor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /**
     * 楼层
     */
    @Column(name = "floor_no",nullable = false)
    private Integer floorNo;

    /**
     * 楼层名称
     */
    @Column(name = "floor_name",nullable = false)
    private String floorName;

    /**
     * 酒店房间列表
     */
    @Transient
    private List<HotelRoom> roomList;
}

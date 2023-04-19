package cn.xueden.hotel.vo;

import lombok.Data;

import java.time.LocalDate;

/**功能描述：后台入住房间对象
 * @author:梁志杰
 * @date:2023/3/25
 * @description:cn.xueden.hotel.vo
 * @version:1.0
 */
@Data
public class HotelCheckinModel {

    private String memberName;

    private String phone;

    private String idCard;

    private Integer memberNumber;

    private String gender;

    private String province;

    private String city;

    private String address;

    private String remarks;

    private LocalDate checkinDate;

    private String[] checkRoomList;
}

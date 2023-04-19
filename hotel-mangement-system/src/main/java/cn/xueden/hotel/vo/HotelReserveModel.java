package cn.xueden.hotel.vo;

import lombok.Data;

import java.time.LocalDate;

/**功能描述：后台预订房间对象
 * @author:梁志杰
 * @date:2023/3/23
 * @description:cn.xueden.hotel.vo
 * @version:1.0
 */
@Data
public class HotelReserveModel {

    private String nickname;

    private String phone;

    private String remarks;

    private LocalDate reserveDate;

    private String[] checkRoomList;
}

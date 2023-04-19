package cn.xueden.hotel.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**功能描述：每天预定房间数量
 * @author:梁志杰
 * @date:2023/3/22
 * @description:cn.xueden.hotel.vo
 * @version:1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountRoomModel {

    /**
     * 数量
     */
    private Integer countRoom;

    /**
     * 日期
     */
    private Date reserveDay;
}

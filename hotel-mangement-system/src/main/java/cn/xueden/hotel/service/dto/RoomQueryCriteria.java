package cn.xueden.hotel.service.dto;

import cn.xueden.annotation.EnableXuedenQuery;
import lombok.Data;

/**酒店房间查询参数
 * @author:梁志杰
 * @date:2023/3/13
 * @description:cn.xueden.hotel.service.dto
 * @version:1.0
 */
@Data
public class RoomQueryCriteria {
    /**
     * 根据房间号、房间名称模糊查询
     */
    @EnableXuedenQuery(blurry = "roomNumber,roomName")
    private String searchValue;

    /**
     * 根据楼层ID查询
     */
    @EnableXuedenQuery
    private Long floorId;

    /**
     * 根据房间类型ID查询
     */
    @EnableXuedenQuery
    private Long roomTypeId;
}

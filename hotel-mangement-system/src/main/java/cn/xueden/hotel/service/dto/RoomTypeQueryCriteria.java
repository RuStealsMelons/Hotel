package cn.xueden.hotel.service.dto;

import cn.xueden.annotation.EnableXuedenQuery;
import lombok.Data;

/**功能描述：房间类型查询条件类
 * @author:梁志杰
 * @date:2023/3/16
 * @description:cn.xueden.hotel.service.dto
 * @version:1.0
 */
@Data
public class RoomTypeQueryCriteria {

    /**
     * 根据房间类型名称模糊查询
     */
    @EnableXuedenQuery(blurry = "typeName")
    private String searchValue;
}

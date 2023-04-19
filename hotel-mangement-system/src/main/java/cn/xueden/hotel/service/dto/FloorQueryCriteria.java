package cn.xueden.hotel.service.dto;

import cn.xueden.annotation.EnableXuedenQuery;
import lombok.Data;

/**功能描述：楼层条件查询类
 * @author:梁志杰
 * @date:2023/3/17
 * @description:cn.xueden.hotel.service.dto
 * @version:1.0
 */
@Data
public class FloorQueryCriteria {

    /**
     * 根据楼层名称、楼层模糊查询
     */
    @EnableXuedenQuery(blurry = "floorNo,floorName")
    private String searchValue;
}

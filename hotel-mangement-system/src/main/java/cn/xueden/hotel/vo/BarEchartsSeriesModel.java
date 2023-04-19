package cn.xueden.hotel.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**功能描述：柱形图返回结果集对象
 * @author:梁志杰
 * @date:2023/3/1
 * @description:cn.xueden.student.vo
 * @version:1.0
 */
@Data
public class BarEchartsSeriesModel {

    private List<Integer> data;

    private String type;

    private String name;
}

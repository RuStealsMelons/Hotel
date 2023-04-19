package cn.xueden.hotel.controller;

import cn.xueden.base.BaseResult;
import cn.xueden.hotel.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**功能描述：后台首页前端控制器
 * @author:梁志杰
 * @date:2023/3/2
 * @description:cn.xueden.student.controller
 * @version:1.0
 */
@RestController
@RequestMapping("home")
public class HotelHomeController {

   private final IHotelReserveService hotelReserveService;

   private final IHotelCheckinService hotelCheckinService;

    public HotelHomeController(IHotelReserveService hotelReserveService, IHotelCheckinService hotelCheckinService) {
        this.hotelReserveService = hotelReserveService;
        this.hotelCheckinService = hotelCheckinService;
    }

    /**
     * 后台首页统计
     * @return
     */
    @GetMapping
    public BaseResult getIndexTotal(){
        Map<String,Object> resultMap = new HashMap<>();
        // 今日预达人数
       long arriveNums = hotelReserveService.getArriveNums();
       resultMap.put("arriveNums",arriveNums);
       // 今日预离人数
       long leaveNums = hotelCheckinService.getLeaveNums();
        resultMap.put("leaveNums",leaveNums);

        // 今日订单数量
        long orderNums = hotelReserveService.getOrderNums();
        resultMap.put("orderNums",orderNums);

        // 统计今日营业额
        long tradeNums = hotelReserveService.getTradeNums();
        resultMap.put("tradeNums",tradeNums);

        // 统计近七天入住人数
        HashMap<String,Object> checkinNumMap = hotelCheckinService.getSevenDaysOccupancy();
        resultMap.put("checkinNum",checkinNumMap);

       return BaseResult.success(resultMap);
    }
}

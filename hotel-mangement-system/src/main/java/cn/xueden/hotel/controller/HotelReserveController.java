package cn.xueden.hotel.controller;

import cn.xueden.annotation.EnableSysLog;
import cn.xueden.base.BaseResult;
import cn.xueden.exception.BadRequestException;
import cn.xueden.hotel.domain.HotelCheckin;
import cn.xueden.hotel.domain.HotelReserve;
import cn.xueden.hotel.service.IHotelReserveService;
import cn.xueden.hotel.service.dto.ReserveQueryCriteria;
import cn.xueden.hotel.vo.HotelReserveModel;
import cn.xueden.utils.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**功能描述：预订模块前端控制器
 * @author:梁志杰
 * @date:2023/3/19
 * @description:cn.xueden.hotel.controller
 * @version:1.0
 */
@RestController
@RequestMapping("reserve")
public class HotelReserveController {

    private final IHotelReserveService hotelReserveService;

    public HotelReserveController(IHotelReserveService hotelReserveService) {
        this.hotelReserveService = hotelReserveService;
    }

    /**
     * 获取预订列表数据
     * @param queryCriteria
     * @param pageVo
     * @return
     */
    @GetMapping
    @EnableSysLog("获取预订列表数据")
    public ResponseEntity<Object> getList(ReserveQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(hotelReserveService.getList(queryCriteria,pageable), HttpStatus.OK);
    }

    /**
     * 根据id获取详情信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @EnableSysLog("根据id获取详情信息")
    public BaseResult detail(@PathVariable Long id){
        if(null==id){
            throw new BadRequestException("获取信息失败");
        }
        HotelReserve dbHotelReserve = hotelReserveService.getById(id);
        return BaseResult.success(dbHotelReserve);
    }

    /**
     * 退订
     * @param id
     * @return
     */
    @PutMapping("unsubscribe/{id}")
    @EnableSysLog("退订")
    public BaseResult unsubscribe(@PathVariable Long id){
        if(null==id){
            throw new BadRequestException("退订失败");
        }else{
            hotelReserveService.unsubscribe(id);
        }
        return BaseResult.success("退订成功");
    }

    /**
     * 确认收款
     * @param id
     * @return
     */
    @PutMapping("payment/{id}")
    @EnableSysLog("确认收款")
    public BaseResult payment(@PathVariable Long id){
        if(null==id){
            throw new BadRequestException("收款失败");
        }else{
            hotelReserveService.payment(id);
        }
        return BaseResult.success("收款成功");
    }

    /**
     * 入住酒店
     * @param hotelCheckin
     * @return
     */
    @PostMapping("checkIn")
    @EnableSysLog("入住酒店登记")
    public BaseResult checkIn(@RequestBody HotelCheckin hotelCheckin){
        hotelReserveService.checkIn(hotelCheckin);
        return BaseResult.success("登记成功");
    }

    /**
     * 后台预订房间
     * @param reserveModel
     * @return
     */
    @PostMapping("addReserve")
    @EnableSysLog("后台预订房间")
    public BaseResult addReserve(@RequestBody HotelReserveModel reserveModel){
        hotelReserveService.addMemberReserve(reserveModel);
        return BaseResult.success("预订成功");
    }



}

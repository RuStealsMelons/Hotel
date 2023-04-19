package cn.xueden.hotel.controller;

import cn.xueden.annotation.EnableSysLog;
import cn.xueden.base.BaseResult;
import cn.xueden.exception.BadRequestException;
import cn.xueden.hotel.domain.HotelRoom;

import cn.xueden.hotel.service.IHotelReserveService;
import cn.xueden.hotel.service.IHotelRoomService;
import cn.xueden.hotel.service.dto.RoomQueryCriteria;

import cn.xueden.utils.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**功能描述：房间管理前端控制器
 * @author:梁志杰
 * @date:2023/3/17
 * @description:cn.xueden.hotel.controller
 * @version:1.0
 */
@RestController
@RequestMapping("room")
public class HotelRoomController {

    private final IHotelRoomService hotelRoomService;

    private final IHotelReserveService hotelReserveService;

    public HotelRoomController(IHotelRoomService hotelRoomService, IHotelReserveService hotelReserveService) {
        this.hotelRoomService = hotelRoomService;
        this.hotelReserveService = hotelReserveService;
    }

    /**
     * 获取房间列表数据
     * @param queryCriteria
     * @param pageVo
     * @return
     */
    @GetMapping
    @EnableSysLog("获取房间列表数据")
    public ResponseEntity<Object> getList(RoomQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(hotelRoomService.getList(queryCriteria,pageable), HttpStatus.OK);
    }

    /**
     * 添加房间信息
     * @param hotelRoom
     * @return
     */
    @PostMapping
    @EnableSysLog("添加房间信息")
    public BaseResult addRoom(@RequestBody HotelRoom hotelRoom){
        boolean result = hotelRoomService.addRoom(hotelRoom);
        if(result){
            return BaseResult.success("添加成功");
        }else {
            return BaseResult.fail("添加失败，房间号已经存在！");
        }
    }

    /**
     * 根据id获取详情信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @EnableSysLog("根据id获取房间详情信息")
    public BaseResult detail(@PathVariable Long id){
        if(null==id){
            throw new BadRequestException("获取信息失败");
        }
        HotelRoom dbHotelRoom = hotelRoomService.getById(id);
        return BaseResult.success(dbHotelRoom);
    }

    /**
     * 更新房间信息
     * @param hotelRoom
     * @return
     */
    @PutMapping
    @EnableSysLog("更新房间信息")
    public BaseResult EditRoom(@RequestBody HotelRoom hotelRoom){
        hotelRoomService.editRoom(hotelRoom);
        return BaseResult.success("更新成功");
    }

    /**
     * 删除房间信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @EnableSysLog("删除房间信息")
    public BaseResult delete(@PathVariable Long id){
        if (null==id){
            throw new BadRequestException("删除信息失败");
        }
        hotelRoomService.deleteById(id);
        return BaseResult.success("删除成功");
    }

    /**
     * 统计房间数量和每天剩余房间数量
     * @return
     */
    @GetMapping("count")
    @EnableSysLog("统计房间数量和每天剩余房间数量")
    public BaseResult count(){
        Map<String,Object> resultMap = new HashMap<>();
        long counRoom = hotelRoomService.count();
        resultMap.put("counRoom",counRoom);
        // 统计每天剩余房间数量
        List<Map<String,Object>> countRoomMapList = hotelReserveService.countReserveRoomByDay();
        resultMap.put("countRoomMapList",countRoomMapList);
        return BaseResult.success(resultMap);
    }

    /**
     * 根据指定日期获取所有空闲房间
     * @param nowDate
     * @return
     */
    @GetMapping("getRoomListByDate/{nowDate}")
    public BaseResult getRoomListByDate(@PathVariable String nowDate){
        List<Map<String,Object>> roomList = hotelRoomService.getRoomListByDate(nowDate);
        return BaseResult.success(roomList);
    }

}

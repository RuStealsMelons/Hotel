package cn.xueden.hotel.controller;

import cn.xueden.annotation.EnableSysLog;
import cn.xueden.base.BaseResult;
import cn.xueden.exception.BadRequestException;
import cn.xueden.hotel.domain.HotelFloor;
import cn.xueden.hotel.domain.HotelRoomType;
import cn.xueden.hotel.service.IHotelFloorService;
import cn.xueden.hotel.service.dto.FloorQueryCriteria;
import cn.xueden.utils.PageVo;
import cn.xueden.utils.ResultVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**功能描述：楼层前端控制器
 * @author:梁志杰
 * @date:2023/3/16
 * @description:cn.xueden.hotel.controller
 * @version:1.0
 */
@RestController
@RequestMapping("floor")
public class HotelFloorController {

    private final IHotelFloorService hotelFloorService;

    public HotelFloorController(IHotelFloorService hotelFloorService) {
        this.hotelFloorService = hotelFloorService;
    }

    /**
     * 获取楼层列表数据
     * @param queryCriteria
     * @param pageVo
     * @return
     */
    @GetMapping
    @EnableSysLog("获取楼层列表数据")
    public ResponseEntity<Object> getList(FloorQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(hotelFloorService.getList(queryCriteria,pageable), HttpStatus.OK);
    }

    /**
     * 添加楼层信息
     * @param hotelFloor
     * @return
     */
    @PostMapping
    @EnableSysLog("添加楼层信息")
    public BaseResult addFloor(@RequestBody HotelFloor hotelFloor){
        boolean result = hotelFloorService.addFloor(hotelFloor);
        if(result){
            return BaseResult.success("添加成功");
        }else {
            return BaseResult.fail("添加失败");
        }
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
        HotelFloor dbHotelFloor = hotelFloorService.getById(id);
        return BaseResult.success(dbHotelFloor);
    }

    /**
     * 更新楼层信息
     * @param hotelFloor
     * @return
     */
    @PutMapping
    @EnableSysLog("更新楼层信息")
    public BaseResult editFloor(@RequestBody HotelFloor hotelFloor){
        hotelFloorService.editFloor(hotelFloor);
        return BaseResult.success("更新成功");
    }

    /**
     * 删除楼层信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @EnableSysLog("删除楼层信息")
    public BaseResult delete(@PathVariable Long id){
        if (null==id){
            throw new BadRequestException("删除信息失败");
        }
        hotelFloorService.deleteById(id);
        return BaseResult.success("删除成功");
    }

    /**
     * 获取所有楼层
     * @return
     */
    @GetMapping("all")
    @EnableSysLog("获取所有楼层")
    public BaseResult all(){
        List<HotelFloor> list = hotelFloorService.getAll();
        List<ResultVo> resultVoList = list.stream().map(temp-> {
            ResultVo obj = new ResultVo();
            obj.setName(temp.getFloorName());
            obj.setId(temp.getId());
            return obj;
        }).collect(Collectors.toList());
        return BaseResult.success(resultVoList);
    }

}

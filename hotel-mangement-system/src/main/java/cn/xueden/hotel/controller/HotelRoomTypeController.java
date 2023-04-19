package cn.xueden.hotel.controller;

import cn.xueden.annotation.EnableSysLog;
import cn.xueden.base.BaseResult;
import cn.xueden.exception.BadRequestException;

import cn.xueden.hotel.domain.HotelRoomType;
import cn.xueden.hotel.service.IHotelRoomTypeService;
import cn.xueden.hotel.service.dto.RoomTypeQueryCriteria;

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

/**功能描述：房间类型前端控制器
 * @author:梁志杰
 * @date:2023/3/16
 * @description:cn.xueden.hotel.controller
 * @version:1.0
 */
@RestController
@RequestMapping("roomType")
public class HotelRoomTypeController {

    private final IHotelRoomTypeService hotelRoomTypeService;

    public HotelRoomTypeController(IHotelRoomTypeService hotelRoomTypeService) {
        this.hotelRoomTypeService = hotelRoomTypeService;
    }

    /**
     * 获取房间类型列表数据
     * @param queryCriteria
     * @param pageVo
     * @return
     */
    @GetMapping
    @EnableSysLog("获取房间类型列表数据")
    public ResponseEntity<Object> getList(RoomTypeQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(hotelRoomTypeService.getList(queryCriteria,pageable), HttpStatus.OK);
    }

    /**
     * 添加房间类型信息
     * @param hotelRoomType
     * @return
     */
    @PostMapping
    @EnableSysLog("添加房间类型信息")
    public BaseResult addRoomType(@RequestBody HotelRoomType hotelRoomType){
        boolean result = hotelRoomTypeService.addRoomType(hotelRoomType);
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
    @EnableSysLog("根据id获取房间类型详情信息")
    public BaseResult detail(@PathVariable Long id){
        if(null==id){
            throw new BadRequestException("获取信息失败");
        }
        HotelRoomType dbHotelRoomType  = hotelRoomTypeService.getById(id);
        return BaseResult.success(dbHotelRoomType);
    }

    /**
     * 更新房间类型信息
     * @param hotelRoomType
     * @return
     */
    @PutMapping
    @EnableSysLog("更新房间类型信息")
    public BaseResult editRoomType(@RequestBody HotelRoomType hotelRoomType){
        hotelRoomTypeService.editRoomType(hotelRoomType);
        return BaseResult.success("更新成功");
    }

    /**
     * 删除房间类型信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @EnableSysLog("删除房间类型信息")
    public BaseResult delete(@PathVariable Long id){
        if (null==id){
            throw new BadRequestException("删除信息失败");
        }
        hotelRoomTypeService.deleteById(id);
        return BaseResult.success("删除成功");
    }

    /**
     * 获取所有房间类型
     * @return
     */
    @GetMapping("all")
    @EnableSysLog("获取所有房间类型")
    public BaseResult all(){
        List<HotelRoomType> list = hotelRoomTypeService.getAll();
        List<ResultVo> resultVoList = list.stream().map(temp-> {
            ResultVo obj = new ResultVo();
            obj.setName(temp.getTypeName());
            obj.setId(temp.getId());
            return obj;
        }).collect(Collectors.toList());
        return BaseResult.success(resultVoList);
    }

}

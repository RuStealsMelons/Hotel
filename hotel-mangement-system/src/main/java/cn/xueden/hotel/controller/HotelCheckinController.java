package cn.xueden.hotel.controller;

import cn.xueden.base.BaseResult;
import cn.xueden.hotel.service.IHotelCheckinService;
import cn.xueden.hotel.service.dto.CheckinQueryCriteria;
import cn.xueden.hotel.vo.HotelCheckinModel;
import cn.xueden.utils.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**功能描述：入住管理前端控制器
 * @author:梁志杰
 * @date:2023/3/25
 * @description:cn.xueden.hotel.controller
 * @version:1.0
 */
@RestController
@RequestMapping("checkin")
public class HotelCheckinController {

    private IHotelCheckinService hotelCheckinService;

    public HotelCheckinController(IHotelCheckinService hotelCheckinService) {
        this.hotelCheckinService = hotelCheckinService;
    }

    /**
     * 获取入住列表数据
     * @param queryCriteria
     * @param pageVo
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> getList(CheckinQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(hotelCheckinService.getList(queryCriteria,pageable), HttpStatus.OK);
    }

    /**
     * 后台入住登记
     * @param checkinModel
     * @return
     */
    @PostMapping("addCheckin")
    public BaseResult addCheckin(@RequestBody HotelCheckinModel checkinModel){
        hotelCheckinService.addCheckin(checkinModel);
        return BaseResult.success("入住登记成功");
    }

    /**
     * 退房
     * @param id
     * @return
     */
    @PutMapping("/checkOut/{id}")
    public BaseResult checkOut(@PathVariable Long id){
        hotelCheckinService.checkOut(id);
        return BaseResult.success("退房成功");
    }

}

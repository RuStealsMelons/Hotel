package cn.xueden.hotel.controller;

import cn.xueden.annotation.EnableSysLog;
import cn.xueden.base.BaseResult;
import cn.xueden.exception.BadRequestException;
import cn.xueden.hotel.domain.HotelMember;
import cn.xueden.hotel.service.IHotelMemberService;
import cn.xueden.hotel.service.dto.MemberQueryCriteria;
import cn.xueden.utils.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**功能描述：会员模块前端控制器
 * @author:梁志杰
 * @date:2023/3/18
 * @description:cn.xueden.hotel.controller
 * @version:1.0
 */
@RestController
@RequestMapping("member")
public class HotelMemberController {

    private final IHotelMemberService hotelMemberService;

    public HotelMemberController(IHotelMemberService hotelMemberService) {
        this.hotelMemberService = hotelMemberService;
    }

    /**
     * 获取会员列表数据
     * @param queryCriteria
     * @param pageVo
     * @return
     */
    @GetMapping
    @EnableSysLog("获取会员列表数据")
    public ResponseEntity<Object> getList(MemberQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(hotelMemberService.getList(queryCriteria,pageable), HttpStatus.OK);
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
        HotelMember dbHotelMember = hotelMemberService.getById(id);
        return BaseResult.success(dbHotelMember);
    }

    /**
     * 会员充值
     * @param Member
     * @return
     */
    @PutMapping("recharge")
    @EnableSysLog("会员充值")
    public BaseResult recharge(@RequestBody HotelMember Member){
        hotelMemberService.recharge(Member);
        return BaseResult.success("充值成功");
    }

    /**
     * 删除会员信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @EnableSysLog("删除会员信息")
    public BaseResult delete(@PathVariable Long id){
        if (null==id){
            throw new BadRequestException("删除信息失败");
        }
        hotelMemberService.deleteById(id);
        return BaseResult.success("删除成功");
    }

}

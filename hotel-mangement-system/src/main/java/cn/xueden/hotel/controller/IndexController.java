package cn.xueden.hotel.controller;

import cn.xueden.annotation.EnableSysLog;
import cn.xueden.base.BaseResult;
import cn.xueden.hotel.domain.*;
import cn.xueden.hotel.service.*;
import cn.xueden.hotel.service.dto.RechargeRecordQueryCriteria;
import cn.xueden.hotel.service.dto.ReserveQueryCriteria;
import cn.xueden.hotel.service.dto.RoomQueryCriteria;
import cn.xueden.hotel.vo.MemberPwdModel;
import cn.xueden.utils.HutoolJWTUtil;
import cn.xueden.utils.PageVo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**功能描述：酒店首页控制器
 * @author:梁志杰
 * @date:2023/3/10
 * @description:cn.xueden.hotel.controller
 * @version:1.0
 */
@RestController
@RequestMapping("hotel")
public class IndexController {

    private final IHotelRoomTypeService hotelRoomTypeService;

    private final IHotelFloorService hotelFloorService;

    private final IHotelRoomService hotelRoomService;

    private final IHotelMemberService hotelMemberService;

    private final IHotelReserveService hotelReserveService;

    private final IHotelRechargeRecordService hotelRechargeRecordService;


    public IndexController(IHotelRoomTypeService hotelRoomTypeService, IHotelFloorService hotelFloorService, IHotelRoomService hotelRoomService, IHotelMemberService hotelMemberService, IHotelReserveService hotelReserveService, IHotelRechargeRecordService hotelRechargeRecordService) {
        this.hotelRoomTypeService = hotelRoomTypeService;
        this.hotelFloorService = hotelFloorService;
        this.hotelRoomService = hotelRoomService;
        this.hotelMemberService = hotelMemberService;
        this.hotelReserveService = hotelReserveService;
        this.hotelRechargeRecordService = hotelRechargeRecordService;
    }

    /**
     *
     * @return
     */
    @GetMapping("getIndex")
    @EnableSysLog("酒店首页获取数据")
    public BaseResult getIndex(){

        HashMap<String,Object> resultMap = new HashMap<>();
        // 获取房间类型列表
        List<HotelRoomType> indexRoomTypeList = hotelRoomTypeService.getAll();
        // 首页房间类型列表
        resultMap.put("indexRoomTypeList",indexRoomTypeList);

        // 获取酒店楼层列表
        List<HotelFloor> hotelFloorList = hotelFloorService.getAll();
        //每个楼层获取四个房间
        setRoomToFloor(hotelFloorList);
        resultMap.put("hotelFloorList",hotelFloorList);
        return BaseResult.success(resultMap);

    }

    /**
     * 每个楼层获取四个房间
     * @param floors
     * @return
     */
    private List<HotelFloor> setRoomToFloor(List<HotelFloor> floors){
        for(HotelFloor r : floors){
            if(r.getId() != null && r.getId() != 0){
                // 根据楼层ID获取对应四个房间
                List<HotelRoom> roomList = hotelRoomService.getRoomListByfloorId(r.getId(),4);
                r.setRoomList(roomList);

            }
        }
        return floors;
    }

    /**
     * 根据房间ID获取房间详情
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    @EnableSysLog("访问酒店房间详情")
    public BaseResult detail(@PathVariable Long id){
        HotelRoom dbHotelRoom = hotelRoomService.getById(id);
        return BaseResult.success(dbHotelRoom);
    }

    /**
     * 会员注册
     * @param hotelMember
     * @return
     */
    @PostMapping("register")
    @EnableSysLog("酒店会员注册")
    public BaseResult register(@RequestBody HotelMember hotelMember){
        hotelMemberService.register(hotelMember);
        return BaseResult.success("注册成功");
    }

    /**
     * 会员登录
     * @param hotelMember
     * @return
     */
    @PostMapping("login")
    @EnableSysLog("酒店会员登录")
    public BaseResult login(@RequestBody HotelMember hotelMember){
        HotelMember dbHotelMember = hotelMemberService.login(hotelMember);
        // 生成token
        String token = HutoolJWTUtil.createToken(dbHotelMember);
        dbHotelMember.setMemberToken(token);
        return BaseResult.success("登录成功",dbHotelMember);
    }

    /**
     * 获取所有楼层和房间类型
     * @return
     */
    @GetMapping("getAllRoomTypeAndFloor")
    @EnableSysLog("获取所有楼层和房间类型")
    public BaseResult getAllRoomTypeAndFloor(){
        Map<String,Object> resultMap = new HashMap<>();
        // 获取房间类型列表
        List<HotelRoomType> roomTypeList = hotelRoomTypeService.getAll();
        resultMap.put("roomTypeList",roomTypeList);
        // 获取酒店楼层列表
        List<HotelFloor> floorList = hotelFloorService.getAll();
        resultMap.put("floorList",floorList);
        return BaseResult.success(resultMap);
    }

    /**
     * 获取房间列表
     * @param queryCriteria
     * @param pageVo
     * @return
     */
    @GetMapping("getRoomList")
    @EnableSysLog("获取房间列表")
    public BaseResult getRoomList(RoomQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize() ,
                Sort.Direction.DESC,"id");
       return BaseResult.success(hotelRoomService.getList(queryCriteria,pageable)) ;
    }

    /**
     * 会员预订房间
     * @param hotelReserve
     * @return
     */
    @PostMapping("addReserve")
    @EnableSysLog("会员预订房间")
    public BaseResult addReserve(@RequestBody HotelReserve hotelReserve,
                                 @RequestHeader("memberToken")String memberToken
    ){
        System.out.println("memberToken:"+memberToken);
        if(memberToken==null){
            return BaseResult.fail("预订失败，请先登录！");
        }else {
            // 根据memberToken获取登录用户ID
            Long memberId = HutoolJWTUtil.parseToken(memberToken);
            hotelReserve.setCreateBy(memberId);
        }
        hotelReserveService.addReserve(hotelReserve);
        return BaseResult.success("预订成功");
    }

    /**
     * 根据房间ID获取预订日期数据
     * @param id
     * @return
     */
    @GetMapping("getDatesByRoomId/{id}")
    @EnableSysLog("根据房间ID获取预订日期数据")
    public BaseResult getDatesByRoomId(@PathVariable Long id){
        Set<LocalDate> stringSet = hotelReserveService.getDatesByRoomId(id);
        return BaseResult.success(stringSet);
    }

    /**
     * 获取登录会员信息
     * @param memberToken
     * @return
     */
    @GetMapping("getLoginMemberInfo")
    @EnableSysLog("获取登录会员信息")
    public BaseResult getLoginMemberInfo(@RequestHeader("memberToken")String memberToken){
        if(memberToken==null){
            return BaseResult.fail("预订失败，请先登录！");
        }else {
            // 根据memberToken获取登录用户ID
            Long memberId = HutoolJWTUtil.parseToken(memberToken);
           HotelMember dbHotelMember =  hotelMemberService.getLoginMemberInfo(memberId);
            return BaseResult.success(dbHotelMember);
        }
    }

    /**
     * 更新会员信息
     * @param hotelMember
     * @return
     */
    @PutMapping("updateMember")
    @EnableSysLog("更新会员信息")
    public BaseResult updateMember(@RequestBody HotelMember hotelMember){
        hotelMemberService.updateMember(hotelMember);
        return BaseResult.success("更新成功");

    }

    /**
     * 获取我的预订房间列表
     * @param queryCriteria
     * @param pageVo
     * @param memberToken
     * @return
     */
    @GetMapping("getMyReserveList")
    @EnableSysLog("获取我的预订房间列表")
    public BaseResult getMyReserveList(ReserveQueryCriteria queryCriteria,PageVo pageVo,
                                       @RequestHeader("memberToken")String memberToken){
        if(memberToken==null){
            return BaseResult.fail("查看预订信息失败，请先登录！");
        }else {
            // 根据memberToken获取登录用户ID
            Long memberId = HutoolJWTUtil.parseToken(memberToken);
            Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize() ,
                    Sort.Direction.DESC,"id");
            queryCriteria.setCreateBy(memberId);
            return BaseResult.success(hotelReserveService.getMyReserveList(queryCriteria,pageable));
        }
    }

    /**
     * 订单付款
     * @param id
     * @return
     */
    @PutMapping("payment/{id}")
    @EnableSysLog("订单付款")
    public BaseResult payment(@PathVariable Long id){
        hotelReserveService.payment(id);
        return BaseResult.success("付款成功");

    }

    @PutMapping("updateMemberPwd")
    @EnableSysLog("修改会员个人密码")
    public BaseResult updateMemberPwd(@RequestBody MemberPwdModel memberPwdModel,
                                      @RequestHeader("memberToken")String memberToken){
        if(memberToken==null){
            return BaseResult.fail("修改密码失败，请先登录！");
        }else {
            // 根据memberToken获取登录用户ID
            Long memberId = HutoolJWTUtil.parseToken(memberToken);
            // 根据会员ID更新密码
            memberPwdModel.setMemberId(memberId);
            hotelMemberService.updateMemberPwd(memberPwdModel);
            return BaseResult.success("修改密码成功");
        }
    }

    /**
     * 获取我的充值记录列表
     * @param queryCriteria
     * @param pageVo
     * @param memberToken
     * @return
     */
    @GetMapping("getMyRechargeRecordList")
    @EnableSysLog("获取我的充值记录列表")
    public BaseResult getMyRechargeRecordList(RechargeRecordQueryCriteria queryCriteria, PageVo pageVo,
                                       @RequestHeader("memberToken")String memberToken){
        if(memberToken==null){
            return BaseResult.fail("查看预订信息失败，请先登录！");
        }else {
            // 根据memberToken获取登录用户ID
            Long memberId = HutoolJWTUtil.parseToken(memberToken);
            Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize() ,
                    Sort.Direction.DESC,"id");
            queryCriteria.setMemberId(memberId);
            return BaseResult.success(hotelRechargeRecordService.getMyRechargeRecordList(queryCriteria,pageable));
        }
    }


}

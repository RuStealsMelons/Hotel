package cn.xueden.hotel.service;

import cn.xueden.hotel.domain.HotelCheckin;
import cn.xueden.hotel.domain.HotelReserve;
import cn.xueden.hotel.service.dto.ReserveQueryCriteria;
import cn.xueden.hotel.vo.CountRoomModel;
import cn.xueden.hotel.vo.HotelReserveModel;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**功能描述：会员预订房间接口
 * @author:梁志杰
 * @date:2023/3/13
 * @description:cn.xueden.hotel.service
 * @version:1.0
 */
public interface IHotelReserveService {

    /**
     * 会员预订房间
     * @param hotelReserve
     */
    void addReserve(HotelReserve hotelReserve);

    /**
     * 根据房间ID获取预订日期数据
     * @param id
     */
    Set<LocalDate> getDatesByRoomId(Long id);

    /**
     * 获取我的预订列表数据
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Object getMyReserveList(ReserveQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 订单付款
     * @param id
     */
    void payment(Long id);

    /**
     * 获取预订列表数据
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Object getList(ReserveQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 根据ID获取详情信息
     * @param id
     * @return
     */
    HotelReserve getById(Long id);

    /**
     * 退订
     * @param id
     */
    void unsubscribe(Long id);

    /**
     * 入住酒店
     * @param hotelCheckin
     */
    void checkIn(HotelCheckin hotelCheckin);

    /**
     * 统计每天剩余房间数量
     * @return
     */
    List<Map<String,Object>> countReserveRoomByDay();

    /**
     * 后台预订房间
     * @param reserveModel
     */
    void addMemberReserve(HotelReserveModel reserveModel);

    /**
     * 统计今日预达人数
     * @return
     */
    long getArriveNums();

    /**
     * 统计今日订单数量
     * @return
     */
    long getOrderNums();

    /**
     * 统计今日营业额
     * @return
     */
    long getTradeNums();
}

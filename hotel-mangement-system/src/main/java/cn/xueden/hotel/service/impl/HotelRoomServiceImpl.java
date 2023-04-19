package cn.xueden.hotel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.xueden.exception.BadRequestException;

import cn.xueden.hotel.domain.HotelFloor;
import cn.xueden.hotel.domain.HotelRoom;
import cn.xueden.hotel.domain.HotelRoomType;
import cn.xueden.hotel.repository.HotelFloorRepository;
import cn.xueden.hotel.repository.HotelRoomRepository;
import cn.xueden.hotel.repository.HotelRoomTypeRepository;
import cn.xueden.hotel.service.IHotelRoomService;
import cn.xueden.hotel.service.dto.RoomQueryCriteria;
import cn.xueden.utils.PageUtil;
import cn.xueden.utils.QueryHelp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**功能描述：酒店房间接口实现类
 * @author:梁志杰
 * @date:2023/3/10
 * @description:cn.xueden.hotel.service.impl
 * @version:1.0
 */
@Service
@Transactional(readOnly = true)
public class HotelRoomServiceImpl implements IHotelRoomService {

    private final HotelRoomRepository hotelRoomRepository;

    private final HotelFloorRepository hotelFloorRepository;

    private final HotelRoomTypeRepository hotelRoomTypeRepository;

    public HotelRoomServiceImpl(HotelRoomRepository hotelRoomRepository, HotelFloorRepository hotelFloorRepository, HotelRoomTypeRepository hotelRoomTypeRepository) {
        this.hotelRoomRepository = hotelRoomRepository;
        this.hotelFloorRepository = hotelFloorRepository;
        this.hotelRoomTypeRepository = hotelRoomTypeRepository;
    }

    /**
     * 根据楼层Id获取房间列表
     * @param id
     * @return
     */
    @Override
    public List<HotelRoom> getRoomListByfloorId(Long id,Integer num) {
        List<HotelRoom> list = hotelRoomRepository.getRoomListByfloorId(id,num);
        return list;
    }

    /**
     * 根据房间ID获取房间信息
     * @param id
     * @return
     */
    @Override
    public HotelRoom getById(Long id) {
        return hotelRoomRepository.findById(id).orElseGet(HotelRoom::new);
    }

    /**
     * 获取房间列表
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @Override
    public Object getList(RoomQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelRoom> page = hotelRoomRepository.findAll((root,query,criteriaBuilder)->
                QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        setFloorAndTypeToRoom(page.stream().toList());
        return PageUtil.toPage(page);
    }

    //添加楼层名称和房间类别到房间列表
    private List<HotelRoom> setFloorAndTypeToRoom(List<HotelRoom> hotelRooms){

        for(HotelRoom r:hotelRooms){
            if(r.getFloorId() != null && r.getFloorId() != 0){
                HotelFloor hotelFloor = hotelFloorRepository.findById(r.getFloorId()).orElseGet(HotelFloor::new);
                if(StringUtils.isNotBlank(hotelFloor.getFloorName())){
                    r.setHotelFloor(hotelFloor);
                }
            }

            if(r.getRoomTypeId() != null && r.getRoomTypeId() != 0){
                HotelRoomType hotelRoomType = hotelRoomTypeRepository.findById(r.getRoomTypeId()).orElseGet(HotelRoomType::new);
                if(StringUtils.isNotBlank(hotelRoomType.getTypeName())){
                    r.setHotelRoomType(hotelRoomType);
                }
            }
        }

        return hotelRooms;
    }

    /**
     * 添加房间信息
     * @param hotelRoom
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRoom(HotelRoom hotelRoom) {
        // 根据房间号查询记录
        HotelRoom dbHotelRoom = hotelRoomRepository.findByRoomNumber(hotelRoom.getRoomNumber());
        if(dbHotelRoom!=null){
            return false;
        }else {
            hotelRoom.setRoomStatus(0);
            dbHotelRoom = hotelRoomRepository.save(hotelRoom);
        }
        return dbHotelRoom.getId()!=null;
    }

    /**
     * 更新房间信息
     * @param hotelRoom
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRoom(HotelRoom hotelRoom) {
        // 更加房间号获取信息
        HotelRoom dbHotelRoom = hotelRoomRepository.findByRoomNumber(hotelRoom.getRoomNumber());
        if(dbHotelRoom!=null&& !dbHotelRoom.getId().equals(hotelRoom.getId())){
            throw new BadRequestException("更新失败，房间号已经存在！");
        }else{
            BeanUtil.copyProperties(hotelRoom,dbHotelRoom,
                    CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            hotelRoomRepository.save(dbHotelRoom);
        }

    }

    /**
     * 根据ID删除房间信息
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        hotelRoomRepository.deleteById(id);
    }

    /**
     * 统计房间数量
     * @return
     */
    @Override
    public long count() {
        return hotelRoomRepository.count();
    }

    /**
     * 根据指定日期获取所有空闲房间
     * @param nowDate
     */
    @Override
    public List<Map<String,Object>> getRoomListByDate(String nowDate) {
       return hotelRoomRepository.getRoomListByDate(nowDate);
    }
}

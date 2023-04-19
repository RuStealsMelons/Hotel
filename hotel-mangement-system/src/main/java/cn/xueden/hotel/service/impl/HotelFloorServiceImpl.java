package cn.xueden.hotel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.xueden.exception.BadRequestException;
import cn.xueden.hotel.domain.HotelFloor;


import cn.xueden.hotel.repository.HotelFloorRepository;
import cn.xueden.hotel.service.IHotelFloorService;
import cn.xueden.hotel.service.dto.FloorQueryCriteria;
import cn.xueden.utils.PageUtil;
import cn.xueden.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**功能描述：楼层接口实现类
 * @author:梁志杰
 * @date:2023/3/10
 * @description:cn.xueden.hotel.service.impl
 * @version:1.0
 */
@Service
@Transactional(readOnly = true)
public class HotelFloorServiceImpl implements IHotelFloorService {

    private final HotelFloorRepository hotelFloorRepository;

    public HotelFloorServiceImpl(HotelFloorRepository hotelFloorRepository) {
        this.hotelFloorRepository = hotelFloorRepository;
    }

    /**
     * 获取所有楼层列表
     * @return
     */
    @Override
    public List<HotelFloor> getAll() {
        return hotelFloorRepository.findAll();
    }

    /**
     * 获取楼层列表数据
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @Override
    public Object getList(FloorQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelFloor> page = hotelFloorRepository.findAll((root, query, criteriaBuilder)->
                QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page);
    }

    /**
     * 添加楼层信息
     * @param hotelFloor
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addFloor(HotelFloor hotelFloor) {
        // 根据楼层号查询记录
        HotelFloor dbHotelFloor = hotelFloorRepository.findByFloorNo(hotelFloor.getFloorNo());
        if(dbHotelFloor!=null){
            throw new BadRequestException("添加失败，楼层号已经存在!");
        }else {
            dbHotelFloor = hotelFloorRepository.save(hotelFloor);
            return dbHotelFloor.getId()!=null;
        }

    }

    /**
     * 根据ID获取详情信息
     * @param id
     * @return
     */
    @Override
    public HotelFloor getById(Long id) {
        return hotelFloorRepository.findById(id).orElseGet(HotelFloor::new);
    }

    /**
     * 更新楼层信息
     * @param hotelFloor
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editFloor(HotelFloor hotelFloor) {

        // 根据楼层ID获取楼层信息
        HotelFloor dbHotelFloor = hotelFloorRepository.getReferenceById(hotelFloor.getId());
        // 根据楼层号获取楼层信息
        HotelFloor tempHotelFloor = hotelFloorRepository.findByFloorNo(hotelFloor.getFloorNo());
        if(tempHotelFloor!=null&& !tempHotelFloor.getId().equals(hotelFloor.getId())){
            throw new BadRequestException("更新失败，该楼层号已经存在！");
        }else{
            BeanUtil.copyProperties(hotelFloor,dbHotelFloor,
                    CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            hotelFloorRepository.save(dbHotelFloor);
        }

    }

    /**
     * 根据ID删除楼层信息
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        hotelFloorRepository.deleteById(id);
    }


}

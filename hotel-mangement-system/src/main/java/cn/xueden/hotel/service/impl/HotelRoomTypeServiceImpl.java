package cn.xueden.hotel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.xueden.hotel.domain.HotelRoomType;

import cn.xueden.hotel.repository.HotelRoomTypeRepository;
import cn.xueden.hotel.service.IHotelRoomTypeService;
import cn.xueden.hotel.service.dto.RoomTypeQueryCriteria;
import cn.xueden.utils.PageUtil;
import cn.xueden.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**功能描述：房间类型接口实现类
 * @author:梁志杰
 * @date:2023/3/10
 * @description:cn.xueden.hotel.service.impl
 * @version:1.0
 */
@Service
@Transactional(readOnly = true)
public class HotelRoomTypeServiceImpl implements IHotelRoomTypeService {

    private final HotelRoomTypeRepository hotelRoomTypeRepository;

    public HotelRoomTypeServiceImpl(HotelRoomTypeRepository hotelRoomTypeRepository) {
        this.hotelRoomTypeRepository = hotelRoomTypeRepository;
    }

    /**
     * 获取所有房间类型数据
     * @return
     */
    @Override
    public List<HotelRoomType> getAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "typeSort");
        return hotelRoomTypeRepository.findAll(sort);
    }

    /**
     * 获取房间类型列表
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @Override
    public Object getList(RoomTypeQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelRoomType> page = hotelRoomTypeRepository.findAll((root, query, criteriaBuilder)->
                QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page);
    }

    /**
     * 添加房间类型信息
     * @param hotelRoomType
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRoomType(HotelRoomType hotelRoomType) {
        HotelRoomType dbHotelRoomType = hotelRoomTypeRepository.save(hotelRoomType);
        return dbHotelRoomType.getId()!=null;
    }

    /**
     * 根据ID获取详情信息
     * @param id
     * @return
     */
    @Override
    public HotelRoomType getById(Long id) {
        return hotelRoomTypeRepository.findById(id).orElseGet(HotelRoomType::new);
    }

    /**
     * 更新房间类型信息
     * @param hotelRoomType
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRoomType(HotelRoomType hotelRoomType) {
        HotelRoomType dbHotelRoomType = hotelRoomTypeRepository.getReferenceById(hotelRoomType.getId());
        BeanUtil.copyProperties(hotelRoomType,dbHotelRoomType,
                CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        hotelRoomTypeRepository.save(dbHotelRoomType);
    }

    /**
     * 根据ID删除房间类型信息
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        hotelRoomTypeRepository.deleteById(id);
    }
}

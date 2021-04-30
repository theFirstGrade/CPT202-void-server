package com.voidserver.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.ApplicationVO;
import com.voidserver.common.RentalProductVO;
import com.voidserver.entity.RentalProduct;
import com.voidserver.mapper.RentalProductMapper;
import com.voidserver.service.RentalProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-25
 */
@Service
public class RentalProductServiceImpl extends ServiceImpl<RentalProductMapper, RentalProduct> implements RentalProductService {

    @Override
    public Page<RentalProductVO> getRentalProductDepository(Page<RentalProductVO> page, @Param("searchAddress") String searchAddress) {
        return page.setRecords(this.baseMapper.getRentalProductDepository(page, searchAddress));
    }

    @Override
    public Page<RentalProductVO> getRentalProductDepository1(Page<RentalProductVO> page, @Param("searchAddress") String searchAddress) {
        return page.setRecords(this.baseMapper.getRentalProductDepository1(page, searchAddress));
    }

    @Override
    public Page<RentalProductVO> getRentalProductDepository2(Page<RentalProductVO> page, @Param("searchAddress") String searchAddress, @Param("searchName") String searchName) {
        return page.setRecords(this.baseMapper.getRentalProductDepository2(page, searchAddress, searchName));
    }

    //
    @Override
    public Page<RentalProductVO> getRentalProductDepository3(Page<RentalProductVO> page, @Param("searchAddress") String searchAddress, @Param("searchCate") String searchCate) {
        return page.setRecords(this.baseMapper.getRentalProductDepository3(page, searchAddress, searchCate));
    }

    @Override
    public Page<RentalProductVO> getRentalProductDepository4(Page<RentalProductVO> page, @Param("searchAddress") String searchAddress, @Param("searchCate") String searchCate, @Param("searchName") String searchName) {
        return page.setRecords(this.baseMapper.getRentalProductDepository4(page, searchAddress, searchCate, searchName));
    }


}

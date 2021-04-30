package com.voidserver.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.RentalProductVO;
import com.voidserver.entity.RentalDepository;
import com.voidserver.mapper.RentalDepositoryMapper;
import com.voidserver.service.RentalDepositoryService;
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
public class RentalDepositoryServiceImpl extends ServiceImpl<RentalDepositoryMapper, RentalDepository> implements RentalDepositoryService {

    @Override
    public Page<RentalProductVO> getRentalProductDepository1(Page<RentalProductVO> page) {
        return page.setRecords(this.baseMapper.getRentalProductDepository1(page));
    }

    @Override
    public Page<RentalProductVO> getRentalProductDepository2(Page<RentalProductVO> page, @Param("searchAddress") String searchAddress) {
        return page.setRecords(this.baseMapper.getRentalProductDepository2(page, searchAddress));
    }

    @Override
    public Page<RentalProductVO> getRentalProductDepository3(Page<RentalProductVO> page, @Param("searchAddress") String searchAddress, @Param("searchName") String searchName) {
        return page.setRecords(this.baseMapper.getRentalProductDepository3(page, searchAddress, searchName));
    }

    @Override
    public Page<RentalProductVO> getRentalProductDepository4(Page<RentalProductVO> page, @Param("searchCate") String searchCate) {
        return page.setRecords(this.baseMapper.getRentalProductDepository4(page, searchCate));
    }

    @Override
    public Page<RentalProductVO> getRentalProductDepository5(Page<RentalProductVO> page, @Param("searchCate") String searchCate, @Param("searchAddress") String searchAddress) {
        return page.setRecords(this.baseMapper.getRentalProductDepository5(page, searchCate, searchAddress));
    }

    @Override
    public Page<RentalProductVO> getRentalProductDepository6(Page<RentalProductVO> page, @Param("searchCate") String searchCate, @Param("searchAddress") String searchAddress, @Param("searchName") String searchName) {
        return page.setRecords(this.baseMapper.getRentalProductDepository6(page, searchCate, searchAddress, searchName));
    }

    @Override
    public Page<RentalProductVO> getRentalProductDepository7(Page<RentalProductVO> page, @Param("searchName") String searchName) {
        return page.setRecords(this.baseMapper.getRentalProductDepository7(page, searchName));
    }

    @Override
    public Page<RentalProductVO> getRentalProductDepository8(Page<RentalProductVO> page, @Param("searchCate") String searchCate, @Param("searchName") String searchName) {
        return page.setRecords(this.baseMapper.getRentalProductDepository8(page, searchCate, searchName));
    }

}

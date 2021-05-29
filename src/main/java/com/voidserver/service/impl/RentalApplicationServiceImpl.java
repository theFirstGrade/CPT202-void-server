package com.voidserver.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.ApplicationVO;
import com.voidserver.common.RentalApplicationVO;
import com.voidserver.common.UserApplication;
import com.voidserver.common.UserRentalApplication;
import com.voidserver.entity.RentalApplication;
import com.voidserver.mapper.RentalApplicationMapper;
import com.voidserver.service.RentalApplicationService;
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
public class RentalApplicationServiceImpl extends ServiceImpl<RentalApplicationMapper, RentalApplication> implements RentalApplicationService {

    @Override
    public Page<RentalApplicationVO> getApplicationUser(Page<RentalApplicationVO> page) {
        return page.setRecords(this.baseMapper.getApplicationUser(page));
    }

    @Override
    public Page<RentalApplicationVO> getApplicationUser2(Page<RentalApplicationVO> page, @Param("searchAddress") String searchAddress, @Param("verifyCode") Integer verifyCode) {
        return page.setRecords(this.baseMapper.getApplicationUser2(page, searchAddress, verifyCode));
    }

    @Override
    public Page<RentalApplicationVO> getApplicationUser3(Page<RentalApplicationVO> page, @Param("searchAddress") String searchAddress) {
        return page.setRecords(this.baseMapper.getApplicationUser3(page, searchAddress));
    }

    @Override
    public Page<RentalApplicationVO> getApplicationUser4(Page<RentalApplicationVO> page, @Param("verifyCode") Integer verifyCode) {
        return page.setRecords(this.baseMapper.getApplicationUser4(page, verifyCode));
    }

    @Override
    public Page<RentalApplicationVO> getReturnApplicationUser1(Page<RentalApplicationVO> page) {
        return page.setRecords(this.baseMapper.getReturnApplicationUser1(page));
    }

    @Override
    public Page<RentalApplicationVO> getReturnApplicationUser2(Page<RentalApplicationVO> page, @Param("searchAddress") String searchAddress, @Param("verifyCode") Integer verifyCode) {
        return page.setRecords(this.baseMapper.getReturnApplicationUser2(page, searchAddress, verifyCode));
    }

    @Override
    public Page<RentalApplicationVO> getReturnApplicationUser3(Page<RentalApplicationVO> page, @Param("searchAddress") String searchAddress) {
        return page.setRecords(this.baseMapper.getReturnApplicationUser3(page, searchAddress));
    }

    @Override
    public Page<RentalApplicationVO> getReturnApplicationUser4(Page<RentalApplicationVO> page, @Param("verifyCode") Integer verifyCode) {
        return page.setRecords(this.baseMapper.getReturnApplicationUser4(page, verifyCode));
    }

    @Override
    public Page<UserRentalApplication> getUserRentalApplication1(Page<UserRentalApplication> page, @Param("id") Integer id, @Param("searchAddress") String searchAddress, @Param("isCompleted") Integer isCompleted) {
        return page.setRecords(this.baseMapper.getUserRentalApplication1(page, id, searchAddress, isCompleted));
    }

    @Override
    public Page<UserRentalApplication> getUserRentalApplication2(Page<UserRentalApplication> page, @Param("id") Integer id, @Param("isCompleted") Integer isCompleted) {
        return page.setRecords(this.baseMapper.getUserRentalApplication2(page, id, isCompleted));
    }

    @Override
    public Page<UserRentalApplication> getUserRentalApplication3(Page<UserRentalApplication> page, @Param("id") Integer id, @Param("searchAddress") String searchAddress, @Param("isCompleted") Integer isCompleted) {
        return page.setRecords(this.baseMapper.getUserRentalApplication3(page, id, searchAddress, isCompleted));
    }

    @Override
    public Page<UserRentalApplication> getUserRentalApplication4(Page<UserRentalApplication> page, @Param("id") Integer id, @Param("isCompleted") Integer isCompleted) {
        return page.setRecords(this.baseMapper.getUserRentalApplication4(page, id, isCompleted));
    }


}

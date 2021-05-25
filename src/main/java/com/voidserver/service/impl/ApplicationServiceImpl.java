package com.voidserver.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.ApplicationVO;
import com.voidserver.common.UserApplication;
import com.voidserver.entity.Application;
import com.voidserver.mapper.ApplicationMapper;
import com.voidserver.service.ApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-06
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    @Override
    public Page<ApplicationVO> getApplicationUser(Page<ApplicationVO> page) {
        return page.setRecords(this.baseMapper.getApplicationUser(page));
    }


    @Override
    public Page<ApplicationVO> getApplicationUser2(Page<ApplicationVO> page, @Param("searchAddress") String searchAddress, @Param("verifyCode") Integer verifyCode) {
        return page.setRecords(this.baseMapper.getApplicationUser2(page, searchAddress, verifyCode));
    }

    @Override
    public Page<ApplicationVO> getApplicationUser3(Page<ApplicationVO> page, @Param("searchAddress") String searchAddress) {
        return page.setRecords(this.baseMapper.getApplicationUser3(page, searchAddress));
    }

    @Override
    public Page<ApplicationVO> getApplicationUser4(Page<ApplicationVO> page, @Param("verifyCode") Integer verifyCode) {
        return page.setRecords(this.baseMapper.getApplicationUser4(page, verifyCode));
    }

    @Override
    public Page<UserApplication> getUserApplication1(Page<UserApplication> page, @Param("id") Integer id, @Param("searchAddress") String searchAddress, @Param("isCompleted") Integer isCompleted) {
        return page.setRecords(this.baseMapper.getUserApplication1(page, id, searchAddress, isCompleted));
    }

    @Override
    public Page<UserApplication> getUserApplication2(Page<UserApplication> page, @Param("id") Integer id, @Param("isCompleted") Integer isCompleted) {
        return page.setRecords(this.baseMapper.getUserApplication2(page, id, isCompleted));
    }
}

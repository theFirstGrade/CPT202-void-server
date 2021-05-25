package com.voidserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.ApplicationVO;
import com.voidserver.common.UserApplication;
import com.voidserver.entity.Application;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-06
 */
public interface ApplicationService extends IService<Application> {
    Page<ApplicationVO> getApplicationUser(Page<ApplicationVO> page);

    Page<ApplicationVO> getApplicationUser2(Page<ApplicationVO> page, @Param("searchAddress") String searchAddress, @Param("verifyCode") Integer verifyCode);

    Page<ApplicationVO> getApplicationUser3(Page<ApplicationVO> page, @Param("searchAddress") String searchAddress);

    Page<ApplicationVO> getApplicationUser4(Page<ApplicationVO> page, @Param("verifyCode") Integer verifyCode);

    Page<UserApplication> getUserApplication1(Page<UserApplication> page, @Param("id") Integer id, @Param("searchAddress") String searchAddress, @Param("isCompleted") Integer isCompleted);

    Page<UserApplication> getUserApplication2(Page<UserApplication> page, @Param("id") Integer id, @Param("isCompleted") Integer isCompleted);

}

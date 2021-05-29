package com.voidserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.ApplicationVO;
import com.voidserver.common.RentalApplicationVO;
import com.voidserver.common.UserApplication;
import com.voidserver.common.UserRentalApplication;
import com.voidserver.entity.RentalApplication;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-25
 */
public interface RentalApplicationService extends IService<RentalApplication> {

    Page<RentalApplicationVO> getApplicationUser(Page<RentalApplicationVO> page);

    Page<RentalApplicationVO> getApplicationUser2(Page<RentalApplicationVO> page, @Param("searchAddress") String searchAddress, @Param("verifyCode") Integer verifyCode);

    Page<RentalApplicationVO> getApplicationUser3(Page<RentalApplicationVO> page, @Param("searchAddress") String searchAddress);

    Page<RentalApplicationVO> getApplicationUser4(Page<RentalApplicationVO> page, @Param("verifyCode") Integer verifyCode);

    Page<RentalApplicationVO> getReturnApplicationUser1(Page<RentalApplicationVO> page);

    Page<RentalApplicationVO> getReturnApplicationUser2(Page<RentalApplicationVO> page, @Param("searchAddress") String searchAddress, @Param("verifyCode") Integer verifyCode);

    Page<RentalApplicationVO> getReturnApplicationUser3(Page<RentalApplicationVO> page, @Param("searchAddress") String searchAddress);

    Page<RentalApplicationVO> getReturnApplicationUser4(Page<RentalApplicationVO> page, @Param("verifyCode") Integer verifyCode);

    Page<UserRentalApplication> getUserRentalApplication1(Page<UserRentalApplication> page, @Param("id") Integer id, @Param("searchAddress") String searchAddress, @Param("isCompleted") Integer isCompleted);

    Page<UserRentalApplication> getUserRentalApplication2(Page<UserRentalApplication> page, @Param("id") Integer id, @Param("isCompleted") Integer isCompleted);

    Page<UserRentalApplication> getUserRentalApplication3(Page<UserRentalApplication> page, @Param("id") Integer id, @Param("searchAddress") String searchAddress, @Param("isCompleted") Integer isCompleted);

    Page<UserRentalApplication> getUserRentalApplication4(Page<UserRentalApplication> page, @Param("id") Integer id, @Param("isCompleted") Integer isCompleted);
}

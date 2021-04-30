package com.voidserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.ApplicationVO;
import com.voidserver.common.RentalApplicationVO;
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


}

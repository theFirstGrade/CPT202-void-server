package com.voidserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.RentalProductVO;
import com.voidserver.entity.RentalDepository;
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
public interface RentalDepositoryService extends IService<RentalDepository> {

    Page<RentalProductVO> getRentalProductDepository1(Page<RentalProductVO> page);

    Page<RentalProductVO> getRentalProductDepository2(Page<RentalProductVO> page, @Param("searchAddress") String searchAddress);

    Page<RentalProductVO> getRentalProductDepository3(Page<RentalProductVO> page, @Param("searchAddress") String searchAddress, @Param("searchName") String searchName);

    Page<RentalProductVO> getRentalProductDepository4(Page<RentalProductVO> page, @Param("searchCate") String searchCate);

    Page<RentalProductVO> getRentalProductDepository5(Page<RentalProductVO> page, @Param("searchCate") String searchCate, @Param("searchAddress") String searchAddress);

    Page<RentalProductVO> getRentalProductDepository6(Page<RentalProductVO> page, @Param("searchCate") String searchCate, @Param("searchAddress") String searchAddress, @Param("searchName") String searchName);

    Page<RentalProductVO> getRentalProductDepository7(Page<RentalProductVO> page, @Param("searchName") String searchName);

    Page<RentalProductVO> getRentalProductDepository8(Page<RentalProductVO> page, @Param("searchCate") String searchCate, @Param("searchName") String searchName);


}

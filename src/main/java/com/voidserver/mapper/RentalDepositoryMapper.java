package com.voidserver.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.RentalProductVO;
import com.voidserver.entity.RentalDepository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-25
 */
public interface RentalDepositoryMapper extends BaseMapper<RentalDepository> {

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id")
    List<RentalProductVO> getRentalProductDepository1(Page page);

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_depository.address='${searchAddress}'")
    List<RentalProductVO> getRentalProductDepository2(Page page, @Param("searchAddress") String searchAddress);

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_product.rental_name like '%${searchName}%' and rental_depository.address='${searchAddress}'")
    List<RentalProductVO> getRentalProductDepository3(Page page, @Param("searchAddress") String searchAddress, @Param("searchName") String searchName);

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_product.category='${searchCate}'")
    List<RentalProductVO> getRentalProductDepository4(Page page, @Param("searchCate") String searchCate);

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_product.category='${searchCate}' and rental_depository.address='${searchAddress}'")
    List<RentalProductVO> getRentalProductDepository5(Page page, @Param("searchCate") String searchCate, @Param("searchAddress") String searchAddress);

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_product.category='${searchCate}' and rental_depository.address='${searchAddress}' and rental_product.rental_name like '%${searchName}%'")
    List<RentalProductVO> getRentalProductDepository6(Page page, @Param("searchCate") String searchCate, @Param("searchAddress") String searchAddress, @Param("searchName") String searchName);

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_product.rental_name like '%${searchName}%'")
    List<RentalProductVO> getRentalProductDepository7(Page page, @Param("searchName") String searchName);

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_product.category='${searchCate}' and rental_product.rental_name like '%${searchName}%'")
    List<RentalProductVO> getRentalProductDepository8(Page page, @Param("searchCate") String searchCate, @Param("searchName") String searchName);

}

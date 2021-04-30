package com.voidserver.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.RentalProductVO;
import com.voidserver.entity.RentalProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-25
 */
public interface RentalProductMapper extends BaseMapper<RentalProduct> {

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_depository.address='${searchAddress}'")
    List<RentalProductVO> getRentalProductDepository(Page page, @Param("searchAddress") String searchAddress);

    //rental_product.rental_name,rental_product.image_src,rental_depository.* FROM rental_product,rental_depository
    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_depository.address='${searchAddress}'")
    List<RentalProductVO> getRentalProductDepository1(Page page, @Param("searchAddress") String searchAddress);

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_depository.address='${searchAddress}' and rental_product.rental_name like '%${searchName}%'")
    List<RentalProductVO> getRentalProductDepository2(Page page, @Param("searchAddress") String searchAddress, @Param("searchName") String searchName);

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_depository.address='${searchAddress}' and rental_product.category='${searchCate}'")
    List<RentalProductVO> getRentalProductDepository3(Page page, @Param("searchAddress") String searchAddress, @Param("searchCate") String searchCate);

    @Select("SELECT rental_product.rental_name,rental_product.image_src,rental_product.category,rental_product.unit,rental_depository.* FROM rental_product,rental_depository WHERE rental_depository.rental_id=rental_product.rental_id and rental_depository.address='${searchAddress}' and rental_product.category='${searchCate}' and rental_product.rental_name like '%${searchName}%'")
    List<RentalProductVO> getRentalProductDepository4(Page page, @Param("searchAddress") String searchAddress, @Param("searchCate") String searchCate, @Param("searchName") String searchName);

}

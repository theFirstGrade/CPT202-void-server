package com.voidserver.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.ApplicationVO;
import com.voidserver.common.RentalApplicationVO;
import com.voidserver.common.UserApplication;
import com.voidserver.common.UserRentalApplication;
import com.voidserver.entity.RentalApplication;
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
public interface RentalApplicationMapper extends BaseMapper<RentalApplication> {
    //and rental_depository.depository_id=rental_application.depository_id
    //    @Select("SELECT user.'username',rental_product.'rental_name',rental_application.* FROM rental_application,rental_product,user,rental_depository WHERE rental_application.deleted=0 and rental_application.user_id=user.id ")
//    @Select("SELECT user.username,rental_application.* FROM user,rental_application WHERE rental_application.user_id=1")
    @Select("SELECT user.username,rental_product.rental_name,rental_product.unit,rental_application.* FROM rental_application,rental_product,user,rental_depository WHERE rental_application.deleted=0 and rental_application.user_id=user.id and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id order by rental_application.application_id desc")
    List<RentalApplicationVO> getApplicationUser(Page page);

    // @Select("SELECT application.*,user.`username` FROM application,user WHERE application.deleted=0 and application.userId=user.id and application.address='${searchAddress}' and application.verifyCode=${verifyCode}")
    @Select("SELECT user.username,rental_product.rental_name,rental_product.unit,rental_application.* FROM rental_application,rental_product,user,rental_depository WHERE rental_application.deleted=0 and rental_application.address='${searchAddress}' and rental_application.verifyCode='${verifyCode}' and rental_application.user_id=user.id and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id order by rental_application.application_id desc")
    List<RentalApplicationVO> getApplicationUser2(Page page, @Param("searchAddress") String searchAddress, @Param("verifyCode") Integer verifyCode);

    //    @Select("SELECT application.*,user.`username` FROM application,user WHERE application.deleted=0 and application.userId=user.id and application.address='${searchAddress}'")
    @Select("SELECT user.username,rental_product.rental_name,rental_product.unit,rental_application.* FROM rental_application,rental_product,user,rental_depository WHERE rental_application.deleted=0 and rental_application.user_id=user.id and rental_application.address='${searchAddress}' and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id order by rental_application.application_id desc")
    List<RentalApplicationVO> getApplicationUser3(Page page, @Param("searchAddress") String searchAddress);

    //@Select("SELECT application.*,user.`username` FROM application,user WHERE application.deleted=0 and application.userId=user.id and application.verifyCode=${verifyCode}")
    @Select("SELECT user.username,rental_product.rental_name,rental_product.unit,rental_application.* FROM rental_application,rental_product,user,rental_depository WHERE rental_application.deleted=0 and rental_application.user_id=user.id and rental_application.verifyCode='${verifyCode}' and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id order by rental_application.application_id desc")
    List<RentalApplicationVO> getApplicationUser4(Page page, @Param("verifyCode") Integer verifyCode);

    @Select("SELECT user.username,rental_product.rental_name,rental_product.unit,rental_application.* FROM rental_application,rental_product,user,rental_depository WHERE rental_application.deleted=1 and rental_application.user_id=user.id and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id order by rental_application.application_id desc")
    List<RentalApplicationVO> getReturnApplicationUser1(Page page);

    @Select("SELECT user.username,rental_product.rental_name,rental_product.unit,rental_application.* FROM rental_application,rental_product,user,rental_depository WHERE rental_application.deleted=1 and rental_application.address='${searchAddress}' and rental_application.verifyCode='${verifyCode}' and rental_application.user_id=user.id and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id order by rental_application.application_id desc")
    List<RentalApplicationVO> getReturnApplicationUser2(Page page, @Param("searchAddress") String searchAddress, @Param("verifyCode") Integer verifyCode);

    //    @Select("SELECT application.*,user.`username` FROM application,user WHERE application.deleted=0 and application.userId=user.id and application.address='${searchAddress}'")
    @Select("SELECT user.username,rental_product.rental_name,rental_product.unit,rental_application.* FROM rental_application,rental_product,user,rental_depository WHERE rental_application.deleted=1 and rental_application.user_id=user.id and rental_application.address='${searchAddress}' and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id order by rental_application.application_id desc")
    List<RentalApplicationVO> getReturnApplicationUser3(Page page, @Param("searchAddress") String searchAddress);

    //@Select("SELECT application.*,user.`username` FROM application,user WHERE application.deleted=0 and application.userId=user.id and application.verifyCode=${verifyCode}")
    @Select("SELECT user.username,rental_product.rental_name,rental_product.unit,rental_application.* FROM rental_application,rental_product,user,rental_depository WHERE rental_application.deleted=1 and rental_application.user_id=user.id and rental_application.verifyCode='${verifyCode}' and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id order by rental_application.application_id desc")
    List<RentalApplicationVO> getReturnApplicationUser4(Page page, @Param("verifyCode") Integer verifyCode);

    @Select("SELECT rental_product.rental_name,rental_product.unit,rental_depository.address,rental_application.* FROM rental_application,rental_product,rental_depository WHERE rental_application.user_id=#{id} and rental_depository.address=#{searchAddress} and rental_application.deleted=#{isCompleted} and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id")
    List<UserRentalApplication> getUserRentalApplication1(Page page, @Param("id") Integer id, @Param("searchAddress") String searchAddress, @Param("isCompleted") Integer isCompleted);

    @Select("SELECT rental_product.rental_name,rental_product.unit,rental_depository.address,rental_application.* FROM rental_application,rental_product,rental_depository WHERE rental_application.user_id=#{id} and rental_application.deleted=#{isCompleted} and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id")
    List<UserRentalApplication> getUserRentalApplication2(Page page, @Param("id") Integer id, @Param("isCompleted") Integer isCompleted);

    @Select("SELECT rental_product.rental_name,rental_product.unit,rental_depository.address,rental_application.* FROM rental_application,rental_product,rental_depository WHERE rental_application.user_id=#{id} and rental_depository.address=#{searchAddress} and rental_application.deleted!=#{isCompleted} and rental_application.deleted!=5 and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id")
    List<UserRentalApplication> getUserRentalApplication3(Page page, @Param("id") Integer id, @Param("searchAddress") String searchAddress, @Param("isCompleted") Integer isCompleted);

    @Select("SELECT rental_product.rental_name,rental_product.unit,rental_depository.address,rental_application.* FROM rental_application,rental_product,rental_depository WHERE rental_application.user_id=#{id} and rental_application.deleted!=#{isCompleted} and rental_application.deleted!=5 and rental_application.depository_id=rental_depository.depository_id and rental_depository.rental_id=rental_product.rental_id")
    List<UserRentalApplication> getUserRentalApplication4(Page page, @Param("id") Integer id, @Param("isCompleted") Integer isCompleted);

//SELECT user.username,rental_product.rental_name,rental_application.* FROM rental_application,rental_product,user,rental_depository WHERE rental_application.deleted=0 and rental_application.user_id=user.id and rental_depository.rental_id=rental_product.rental_id and rental_application.depository_id=rental_depository.depository_id
}

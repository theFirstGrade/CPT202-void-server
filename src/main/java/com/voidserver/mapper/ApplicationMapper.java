package com.voidserver.mapper;

import com.voidserver.common.ApplicationVO;
import com.voidserver.entity.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-06
 */
public interface ApplicationMapper extends BaseMapper<Application> {

    @Select("SELECT application.*,user.`username` FROM application,user WHERE application.deleted=0 and application.userId=user.id")
    List<ApplicationVO> getApplicationUser(Page page);

    @Select("SELECT application.*,user.`username` FROM application,user WHERE application.deleted=0 and application.userId=user.id and application.address='${searchAddress}' and application.verifyCode=${verifyCode}")
    List<ApplicationVO> getApplicationUser2(Page page, @Param("searchAddress") String searchAddress, @Param("verifyCode") Integer verifyCode);

    @Select("SELECT application.*,user.`username` FROM application,user WHERE application.deleted=0 and application.userId=user.id and application.address='${searchAddress}'")
    List<ApplicationVO> getApplicationUser3(Page page, @Param("searchAddress") String searchAddress);

    @Select("SELECT application.*,user.`username` FROM application,user WHERE application.deleted=0 and application.userId=user.id and application.verifyCode=${verifyCode}")
    List<ApplicationVO> getApplicationUser4(Page page, @Param("verifyCode") Integer verifyCode);
// @Param("date") String date,@Param("type") String type
}

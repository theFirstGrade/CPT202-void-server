package com.voidserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "applicationId", type = IdType.AUTO)
    private Integer applicationId;

    @TableField("productId")
    private Integer productId;

    @TableField("productName")
    private String productName;

    private Integer number;

    private LocalDateTime date;

    private String address;

    @TableField("userId")
    private Integer userId;

    @TableField("verifyCode")
    private Integer verifyCode;

    private String director;

    private String remark;

    private String unit;

    private Integer deleted;

}

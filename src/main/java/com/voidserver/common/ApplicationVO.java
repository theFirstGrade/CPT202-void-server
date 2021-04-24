package com.voidserver.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApplicationVO implements Serializable {
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

    @TableLogic
    private Integer deleted;

    private String username;
}

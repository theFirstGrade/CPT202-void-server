package com.voidserver.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RentalApplicationVO implements Serializable {
    @TableId(value = "applicationId", type = IdType.AUTO)
    private Integer applicationId;

    private Integer depositoryId;

    private String rentalName;

    private Integer number;

    private LocalDateTime created;

    private LocalDateTime expired;

    private String address;

    private Integer userId;

    @TableField("verifyCode")
    private Integer verifyCode;

    private String director;

    private String remark;

    @TableLogic
    private Integer deleted;

    private String username;

    private String unit;

    private Integer rentalDay;
}

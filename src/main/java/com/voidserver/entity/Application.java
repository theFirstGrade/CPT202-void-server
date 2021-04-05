package com.voidserver.entity;

import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "applicationId", type = IdType.AUTO)
    private Integer applicationId;

    private LocalDateTime date;

    @TableField("userId")
    private Integer userId;

    private String director;

    private String remark;

    @TableField("productId")
    private Integer productId;

    @TableField("productName")
    private String productName;

    private String address;

    private Integer number;


}

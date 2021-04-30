package com.voidserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Depository implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "productId", type = IdType.AUTO)
    private Integer productId;

    @TableField("productName")
    private String productName;

    @TableField("imageSrc")
    private String imageSrc;

    private String address;

    private Integer stock;

    private String unit;

    private String category;

}

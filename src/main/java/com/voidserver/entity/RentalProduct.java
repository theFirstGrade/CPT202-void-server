package com.voidserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RentalProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rental_id", type = IdType.AUTO)
    private Integer rentalId;

    private String rentalName;

    private String imageSrc;

    private String category;

    private String unit;


}

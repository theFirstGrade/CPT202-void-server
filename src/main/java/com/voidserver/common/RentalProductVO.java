package com.voidserver.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class RentalProductVO implements Serializable {

    @TableId(value = "rental_id", type = IdType.AUTO)
    private Integer rentalId;

    private String rentalName;

    private String imageSrc;

    private String category;

    private String unit;

    private Integer stock;

    private Integer depositoryId;

    private String address;
}

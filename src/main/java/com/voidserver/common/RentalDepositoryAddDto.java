package com.voidserver.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class RentalDepositoryAddDto {

    @TableId(value = "depository_id", type = IdType.AUTO)
    private Integer depositoryId;

    private String rentalId;

    private String address;

    private Integer stock;

    private String rentalName;

    private String imageSrc;

    private String category;

    private String unit;

}

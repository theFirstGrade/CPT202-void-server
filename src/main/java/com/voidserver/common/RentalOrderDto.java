package com.voidserver.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class RentalOrderDto implements Serializable {


    //    @NotBlank(message = "密码不能为空")
    private String address;

    private Integer number;

    private String unit;

    private String rentalName;

    private Integer days;

    private Integer depositoryId;


}
package com.voidserver.common;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class OrderDto implements Serializable {

    //    @NotBlank(message = "昵称不能为空")
    private Integer productId;

    //    @NotBlank(message = "密码不能为空")
    private String address;

    private Integer number;

    private String unit;

    private String productName;

}
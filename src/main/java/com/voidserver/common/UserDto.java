package com.voidserver.common;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

//    @NotBlank(message = "昵称不能为空")
    private Integer userId;

//    @NotBlank(message = "密码不能为空")
    private String email;
}
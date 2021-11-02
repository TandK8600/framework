package com.TandK.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author TandK
 * @since 2021/10/2 14:20
 */
@Data
public class LoginVO {

    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;
}

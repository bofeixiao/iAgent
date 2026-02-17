package com.idataai.auth.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录DTO
 *
 * @author iDataAI
 */
@Data
public class LoginDTO {

    /**
     * 邮箱或手机号
     */
    @NotBlank(message = "邮箱/手机号不能为空")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}

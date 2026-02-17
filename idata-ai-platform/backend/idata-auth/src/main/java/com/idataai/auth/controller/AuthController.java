package com.idataai.auth.controller;

import com.idataai.auth.domain.dto.LoginDTO;
import com.idataai.auth.domain.dto.RegisterDTO;
import com.idataai.auth.domain.vo.LoginVO;
import com.idataai.auth.service.AuthService;
import com.idataai.common.core.annotation.Log;
import com.idataai.common.core.domain.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 *
 * @author iDataAI
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Log(value = "用户登录", type = Log.OperationType.LOGIN)
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = authService.login(loginDTO);
        return Result.success("登录成功", loginVO);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @Log("用户注册")
    public Result<Void> register(@Valid @RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);
        return Result.success("注册成功");
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @Log(value = "用户登出", type = Log.OperationType.LOGOUT)
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return Result.success("登出成功");
    }

    /**
     * 刷新Token
     */
    @PostMapping("/refresh")
    public Result<String> refreshToken(@RequestHeader("Authorization") String token) {
        String newToken = authService.refreshToken(token);
        return Result.success("刷新成功", newToken);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public Result<LoginVO> getUserInfo() {
        LoginVO userInfo = authService.getCurrentUser();
        return Result.success(userInfo);
    }
}

package com.idataai.auth.service;

import com.idataai.auth.domain.dto.LoginDTO;
import com.idataai.auth.domain.dto.RegisterDTO;
import com.idataai.auth.domain.vo.LoginVO;

/**
 * 认证服务接口
 *
 * @author iDataAI
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 用户注册
     *
     * @param registerDTO 注册信息
     */
    void register(RegisterDTO registerDTO);

    /**
     * 用户登出
     *
     * @param token Token
     */
    void logout(String token);

    /**
     * 刷新Token
     *
     * @param token 旧Token
     * @return 新Token
     */
    String refreshToken(String token);

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    LoginVO getCurrentUser();
}

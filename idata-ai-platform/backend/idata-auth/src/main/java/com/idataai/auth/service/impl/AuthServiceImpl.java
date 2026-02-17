package com.idataai.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.idataai.auth.domain.dto.LoginDTO;
import com.idataai.auth.domain.dto.RegisterDTO;
import com.idataai.auth.domain.entity.User;
import com.idataai.auth.domain.vo.LoginVO;
import com.idataai.auth.mapper.UserMapper;
import com.idataai.auth.service.AuthService;
import com.idataai.common.core.constant.Constants;
import com.idataai.common.core.constant.RedisKeyConstants;
import com.idataai.common.core.exception.BusinessException;
import com.idataai.common.core.exception.ErrorCode;
import com.idataai.common.redis.service.RedisService;
import com.idataai.common.security.util.JwtUtil;
import com.idataai.common.security.util.PasswordUtil;
import com.idataai.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 认证服务实现
 *
 * @author iDataAI
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginVO login(LoginDTO loginDTO) {
        // 1. 查询用户
        User user = findUserByAccount(loginDTO.getAccount());
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        // 2. 检查账号状态
        if (Constants.USER_STATUS_DISABLED.equals(user.getStatus())) {
            throw new BusinessException(ErrorCode.ACCOUNT_DISABLED);
        }

        // 3. 验证密码
        if (!PasswordUtil.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }

        // 4. 生成Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        // 5. 缓存Token到Redis
        redisService.setEx(
                RedisKeyConstants.getTokenKey(token),
                user.getId(),
                RedisKeyConstants.TOKEN_EXPIRE_TIME
        );

        // 6. 缓存用户信息到Redis
        redisService.setEx(
                RedisKeyConstants.getUserInfoKey(user.getId()),
                user,
                RedisKeyConstants.CACHE_EXPIRE_TIME
        );

        // 7. 更新最后登录信息
        userMapper.updateLastLoginInfo(user.getId(), getClientIp());

        // 8. 构造返回结果
        return buildLoginVO(user, token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterDTO registerDTO) {
        // 1. 检查邮箱是否已存在
        if (StrUtil.isNotBlank(registerDTO.getEmail())) {
            User existUser = userMapper.selectByEmail(registerDTO.getEmail());
            if (existUser != null) {
                throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }
        }

        // 2. 检查手机号是否已存在
        if (StrUtil.isNotBlank(registerDTO.getPhone())) {
            User existUser = userMapper.selectByPhone(registerDTO.getPhone());
            if (existUser != null) {
                throw new BusinessException(ErrorCode.PHONE_ALREADY_EXISTS);
            }
        }

        // 3. 验证密码强度
        if (!PasswordUtil.validatePasswordStrength(registerDTO.getPassword())) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码必须包含大小写字母和数字，长度8-20位");
        }

        // 4. 创建用户
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setPassword(PasswordUtil.encode(registerDTO.getPassword()));
        user.setNickname(registerDTO.getNickname());
        user.setUsername(registerDTO.getEmail() != null ? registerDTO.getEmail().split("@")[0] : registerDTO.getPhone());
        user.setStatus(Constants.USER_STATUS_NORMAL);
        user.setVipLevel(Constants.DEFAULT_VIP_LEVEL);
        user.setCredits(Constants.REGISTER_DEFAULT_CREDITS);
        user.setRegisterSource("web");

        userMapper.insert(user);

        // 5. 处理邀请码(如果有)
        if (StrUtil.isNotBlank(registerDTO.getInvitationCode())) {
            // TODO: 处理邀请奖励
            log.info("User registered with invitation code: {}", registerDTO.getInvitationCode());
        }
    }

    @Override
    public void logout(String token) {
        if (StrUtil.isBlank(token)) {
            return;
        }
        
        // 移除Bearer前缀
        if (token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.substring(Constants.TOKEN_PREFIX.length());
        }

        // 从Redis中删除Token
        redisService.delete(RedisKeyConstants.getTokenKey(token));

        // 删除用户信息缓存
        Long userId = jwtUtil.getUserIdFromToken(token);
        if (userId != null) {
            redisService.delete(RedisKeyConstants.getUserInfoKey(userId));
        }
    }

    @Override
    public String refreshToken(String token) {
        if (StrUtil.isBlank(token)) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }

        // 移除Bearer前缀
        if (token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.substring(Constants.TOKEN_PREFIX.length());
        }

        // 验证旧Token
        if (!jwtUtil.validateToken(token)) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }

        // 生成新Token
        String newToken = jwtUtil.refreshToken(token);
        if (newToken == null) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }

        // 获取用户ID
        Long userId = jwtUtil.getUserIdFromToken(token);
        if (userId == null) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }

        // 删除旧Token
        redisService.delete(RedisKeyConstants.getTokenKey(token));

        // 缓存新Token
        redisService.setEx(
                RedisKeyConstants.getTokenKey(newToken),
                userId,
                RedisKeyConstants.TOKEN_EXPIRE_TIME
        );

        return newToken;
    }

    @Override
    public LoginVO getCurrentUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        // 先从Redis获取
        User user = redisService.get(RedisKeyConstants.getUserInfoKey(userId), User.class);
        if (user == null) {
            // Redis没有，从数据库查询
            user = userMapper.selectById(userId);
            if (user == null) {
                throw new BusinessException(ErrorCode.USER_NOT_FOUND);
            }
            // 缓存到Redis
            redisService.setEx(
                    RedisKeyConstants.getUserInfoKey(userId),
                    user,
                    RedisKeyConstants.CACHE_EXPIRE_TIME
            );
        }

        return buildLoginVO(user, null);
    }

    /**
     * 根据账号查找用户
     */
    private User findUserByAccount(String account) {
        // 先尝试邮箱
        if (account.contains("@")) {
            return userMapper.selectByEmail(account);
        }
        // 再尝试手机号
        if (account.matches("^1[3-9]\\d{9}$")) {
            return userMapper.selectByPhone(account);
        }
        // 最后尝试用户名
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, account);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 构建登录返回VO
     */
    private LoginVO buildLoginVO(User user, String token) {
        LoginVO vo = new LoginVO();
        BeanUtils.copyProperties(user, vo);
        vo.setUserId(user.getId());
        vo.setToken(token);
        // 密码不返回
        return vo;
    }

    /**
     * 获取客户端IP
     */
    private String getClientIp() {
        // TODO: 从HttpServletRequest获取真实IP
        return "127.0.0.1";
    }
}

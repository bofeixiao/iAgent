package com.idataai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idataai.common.core.domain.PageResult;
import com.idataai.common.core.exception.BusinessException;
import com.idataai.common.core.exception.ErrorCode;
import com.idataai.common.redis.service.RedisService;
import com.idataai.system.domain.dto.UserDTO;
import com.idataai.system.domain.dto.UserQueryDTO;
import com.idataai.system.domain.entity.User;
import com.idataai.system.domain.vo.UserVO;
import com.idataai.system.mapper.UserMapper;
import com.idataai.system.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 *
 * @author iDataAI
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    private final RedisService redisService;

    private static final String USER_CACHE_PREFIX = "user:info:";
    private static final long USER_CACHE_TIMEOUT = 1; // 小时

    @Override
    public PageResult<UserVO> getUserList(UserQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(queryDTO.getUsername()), User::getUsername, queryDTO.getUsername())
                .like(StringUtils.hasText(queryDTO.getNickname()), User::getNickname, queryDTO.getNickname())
                .eq(StringUtils.hasText(queryDTO.getEmail()), User::getEmail, queryDTO.getEmail())
                .eq(StringUtils.hasText(queryDTO.getPhone()), User::getPhone, queryDTO.getPhone())
                .eq(queryDTO.getStatus() != null, User::getStatus, queryDTO.getStatus())
                .eq(queryDTO.getVipLevel() != null, User::getVipLevel, queryDTO.getVipLevel())
                .ge(StringUtils.hasText(queryDTO.getStartDate()), User::getCreateTime, queryDTO.getStartDate())
                .le(StringUtils.hasText(queryDTO.getEndDate()), User::getCreateTime, queryDTO.getEndDate())
                .orderByDesc(User::getCreateTime);

        // 分页查询
        Page<User> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        IPage<User> userPage = userMapper.selectPage(page, wrapper);

        // 转换为VO
        List<UserVO> userVOList = userPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.build(userVOList, userPage.getTotal(), userPage.getCurrent(), userPage.getSize());
    }

    @Override
    public UserVO getUserById(Long id) {
        // 先从缓存获取
        String cacheKey = USER_CACHE_PREFIX + id;
        UserVO userVO = redisService.get(cacheKey, UserVO.class);
        if (userVO != null) {
            return userVO;
        }

        // 从数据库查询
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        userVO = convertToVO(user);

        // 更新缓存
        redisService.set(cacheKey, userVO, USER_CACHE_TIMEOUT, TimeUnit.HOURS);

        return userVO;
    }

    @Override
    public void updateUser(Long id, UserDTO userDTO) {
        // 检查用户是否存在
        User existUser = userMapper.selectById(id);
        if (existUser == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        // 检查邮箱是否已被其他用户使用
        if (StringUtils.hasText(userDTO.getEmail()) && !userDTO.getEmail().equals(existUser.getEmail())) {
            User emailUser = userMapper.selectByEmail(userDTO.getEmail());
            if (emailUser != null && !emailUser.getId().equals(id)) {
                throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }
        }

        // 检查手机号是否已被其他用户使用
        if (StringUtils.hasText(userDTO.getPhone()) && !userDTO.getPhone().equals(existUser.getPhone())) {
            User phoneUser = userMapper.selectByPhone(userDTO.getPhone());
            if (phoneUser != null && !phoneUser.getId().equals(id)) {
                throw new BusinessException(ErrorCode.PHONE_ALREADY_EXISTS);
            }
        }

        // 更新用户信息
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setId(id);
        userMapper.updateById(user);

        // 删除缓存
        redisService.delete(USER_CACHE_PREFIX + id);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        user.setStatus(status);
        userMapper.updateById(user);

        // 删除缓存
        redisService.delete(USER_CACHE_PREFIX + id);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        // 逻辑删除
        userMapper.deleteById(id);

        // 删除缓存
        redisService.delete(USER_CACHE_PREFIX + id);
    }

    /**
     * 转换为VO对象
     */
    private UserVO convertToVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}

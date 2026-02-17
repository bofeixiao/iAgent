package com.idataai.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.idataai.auth.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper
 *
 * @author iDataAI
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户
     */
    User selectByEmail(@Param("email") String email);

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户
     */
    User selectByPhone(@Param("phone") String phone);

    /**
     * 更新最后登录信息
     *
     * @param userId 用户ID
     * @param loginIp 登录IP
     */
    void updateLastLoginInfo(@Param("userId") Long userId, @Param("loginIp") String loginIp);
}

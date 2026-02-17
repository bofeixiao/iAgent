package com.idataai.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.idataai.system.domain.entity.User;
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
}

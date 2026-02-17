package com.idataai.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.idataai.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class User extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码(BCrypt加密)
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别: 0-未知, 1-男, 2-女
     */
    private Integer gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 账号状态: 0-禁用, 1-正常
     */
    private Integer status;

    /**
     * VIP等级: 0-普通, 1-银卡, 2-金卡, 3-铂金卡
     */
    private Integer vipLevel;

    /**
     * VIP到期时间
     */
    private LocalDateTime vipExpireTime;

    /**
     * 积分余额
     */
    private Integer credits;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 注册来源: web, mobile, wechat
     */
    private String registerSource;
}

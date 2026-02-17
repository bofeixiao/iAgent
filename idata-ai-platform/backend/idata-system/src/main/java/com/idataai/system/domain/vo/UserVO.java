package com.idataai.system.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户VO
 *
 * @author iDataAI
 */
@Data
public class UserVO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 行业
     */
    private String industry;

    /**
     * 状态：0-禁用，1-正常
     */
    private Integer status;

    /**
     * VIP等级
     */
    private Integer vipLevel;

    /**
     * VIP过期时间
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
     * 注册来源
     */
    private String registerSource;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}

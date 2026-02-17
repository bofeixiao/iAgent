package com.idataai.auth.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录返回VO
 *
 * @author iDataAI
 */
@Data
public class LoginVO {

    /**
     * Token
     */
    private String token;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * VIP等级
     */
    private Integer vipLevel;

    /**
     * VIP到期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime vipExpireTime;

    /**
     * 积分余额
     */
    private Integer credits;
}

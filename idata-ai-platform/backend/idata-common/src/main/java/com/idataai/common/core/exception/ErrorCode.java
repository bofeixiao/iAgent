package com.idataai.common.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码枚举
 *
 * @author iDataAI
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 系统错误 1xxxx
    SUCCESS(200, "操作成功"),
    SYSTEM_ERROR(10000, "系统异常"),
    PARAM_ERROR(10001, "参数错误"),
    REQUEST_METHOD_ERROR(10002, "请求方法错误"),
    
    // 认证授权错误 2xxxx
    UNAUTHORIZED(20000, "未登录或登录已过期"),
    FORBIDDEN(20001, "无权限访问"),
    TOKEN_INVALID(20002, "Token无效"),
    TOKEN_EXPIRED(20003, "Token已过期"),
    LOGIN_FAILED(20004, "登录失败"),
    ACCOUNT_DISABLED(20005, "账号已被禁用"),
    PASSWORD_ERROR(20006, "密码错误"),
    
    // 用户错误 3xxxx
    USER_NOT_FOUND(30000, "用户不存在"),
    USER_ALREADY_EXISTS(30001, "用户已存在"),
    EMAIL_ALREADY_EXISTS(30002, "邮箱已被注册"),
    PHONE_ALREADY_EXISTS(30003, "手机号已被注册"),
    VERIFICATION_CODE_ERROR(30004, "验证码错误"),
    
    // 应用错误 4xxxx
    APP_NOT_FOUND(40000, "应用不存在"),
    CATEGORY_NOT_FOUND(40001, "分类不存在"),
    INSTANCE_NOT_FOUND(40002, "实例不存在"),
    INSTANCE_CREATE_FAILED(40003, "实例创建失败"),
    
    // 内容错误 5xxxx
    ARTICLE_NOT_FOUND(50000, "文章不存在"),
    THINKING_NOT_FOUND(50001, "思维记录不存在"),
    THINKING_ALREADY_EXISTS(50002, "思维记录已存在"),
    THINKING_QUOTA_EXCEEDED(50003, "思维创建配额已用完"),
    
    // 支付错误 6xxxx
    INSUFFICIENT_CREDITS(60000, "积分不足"),
    ORDER_NOT_FOUND(60001, "订单不存在"),
    ORDER_PAID(60002, "订单已支付"),
    PAYMENT_FAILED(60003, "支付失败"),
    COUPON_NOT_FOUND(60004, "优惠券不存在"),
    COUPON_EXPIRED(60005, "优惠券已过期"),
    
    // AI服务错误 7xxxx
    AI_SERVICE_ERROR(70000, "AI服务异常"),
    AI_MODEL_NOT_AVAILABLE(70001, "AI模型不可用"),
    AI_GENERATE_FAILED(70002, "AI生成失败"),
    
    // 营销错误 8xxxx
    CAMPAIGN_NOT_FOUND(80000, "活动不存在"),
    INVITATION_CODE_INVALID(80001, "邀请码无效"),
    INVITATION_ALREADY_USED(80002, "邀请码已使用");

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误消息
     */
    private final String message;
}

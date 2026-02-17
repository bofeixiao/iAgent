package com.idataai.common.core.constant;

/**
 * 系统常量
 *
 * @author iDataAI
 */
public class Constants {

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * HTTP请求
     */
    public static final String HTTP = "http://";

    /**
     * HTTPS请求
     */
    public static final String HTTPS = "https://";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "登录成功";

    /**
     * 注销成功
     */
    public static final String LOGOUT_SUCCESS = "注销成功";

    /**
     * 注册成功
     */
    public static final String REGISTER_SUCCESS = "注册成功";

    /**
     * 操作成功
     */
    public static final String OPERATION_SUCCESS = "操作成功";

    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Token请求头
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 用户ID请求头
     */
    public static final String USER_ID_HEADER = "X-User-Id";

    /**
     * 默认页码
     */
    public static final Long DEFAULT_PAGE_NUM = 1L;

    /**
     * 默认每页条数
     */
    public static final Long DEFAULT_PAGE_SIZE = 10L;

    /**
     * 最大每页条数
     */
    public static final Long MAX_PAGE_SIZE = 100L;

    /**
     * 注册默认积分
     */
    public static final Integer REGISTER_DEFAULT_CREDITS = 5;

    /**
     * 默认VIP等级(普通用户)
     */
    public static final Integer DEFAULT_VIP_LEVEL = 0;

    /**
     * 账号状态 - 正常
     */
    public static final Integer USER_STATUS_NORMAL = 1;

    /**
     * 账号状态 - 禁用
     */
    public static final Integer USER_STATUS_DISABLED = 0;

    /**
     * 删除标记 - 未删除
     */
    public static final Integer NOT_DELETED = 0;

    /**
     * 删除标记 - 已删除
     */
    public static final Integer DELETED = 1;

    /**
     * 实例状态 - 处理中
     */
    public static final Integer INSTANCE_STATUS_PROCESSING = 0;

    /**
     * 实例状态 - 成功
     */
    public static final Integer INSTANCE_STATUS_SUCCESS = 1;

    /**
     * 实例状态 - 失败
     */
    public static final Integer INSTANCE_STATUS_FAILED = 2;

    /**
     * 订单状态 - 待支付
     */
    public static final Integer ORDER_STATUS_PENDING = 0;

    /**
     * 订单状态 - 已支付
     */
    public static final Integer ORDER_STATUS_PAID = 1;

    /**
     * 订单状态 - 已取消
     */
    public static final Integer ORDER_STATUS_CANCELLED = 2;

    /**
     * 订单状态 - 已退款
     */
    public static final Integer ORDER_STATUS_REFUNDED = 3;
}

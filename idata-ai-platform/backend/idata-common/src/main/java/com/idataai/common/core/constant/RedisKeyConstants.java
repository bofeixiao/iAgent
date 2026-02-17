package com.idataai.common.core.constant;

/**
 * Redis Key常量
 *
 * @author iDataAI
 */
public class RedisKeyConstants {

    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "token:";

    /**
     * 用户信息前缀
     */
    public static final String USER_INFO_PREFIX = "user:info:";

    /**
     * 验证码前缀
     */
    public static final String CAPTCHA_PREFIX = "captcha:";

    /**
     * 短信验证码前缀
     */
    public static final String SMS_CODE_PREFIX = "sms:code:";

    /**
     * 邮箱验证码前缀
     */
    public static final String EMAIL_CODE_PREFIX = "email:code:";

    /**
     * 应用列表缓存
     */
    public static final String APP_LIST_PREFIX = "app:list:";

    /**
     * 应用详情缓存
     */
    public static final String APP_DETAIL_PREFIX = "app:detail:";

    /**
     * 文章列表缓存
     */
    public static final String ARTICLE_LIST_PREFIX = "article:list:";

    /**
     * 文章详情缓存
     */
    public static final String ARTICLE_DETAIL_PREFIX = "article:detail:";

    /**
     * 热点趋势缓存
     */
    public static final String TRENDING_LIST = "trending:list";

    /**
     * 会员等级缓存
     */
    public static final String MEMBERSHIP_LEVELS = "membership:levels";

    /**
     * 系统配置缓存
     */
    public static final String SYS_CONFIG_PREFIX = "sys:config:";

    /**
     * 限流前缀
     */
    public static final String RATE_LIMIT_PREFIX = "rate:limit:";

    /**
     * 分布式锁前缀
     */
    public static final String LOCK_PREFIX = "lock:";

    /**
     * Token过期时间(秒) - 7天
     */
    public static final long TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60;

    /**
     * 验证码过期时间(秒) - 5分钟
     */
    public static final long CAPTCHA_EXPIRE_TIME = 5 * 60;

    /**
     * 缓存过期时间(秒) - 1小时
     */
    public static final long CACHE_EXPIRE_TIME = 60 * 60;

    /**
     * 获取Token Key
     */
    public static String getTokenKey(String token) {
        return TOKEN_PREFIX + token;
    }

    /**
     * 获取用户信息Key
     */
    public static String getUserInfoKey(Long userId) {
        return USER_INFO_PREFIX + userId;
    }

    /**
     * 获取验证码Key
     */
    public static String getCaptchaKey(String uuid) {
        return CAPTCHA_PREFIX + uuid;
    }

    /**
     * 获取短信验证码Key
     */
    public static String getSmsCodeKey(String phone) {
        return SMS_CODE_PREFIX + phone;
    }

    /**
     * 获取邮箱验证码Key
     */
    public static String getEmailCodeKey(String email) {
        return EMAIL_CODE_PREFIX + email;
    }

    /**
     * 获取应用详情Key
     */
    public static String getAppDetailKey(Long appId) {
        return APP_DETAIL_PREFIX + appId;
    }

    /**
     * 获取文章详情Key
     */
    public static String getArticleDetailKey(Long articleId) {
        return ARTICLE_DETAIL_PREFIX + articleId;
    }

    /**
     * 获取系统配置Key
     */
    public static String getSysConfigKey(String configKey) {
        return SYS_CONFIG_PREFIX + configKey;
    }

    /**
     * 获取限流Key
     */
    public static String getRateLimitKey(String key) {
        return RATE_LIMIT_PREFIX + key;
    }

    /**
     * 获取分布式锁Key
     */
    public static String getLockKey(String key) {
        return LOCK_PREFIX + key;
    }
}

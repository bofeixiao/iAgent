package com.idataai.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 *
 * @author iDataAI
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 操作描述
     */
    String value() default "";

    /**
     * 操作类型
     */
    OperationType type() default OperationType.OTHER;

    /**
     * 是否保存请求参数
     */
    boolean saveParams() default true;

    /**
     * 是否保存响应结果
     */
    boolean saveResult() default false;

    /**
     * 操作类型枚举
     */
    enum OperationType {
        /**
         * 查询
         */
        QUERY,

        /**
         * 新增
         */
        INSERT,

        /**
         * 修改
         */
        UPDATE,

        /**
         * 删除
         */
        DELETE,

        /**
         * 导出
         */
        EXPORT,

        /**
         * 导入
         */
        IMPORT,

        /**
         * 登录
         */
        LOGIN,

        /**
         * 登出
         */
        LOGOUT,

        /**
         * 其他
         */
        OTHER
    }
}

package com.idataai.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验注解
 *
 * @author iDataAI
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {

    /**
     * 需要的权限标识
     */
    String[] value() default {};

    /**
     * 验证模式: AND-所有权限都需要, OR-有任一权限即可
     */
    Logical logical() default Logical.AND;

    enum Logical {
        /**
         * AND - 需要所有权限
         */
        AND,

        /**
         * OR - 需要任一权限
         */
        OR
    }
}

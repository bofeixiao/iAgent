package com.idataai.auth.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.idataai.common.security.util.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis Plus配置
 *
 * @author iDataAI
 */
@Configuration
@MapperScan("com.idataai.auth.mapper")
public class MyBatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 自动填充处理器
     */
    @Component
    public static class MyMetaObjectHandler implements MetaObjectHandler {

        @Override
        public void insertFill(MetaObject metaObject) {
            LocalDateTime now = LocalDateTime.now();
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
            this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
            this.strictInsertFill(metaObject, "isDeleted", Integer.class, 0);

            Long userId = SecurityUtil.getCurrentUserId();
            if (userId != null) {
                this.strictInsertFill(metaObject, "createdBy", Long.class, userId);
                this.strictInsertFill(metaObject, "updatedBy", Long.class, userId);
            }
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

            Long userId = SecurityUtil.getCurrentUserId();
            if (userId != null) {
                this.strictUpdateFill(metaObject, "updatedBy", Long.class, userId);
            }
        }
    }
}

package com.idataai.system.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.idataai.common.security.util.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MyBatis Plus配置
 *
 * @author iDataAI
 */
@Configuration
@MapperScan("com.idataai.system.mapper")
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
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "isDeleted", Integer.class, 0);
                
                try {
                    Long currentUserId = SecurityUtil.getCurrentUserId();
                    this.strictInsertFill(metaObject, "createdBy", Long.class, currentUserId);
                    this.strictInsertFill(metaObject, "updatedBy", Long.class, currentUserId);
                } catch (Exception ignored) {
                    // 如果获取用户ID失败，则不填充
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
                
                try {
                    Long currentUserId = SecurityUtil.getCurrentUserId();
                    this.strictUpdateFill(metaObject, "updatedBy", Long.class, currentUserId);
                } catch (Exception ignored) {
                    // 如果获取用户ID失败，则不填充
                }
            }
        };
    }
}

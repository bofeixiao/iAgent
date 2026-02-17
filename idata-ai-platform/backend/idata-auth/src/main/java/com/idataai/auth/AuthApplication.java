package com.idataai.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 认证服务启动类
 *
 * @author iDataAI
 */
@SpringBootApplication(scanBasePackages = {"com.idataai.auth", "com.idataai.common"})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  iData AI Platform - Auth Service");
        System.out.println("  认证服务启动成功");
        System.out.println("========================================\n");
    }
}

package com.idataai.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用服务启动类
 *
 * @author iDataAI
 */
@SpringBootApplication(scanBasePackages = {"com.idataai.app", "com.idataai.common"})
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  iData AI Platform - Application Service");
        System.out.println("  应用服务启动成功");
        System.out.println("========================================\n");
    }
}

package com.idataai.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统管理服务启动类
 *
 * @author iDataAI
 */
@SpringBootApplication(scanBasePackages = {"com.idataai.system", "com.idataai.common"})
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  iData AI Platform - System Service");
        System.out.println("  系统管理服务启动成功");
        System.out.println("========================================\n");
    }
}

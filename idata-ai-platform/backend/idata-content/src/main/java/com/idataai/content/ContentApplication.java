package com.idataai.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 内容服务启动类
 *
 * @author iDataAI
 */
@SpringBootApplication(scanBasePackages = {"com.idataai.content", "com.idataai.common"})
public class ContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  iData AI Platform - Content Service");
        System.out.println("  内容服务启动成功");
        System.out.println("========================================\n");
    }
}

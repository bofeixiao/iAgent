package com.idataai.marketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 营销服务启动类
 *
 * @author iDataAI
 */
@SpringBootApplication(scanBasePackages = {"com.idataai.marketing", "com.idataai.common"})
public class MarketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketingApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  iData AI Platform - Marketing Service");
        System.out.println("  营销服务启动成功");
        System.out.println("========================================\n");
    }
}

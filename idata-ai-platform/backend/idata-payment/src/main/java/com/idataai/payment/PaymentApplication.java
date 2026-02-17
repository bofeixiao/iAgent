package com.idataai.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 支付服务启动类
 *
 * @author iDataAI
 */
@SpringBootApplication(scanBasePackages = {"com.idataai.payment", "com.idataai.common"})
public class PaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  iData AI Platform - Payment Service");
        System.out.println("  支付服务启动成功");
        System.out.println("========================================\n");
    }
}

package com.idataai.payment.domain.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单VO
 *
 * @author iDataAI
 */
@Data
public class OrderVO {
    private String orderNo;
    private Integer orderType;
    private BigDecimal amount;
    private Integer paymentMethod;
    private Integer status;
    private LocalDateTime paymentTime;
}

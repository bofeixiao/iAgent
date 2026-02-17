package com.idataai.payment.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.idataai.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_payment_order")
public class Order extends BaseEntity {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单类型：1-积分充值，2-会员购买
     */
    private Integer orderType;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 支付方式：1-支付宝，2-微信，3-余额
     */
    private Integer paymentMethod;

    /**
     * 订单状态：0-待支付，1-已支付，2-已关闭，3-已退款
     */
    private Integer status;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 第三方订单号
     */
    private String thirdPartyOrderNo;
}

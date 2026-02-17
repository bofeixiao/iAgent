package com.idataai.payment.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.idataai.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 积分流水实体类
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_payment_credit_log")
public class CreditLog extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 变更类型：1-充值，2-消费，3-退款，4-赠送，5-注册奖励
     */
    private Integer changeType;

    /**
     * 变更金额（正数为增加，负数为减少）
     */
    private Integer changeAmount;

    /**
     * 变更前余额
     */
    private Integer balanceBefore;

    /**
     * 变更后余额
     */
    private Integer balanceAfter;

    /**
     * 关联ID（订单ID或实例ID）
     */
    private Long relatedId;

    /**
     * 关联类型
     */
    private String relatedType;

    /**
     * 备注
     */
    private String remark;
}

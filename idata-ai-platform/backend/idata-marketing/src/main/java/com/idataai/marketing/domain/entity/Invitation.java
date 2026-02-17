package com.idataai.marketing.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.idataai.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 邀请记录实体类
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_marketing_invitation")
public class Invitation extends BaseEntity {

    /**
     * 邀请人ID
     */
    private Long inviterId;

    /**
     * 被邀请人ID
     */
    private Long inviteeId;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 奖励积分
     */
    private Integer rewardCredits;

    /**
     * 状态：0-待完成，1-已完成
     */
    private Integer status;
}

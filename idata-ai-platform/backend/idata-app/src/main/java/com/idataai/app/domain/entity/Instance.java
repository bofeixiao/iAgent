package com.idataai.app.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.idataai.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 应用实例实体类
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_app_instance", autoResultMap = true)
public class Instance extends BaseEntity {

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 实例名称
     */
    private String instanceName;

    /**
     * 输入参数（JSON）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> inputParams;

    /**
     * 输出结果（JSON）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> outputResult;

    /**
     * 状态：0-待处理，1-处理中，2-成功，3-失败
     */
    private Integer status;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 消耗积分
     */
    private Integer creditsUsed;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 任务ID（消息队列）
     */
    private String taskId;
}

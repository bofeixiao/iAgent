package com.idataai.app.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 实例VO
 *
 * @author iDataAI
 */
@Data
public class InstanceVO {

    /**
     * 实例ID
     */
    private Long id;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用图标
     */
    private String appIcon;

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
    private Map<String, Object> inputParams;

    /**
     * 输出结果（JSON）
     */
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
     * 创建时间
     */
    private LocalDateTime createTime;
}

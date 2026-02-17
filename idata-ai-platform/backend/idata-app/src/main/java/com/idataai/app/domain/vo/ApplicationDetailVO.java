package com.idataai.app.domain.vo;

import lombok.Data;

import java.util.Map;

/**
 * 应用详情VO
 *
 * @author iDataAI
 */
@Data
public class ApplicationDetailVO {

    /**
     * 应用ID
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用图标
     */
    private String appIcon;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 应用类型
     */
    private String appType;

    /**
     * 积分消耗
     */
    private Integer creditsCost;

    /**
     * 配置结构（JSON）
     */
    private Map<String, Object> configSchema;

    /**
     * 输入字段（JSON）
     */
    private Map<String, Object> inputFields;

    /**
     * 输出文件（JSON）
     */
    private Map<String, Object> outputFiles;

    /**
     * 是否热门
     */
    private Boolean isHot;

    /**
     * 是否推荐
     */
    private Boolean isRecommended;

    /**
     * 使用次数
     */
    private Integer useCount;
}

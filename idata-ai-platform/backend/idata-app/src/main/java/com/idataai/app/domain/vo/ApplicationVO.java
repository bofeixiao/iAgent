package com.idataai.app.domain.vo;

import lombok.Data;

/**
 * 应用VO
 *
 * @author iDataAI
 */
@Data
public class ApplicationVO {

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

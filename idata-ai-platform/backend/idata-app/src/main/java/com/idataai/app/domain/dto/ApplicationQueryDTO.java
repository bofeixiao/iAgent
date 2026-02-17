package com.idataai.app.domain.dto;

import com.idataai.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用查询DTO
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationQueryDTO extends PageQuery {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 应用类型
     */
    private String appType;

    /**
     * 是否热门
     */
    private Boolean isHot;

    /**
     * 是否推荐
     */
    private Boolean isRecommended;

    /**
     * 状态
     */
    private Integer status;
}

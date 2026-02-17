package com.idataai.app.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.idataai.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 应用实体类
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_app_application", autoResultMap = true)
public class Application extends BaseEntity {

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
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> configSchema;

    /**
     * 输入字段（JSON）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> inputFields;

    /**
     * 输出文件（JSON）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
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
     * 状态：0-未发布，1-已发布，2-已下线
     */
    private Integer status;

    /**
     * 使用次数
     */
    private Integer useCount;

    /**
     * 排序
     */
    private Integer sortOrder;
}

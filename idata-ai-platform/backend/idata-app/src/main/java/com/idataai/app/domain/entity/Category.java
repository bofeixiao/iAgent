package com.idataai.app.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.idataai.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用分类实体类
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_app_category")
public class Category extends BaseEntity {

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类图标
     */
    private String categoryIcon;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;
}

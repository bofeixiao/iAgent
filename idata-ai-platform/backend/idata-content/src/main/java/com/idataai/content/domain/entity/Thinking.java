package com.idataai.content.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.idataai.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 思维分析实体类
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_content_thinking", autoResultMap = true)
public class Thinking extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 行业
     */
    private String industry;

    /**
     * 问卷答案（JSON）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> questionnaireAnswers;

    /**
     * 写作风格
     */
    private String writingStyle;

    /**
     * 资源列表（JSON）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> resources;

    /**
     * 状态：0-未分析，1-分析中，2-已完成
     */
    private Integer status;

    /**
     * 分析时间
     */
    private LocalDateTime analyzedTime;
}

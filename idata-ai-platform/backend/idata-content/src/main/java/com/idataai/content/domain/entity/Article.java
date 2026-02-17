package com.idataai.content.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.idataai.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 文章实体类
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_content_article")
public class Article extends BaseEntity {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 封面图片
     */
    private String coverImage;

    /**
     * 分类
     */
    private String category;

    /**
     * 标签
     */
    private String tags;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 点赞次数
     */
    private Integer likeCount;

    /**
     * 是否精选
     */
    private Boolean isFeatured;

    /**
     * 是否发布
     */
    private Boolean isPublished;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;
}

package com.idataai.content.domain.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文章VO
 *
 * @author iDataAI
 */
@Data
public class ArticleVO {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private String coverImage;
    private String category;
    private String tags;
    private Integer viewCount;
    private Integer likeCount;
    private Boolean isFeatured;
    private LocalDateTime publishTime;
}

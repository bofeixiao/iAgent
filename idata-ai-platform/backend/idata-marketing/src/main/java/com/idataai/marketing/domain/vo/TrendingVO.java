package com.idataai.marketing.domain.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 热点VO
 *
 * @author iDataAI
 */
@Data
public class TrendingVO {
    private Long id;
    private String title;
    private String source;
    private Integer hotValue;
    private String link;
    private LocalDateTime fetchTime;
    private Boolean isPinned;
}

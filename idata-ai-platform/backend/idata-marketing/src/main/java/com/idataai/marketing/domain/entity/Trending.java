package com.idataai.marketing.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.idataai.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 热点实体类
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_marketing_trending")
public class Trending extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 来源：douyin, weibo, wechat, zhihu
     */
    private String source;

    /**
     * 热度值
     */
    private Integer hotValue;

    /**
     * 链接
     */
    private String link;

    /**
     * 抓取时间
     */
    private LocalDateTime fetchTime;

    /**
     * 是否置顶
     */
    private Boolean isPinned;
}

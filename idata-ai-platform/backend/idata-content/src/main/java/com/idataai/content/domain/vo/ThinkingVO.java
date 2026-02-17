package com.idataai.content.domain.vo;

import lombok.Data;
import java.util.Map;

/**
 * 思维分析VO
 *
 * @author iDataAI
 */
@Data
public class ThinkingVO {
    private Long id;
    private String industry;
    private Map<String, Object> questionnaireAnswers;
    private String writingStyle;
    private Map<String, Object> resources;
    private Integer status;
}

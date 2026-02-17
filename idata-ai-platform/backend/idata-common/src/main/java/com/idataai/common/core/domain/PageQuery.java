package com.idataai.common.core.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求参数
 *
 * @author iDataAI
 */
@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页码，从1开始
     */
    private Long pageNum = 1L;

    /**
     * 每页大小
     */
    private Long pageSize = 10L;
}

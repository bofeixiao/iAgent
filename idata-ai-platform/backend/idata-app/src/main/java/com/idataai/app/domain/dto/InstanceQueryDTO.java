package com.idataai.app.domain.dto;

import com.idataai.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实例查询DTO
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InstanceQueryDTO extends PageQuery {

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 实例名称
     */
    private String instanceName;

    /**
     * 状态：0-待处理，1-处理中，2-成功，3-失败
     */
    private Integer status;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;
}

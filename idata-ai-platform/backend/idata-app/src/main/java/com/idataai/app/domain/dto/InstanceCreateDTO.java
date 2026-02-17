package com.idataai.app.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

/**
 * 实例创建DTO
 *
 * @author iDataAI
 */
@Data
public class InstanceCreateDTO {

    /**
     * 应用ID
     */
    @NotNull(message = "应用ID不能为空")
    private Long appId;

    /**
     * 实例名称
     */
    @NotBlank(message = "实例名称不能为空")
    private String instanceName;

    /**
     * 输入参数
     */
    @NotNull(message = "输入参数不能为空")
    private Map<String, Object> inputParams;
}

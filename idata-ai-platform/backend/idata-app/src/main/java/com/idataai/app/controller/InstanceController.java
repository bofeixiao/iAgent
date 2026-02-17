package com.idataai.app.controller;

import com.idataai.common.core.domain.PageResult;
import com.idataai.common.core.domain.Result;
import com.idataai.app.domain.dto.InstanceCreateDTO;
import com.idataai.app.domain.dto.InstanceQueryDTO;
import com.idataai.app.domain.vo.InstanceVO;
import com.idataai.app.service.IInstanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 应用实例控制器
 *
 * @author iDataAI
 */
@RestController
@RequestMapping("/api/instance")
@RequiredArgsConstructor
public class InstanceController {

    private final IInstanceService instanceService;

    /**
     * 创建应用实例
     */
    @PostMapping("/create")
    public Result<Long> create(@Valid @RequestBody InstanceCreateDTO createDTO) {
        Long instanceId = instanceService.createInstance(createDTO);
        return Result.success("创建成功，正在处理中...", instanceId);
    }

    /**
     * 查询我的实例列表
     */
    @GetMapping("/my-instances")
    public Result<PageResult<InstanceVO>> getMyInstances(InstanceQueryDTO queryDTO) {
        PageResult<InstanceVO> pageResult = instanceService.getMyInstances(queryDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取实例详情
     */
    @GetMapping("/{id}")
    public Result<InstanceVO> getById(@PathVariable Long id) {
        InstanceVO instanceVO = instanceService.getInstanceById(id);
        return Result.success(instanceVO);
    }

    /**
     * 删除实例
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        instanceService.deleteInstance(id);
        return Result.success("删除成功");
    }
}

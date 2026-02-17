package com.idataai.app.controller;

import com.idataai.common.core.domain.PageResult;
import com.idataai.common.core.domain.Result;
import com.idataai.app.domain.dto.ApplicationQueryDTO;
import com.idataai.app.domain.vo.ApplicationDetailVO;
import com.idataai.app.domain.vo.ApplicationVO;
import com.idataai.app.service.IApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应用控制器
 *
 * @author iDataAI
 */
@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final IApplicationService applicationService;

    /**
     * 分页查询应用列表
     */
    @GetMapping("/list")
    public Result<PageResult<ApplicationVO>> list(ApplicationQueryDTO queryDTO) {
        PageResult<ApplicationVO> pageResult = applicationService.getApplicationList(queryDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取应用详情
     */
    @GetMapping("/{id}")
    public Result<ApplicationDetailVO> getById(@PathVariable Long id) {
        ApplicationDetailVO detailVO = applicationService.getApplicationDetail(id);
        return Result.success(detailVO);
    }

    /**
     * 获取热门应用
     */
    @GetMapping("/hot")
    public Result<List<ApplicationVO>> getHotApplications() {
        List<ApplicationVO> list = applicationService.getHotApplications();
        return Result.success(list);
    }

    /**
     * 获取推荐应用
     */
    @GetMapping("/recommended")
    public Result<List<ApplicationVO>> getRecommendedApplications() {
        List<ApplicationVO> list = applicationService.getRecommendedApplications();
        return Result.success(list);
    }

    /**
     * 增加应用使用次数
     */
    @PostMapping("/{id}/use")
    public Result<Void> increaseUseCount(@PathVariable Long id) {
        applicationService.increaseUseCount(id);
        return Result.success();
    }
}

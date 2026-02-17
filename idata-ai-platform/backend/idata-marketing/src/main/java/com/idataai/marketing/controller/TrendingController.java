package com.idataai.marketing.controller;

import com.idataai.common.core.domain.Result;
import com.idataai.marketing.domain.vo.TrendingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 热点控制器
 *
 * @author iDataAI
 */
@RestController
@RequestMapping("/api/trending")
@RequiredArgsConstructor
public class TrendingController {

    /**
     * 获取热点列表
     */
    @GetMapping("/list")
    public Result<List<TrendingVO>> list(
            @RequestParam(required = false) String source,
            @RequestParam(defaultValue = "20") Integer limit) {
        // TODO: 实现查询热点列表逻辑
        return Result.success(null);
    }
}

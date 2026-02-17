package com.idataai.content.controller;

import com.idataai.common.core.domain.PageResult;
import com.idataai.common.core.domain.Result;
import com.idataai.content.domain.vo.ArticleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 文章控制器
 *
 * @author iDataAI
 */
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    /**
     * 获取文章列表
     */
    @GetMapping("/list")
    public Result<PageResult<ArticleVO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean isFeatured) {
        // TODO: 实现查询逻辑
        return Result.success(PageResult.empty(pageNum.longValue(), pageSize.longValue()));
    }

    /**
     * 获取文章详情
     */
    @GetMapping("/{id}")
    public Result<ArticleVO> getById(@PathVariable Long id) {
        // TODO: 实现查询逻辑
        return Result.success(null);
    }

    /**
     * 增加浏览次数
     */
    @PostMapping("/{id}/view")
    public Result<Void> increaseViewCount(@PathVariable Long id) {
        // TODO: 实现增加浏览次数
        return Result.success();
    }
}

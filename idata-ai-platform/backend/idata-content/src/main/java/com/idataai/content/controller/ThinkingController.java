package com.idataai.content.controller;

import com.idataai.common.core.domain.Result;
import com.idataai.content.domain.vo.ThinkingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 思维分析控制器
 *
 * @author iDataAI
 */
@RestController
@RequestMapping("/api/thinking")
@RequiredArgsConstructor
public class ThinkingController {

    /**
     * 获取我的思维分析
     */
    @GetMapping("/my")
    public Result<ThinkingVO> getMyThinking() {
        // TODO: 实现查询逻辑
        return Result.success(null);
    }

    /**
     * 提交问卷
     */
    @PostMapping("/submit")
    public Result<Long> submit(@RequestBody Map<String, Object> questionnaire) {
        // TODO: 实现提交问卷逻辑
        return Result.success(null);
    }
}

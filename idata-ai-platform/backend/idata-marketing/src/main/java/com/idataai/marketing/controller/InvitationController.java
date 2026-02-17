package com.idataai.marketing.controller;

import com.idataai.common.core.domain.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 邀请控制器
 *
 * @author iDataAI
 */
@RestController
@RequestMapping("/api/invitation")
@RequiredArgsConstructor
public class InvitationController {

    /**
     * 获取我的邀请码
     */
    @GetMapping("/my-code")
    public Result<String> getMyInvitationCode() {
        // TODO: 实现获取邀请码逻辑
        return Result.success(null);
    }

    /**
     * 使用邀请码
     */
    @PostMapping("/use")
    public Result<Void> useInvitationCode(@RequestParam String code) {
        // TODO: 实现使用邀请码逻辑
        return Result.success();
    }
}

package com.idataai.payment.controller;

import com.idataai.common.core.domain.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 积分控制器
 *
 * @author iDataAI
 */
@RestController
@RequestMapping("/api/credit")
@RequiredArgsConstructor
public class CreditController {

    /**
     * 获取积分余额
     */
    @GetMapping("/balance")
    public Result<Integer> getBalance() {
        // TODO: 实现查询积分余额逻辑
        return Result.success(0);
    }

    /**
     * 积分充值
     */
    @PostMapping("/recharge")
    public Result<Void> recharge(@RequestParam Integer amount) {
        // TODO: 实现积分充值逻辑
        return Result.success();
    }
}

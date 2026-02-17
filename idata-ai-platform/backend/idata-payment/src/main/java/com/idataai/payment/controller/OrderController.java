package com.idataai.payment.controller;

import com.idataai.common.core.domain.Result;
import com.idataai.payment.domain.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 订单控制器
 *
 * @author iDataAI
 */
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public Result<OrderVO> create(@RequestBody Map<String, Object> params) {
        // TODO: 实现创建订单逻辑
        return Result.success(null);
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/{orderNo}")
    public Result<OrderVO> getByOrderNo(@PathVariable String orderNo) {
        // TODO: 实现查询订单逻辑
        return Result.success(null);
    }
}

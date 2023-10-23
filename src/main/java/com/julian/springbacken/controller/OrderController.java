package com.julian.springbacken.controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping()
public class OrderController {

    // 生成订单
    @PostMapping("/order")
    public Map<String, Object> createOrder(@RequestParam Long rid) {

        // 业务逻辑
        return Map.of(
                "status", "0",
                "msg", "Success",
                "data", Map.of("oid", "123"));
    }

    // 查询订单
    @GetMapping("/order/oid")
    public Map<String, Object> inquireOrder(@RequestParam Long uid) {

        // 业务逻辑
        return Map.of(
                "status", "0",
                "msg", "Success",
                "data", Map.of(
                        "oid", "123",
                        "rid", "321",
                        "create_time","2001/1/1",
                        "price", "25.5"));
    }

    // 创建支付请求
    @PutMapping("/order/setupPay")
    public Map<String, Object> payOrder(@RequestParam Long uid, @RequestParam String platform) {

        // 业务逻辑
        return Map.of(
                "status", "0",
                "msg", "Success",
                "data", Map.of(
                        "orderStr", "123"
                        ));
    }

    @PutMapping("/order/confirmPay")
    public Map<String, Object> ConfirmPayment(@RequestParam Long uid, @RequestParam String platform, @RequestParam Long trade_no) {

        // 业务逻辑
        return Map.of(
                "status", "0",
                "msg", "Success"
                );
    }




}


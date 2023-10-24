package com.julian.springbacken.controller;
import com.julian.springbacken.response.ApiResponse;
import com.julian.springbacken.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService orderService;
    // 生成订单
    @PostMapping("/create")
    public ApiResponse createOrder(@RequestParam Long rid) {
        try{
            Long oid = orderService.createOrder(rid);
            return new ApiResponse("0", oid.toString());
        }catch (Exception e){
            return new ApiResponse("500", "Server error: " + e.getMessage());
        }
    }






}


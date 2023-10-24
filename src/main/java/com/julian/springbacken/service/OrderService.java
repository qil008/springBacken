package com.julian.springbacken.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.julian.springbacken.Entity.OrderEntity;
import com.julian.springbacken.response.ApiResponse;

import java.util.Optional;

public interface OrderService extends IService<OrderEntity> {
    Optional<OrderEntity> getFirstUnpaidOrderByUserId(Long uid);
    Long createOrder(Long rid) throws Exception;

}

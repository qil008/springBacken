package com.julian.springbacken.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.julian.springbacken.Entity.OrderEntity;
import com.julian.springbacken.Entity.RideEntity;
import com.julian.springbacken.mapper.OrderMapper;
import com.julian.springbacken.response.ApiResponse;
import com.julian.springbacken.service.OrderService;
import com.julian.springbacken.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    @Autowired
    private RideService rideService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Optional<OrderEntity> getFirstUnpaidOrderByUserId(Long uid) {
        // 1. 通过uid找到相关的ride记录
        List<RideEntity> rides = rideService.getRidesByPassengerId(uid);
        if (rides == null || rides.isEmpty()) {
            return Optional.empty();
        }
        // 2. 从rides中提取所有的rid
        List<Long> rideIds = rides.stream().map(RideEntity::getRid).collect(Collectors.toList());
        // 3. 通过rideIds找到第一个相关的未支付订单
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("rid", rideIds).eq("Status", "Unpaid").last("LIMIT 1");

        return Optional.ofNullable(orderMapper.selectOne(queryWrapper));
    }

    @Override
    public Long createOrder(Long rid) throws Exception{
        RideEntity ride = rideService.getRideByID(rid);
        // TODO: 通过ride信息添加计费逻辑
        OrderEntity order = new OrderEntity();
        order.setRid(rid);
        order.setStatus("Paid");
        try {
            save(order);
            return order.getOid();
        } catch (Exception e) {
            throw new Exception("Failed to create ride: " + e.getMessage(), e);
        }
    }

    public ApiResponse updatePaymentStatus(Long oid){
        OrderEntity order = getById(oid);
        order.setStatus("paid");
        try{
            updateById(order);
            return new ApiResponse("0", "Success");
        }catch (Exception e){
            return new ApiResponse("500", "Server error: " + e.getMessage());
        }
    }
}

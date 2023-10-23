package com.julian.springbacken.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julian.springbacken.Entity.OrderEntity;
import com.julian.springbacken.Entity.RideEntity;
import com.julian.springbacken.Entity.UserEntity;
import com.julian.springbacken.mapper.UserMapper;
import com.julian.springbacken.mqtt.MqttPushClient;
import com.julian.springbacken.service.RideService;
import com.julian.springbacken.service.OrderService;
import com.julian.springbacken.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RideService rideService;
    @Autowired
    private MqttPushClient mqttPushClient;
    @Override
    public ApiResponse createUser(String phone, String password, String username, String role) {
        UserEntity user = new UserEntity();
        user.setPhone(phone);
        user.setPassword(password);
        user.setUsername((username == null || username.isEmpty()) ? "user" + ((int) (Math.random() * 100000000)) : username);
        user.setRole(role);

        try {
            save(user);
            return new ApiResponse("0", "Success");
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            if (errorMsg.contains("Data truncated for column 'role'")) {
                return new ApiResponse("001", "Role required");
            }
            if (errorMsg.contains("Duplicate entry") && errorMsg.contains("for key 'user.unique_phone'")) {
                return new ApiResponse("002", "Phone registered");
            }
            // 未知错误
            return new ApiResponse("500", "Server error" + e.getMessage());
        }
    }

    @Override
    public ApiResponse updateUser(Long uid,
                                  String phone,
                                  String role,
                                  String password,
                                  String username,
                                  String number_plate,
                                  String vehicle_type,
                                  Double total_trip_length,
                                  String province,
                                  String city) {
        // 尝试找到现有的用户
        UserEntity user = getById(uid);
        if (user == null) {
            return new ApiResponse("004", "User not found");
        }

        // 检查新的电话号码是否已被其他用户使用
        if (phone != null && !phone.isEmpty()) {
            UserEntity userWithNewPhone = lambdaQuery().eq(UserEntity::getPhone, phone).one();
            if (userWithNewPhone != null && !userWithNewPhone.getUid().equals(uid)) {
                return new ApiResponse("003", "Phone already in use by another user");
            }
            user.setPhone(phone);
        }

        // 对其他属性进行非空检查，然后更新
        if (role != null && !role.isEmpty()) {
            user.setRole(role);
        }

        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }

        if (username != null && !username.isEmpty()) {
            user.setUsername(username);
        }

        if (number_plate != null && !number_plate.isEmpty()) {
            user.setNumberPlate(number_plate);
        }

        if (vehicle_type != null && !vehicle_type.isEmpty()) {
            user.setVehicleType(vehicle_type);
        }

        if (total_trip_length != null) {
            user.setTotalTripLength(total_trip_length);
        }

        if (province != null && !province.isEmpty()) {
            user.setProvince(province);
        }

        if (city != null && !city.isEmpty()) {
            user.setCity(city);
        }

        try {
            // 尝试保存更新后的用户
            updateById(user);
            return new ApiResponse("0", "Success");
        } catch (Exception e) {
            return new ApiResponse("500", "Server error: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse getUser(String phone){
        UserEntity user =  lambdaQuery().eq(UserEntity::getPhone, phone).one();
        if (user == null) {
            return new ApiResponse("404", "User not found");
        }
        try {
            String jsonData = new ObjectMapper().writeValueAsString(user);
            return new ApiResponse("0", jsonData);
        } catch (JsonProcessingException e) {
            return new ApiResponse("010", "Failed to convert user to JSON: " + e.getMessage());
        }
    }
    @Override
    public ApiResponse PostRide(Long uid,
                                Double pickUpLong,
                                Double pickUpLat,
                                String pickUpResolvedAddress,
                                Double destLong,
                                Double destLat,
                                String destResolvedAddress,
                                String type,
                                String province,
                                String city) {
        Optional<OrderEntity> unpaidOrderOpt = orderService.getFirstUnpaidOrderByUserId(uid);

        if (unpaidOrderOpt.isPresent()) {
            return new ApiResponse("005", "There is an unpaid order with ID: " + unpaidOrderOpt.get().getOid());
        } else {
            try {
                Long rid = rideService.createRide(uid, pickUpLong, pickUpLat, pickUpResolvedAddress, destLong, destLat, destResolvedAddress,type);
                String pushMessage = String.format(
                        "{" +
                                "\"rid\": \"%d\"," +
                                "\"pickupLongitude\": \"%f\"," +
                                "\"pickupLatitude\": \"%f\"," +
                                "\"pickupAddress\": \"%s\"," +
                                "\"destinationLongitude\": \"%f\"," +
                                "\"destinationLatitude\": \"%f\"," +
                                "\"destinationAddress\": \"%s\"," +
                                "\"type\": \"%s\"," +
                                "\"accepted\": \"%d\"" +
                                "}",
                        rid, pickUpLong, pickUpLat, pickUpResolvedAddress,
                        destLong, destLat, destResolvedAddress,
                        type, 0
                );
                String topic = province + "-" + city;
                mqttPushClient.publish(2,false,topic,pushMessage);
                return new ApiResponse("0", "Success");
            } catch (Exception e) {
                return new ApiResponse("500", "Server error: " + e.getMessage());
            }
        }
    }

    @Override
    public ApiResponse acceptRide(Long rid,
                                  Long uid,
                                  String province,
                                  String city,
                                  String numberPlate){

        RideEntity ride = rideService.getById(rid);
        if (ride == null) {
            return new ApiResponse("404", "Ride not found");
        }

        // 检查ride的status
        if (!"created".equals(ride.getStatus())) {
            return new ApiResponse("006", "Ride already accepted");
        }
        String mqttChannelName = rid + "-" + generateRandomDigits(7);
        String status = "accepted";
        LocalDateTime driver_accept_time = LocalDateTime.now();
        try{
            rideService.updateRide(
                    rid,
                    uid,
                    mqttChannelName,
                    status,
                    driver_accept_time,
                    null,
                    null,
                    null,
                    null,
                    null);
            String pushMessage = String.format(
                    "{" +
                            "\"rid\": \"%d\"," +
                            "\"accepted\": \"%d\"" +
                            "}",
                    rid,1
            );
            String topic = province + "-" + city;
            mqttPushClient.publish(2,false,topic,pushMessage);
            return new ApiResponse("0", mqttChannelName);
        }catch (Exception e){
            return new ApiResponse("500", "Server error: " + e.getMessage());
        }

    }
    private String generateRandomDigits(int n) {
        Random rand = new Random();
        StringBuilder numStr = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int digit = rand.nextInt(10);  // 0-9 inclusive
            numStr.append(digit);
        }
        return numStr.toString();
    }

}



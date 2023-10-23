package com.julian.springbacken.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.julian.springbacken.Entity.UserEntity;
import com.julian.springbacken.response.ApiResponse;

public interface UserService extends IService<UserEntity> {
    ApiResponse createUser(String phone, String password, String username, String role);
    ApiResponse updateUser(Long uid, String phone, String role, String password, String username, String number_plate, String vehicle_type, Double total_trip_length, String province, String city);
    ApiResponse getUser(String Phone);
    ApiResponse PostRide(Long uid,
                         Double pickUpLong,
                         Double pickUpLat,
                         String pickUpResolvedAddress,
                         Double destLong,
                         Double destLat,
                         String destResolvedAddress,
                         String type,
                         String province,
                         String city);
    ApiResponse acceptRide(Long rid,
                           Long uid,
                           String province,
                           String city,
                           String numberPlate);
}
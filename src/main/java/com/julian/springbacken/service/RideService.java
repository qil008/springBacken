package com.julian.springbacken.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.julian.springbacken.Entity.RideEntity;
import com.julian.springbacken.response.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface RideService extends IService<RideEntity> {
    List<RideEntity> getRidesByPassengerId(Long uid);
    Long createRide(Long passenger_uid,
                           Double pickUpLong,
                           Double pickUpLat,
                           String pickUpResolvedAddress,
                           Double destLong,
                           Double destLat,
                           String destResolvedAddress,
                           String ride_type) throws Exception;
    void updateRide(Long rid,
                           Long driver_uid,
                           String mqtt_channel_name,
                           String status,
                           LocalDateTime driver_accept_time,
                           LocalDateTime passenger_pickup_time,
                           LocalDateTime end_point_arrival_time,
                           LocalDateTime trip_cancellation_time,
                           Double total_trip_distance,
                           Long oid) throws Exception;
    ApiResponse getRide(Long rid);

    RideEntity getRideByID(Long rid);

//    ApiResponse updateDistance(Long rid);
}


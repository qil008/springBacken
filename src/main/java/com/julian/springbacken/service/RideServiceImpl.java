package com.julian.springbacken.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julian.springbacken.Entity.RideEntity;
import com.julian.springbacken.Entity.UserEntity;
import com.julian.springbacken.mapper.RideMapper;
import com.julian.springbacken.response.ApiResponse;
import com.julian.springbacken.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RideServiceImpl extends ServiceImpl<RideMapper, RideEntity> implements RideService {

    @Autowired
    private RideMapper rideMapper;

    @Override
    public List<RideEntity> getRidesByPassengerId(Long uid) {
        QueryWrapper<RideEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_uid", uid);

        return rideMapper.selectList(queryWrapper);
    }
    @Override
    public Long createRide(Long passenger_uid,
                           Double pickUpLong,
                           Double pickUpLat,
                           String pickUpResolvedAddress,
                           Double destLong,
                           Double destLat,
                           String destResolvedAddress,
                           String ride_type
    ) throws Exception {

        RideEntity ride = new RideEntity();
        ride.setPassengerUid(passenger_uid);
        ride.setPickUpLong(pickUpLong);
        ride.setPickUpLat(pickUpLat);
        ride.setPickUpResolvedAddress(pickUpResolvedAddress);
        ride.setDestLong(destLong);
        ride.setDestLat(destLat);
        ride.setDestResolvedAddress(destResolvedAddress);
        ride.setRideType(ride_type);

        try {
            save(ride); // Assuming you have a save method in place for RideEntity similar to UserEntity
            return ride.getRid();
        } catch (Exception e) {
            throw new Exception("Failed to create ride: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateRide(Long rid,
                           Long driver_uid,
                           String mqtt_channel_name,
                           String status,
                           LocalDateTime driver_accept_time,
                           LocalDateTime passenger_pickup_time,
                           LocalDateTime end_point_arrival_time,
                           LocalDateTime trip_cancellation_time,
                           Double total_trip_distance,
                           Long oid) throws Exception {

        // Get the existing ride
        RideEntity rideEntity = getById(rid);
        if (rideEntity == null) {
            throw new Exception("Ride not found with ID: " + rid);
        }

        // Update the attributes if they are provided
        if (driver_uid != null) rideEntity.setDriverUid(driver_uid);
        if (mqtt_channel_name != null && !mqtt_channel_name.isEmpty()) rideEntity.setMqttChannelName(mqtt_channel_name);
        if (status != null && !status.isEmpty()) rideEntity.setStatus(status);
        if (driver_accept_time != null) rideEntity.setDriverAcceptTime(driver_accept_time);
        if (passenger_pickup_time != null) rideEntity.setPassengerPickupTime(passenger_pickup_time);
        if (end_point_arrival_time != null) rideEntity.setEndPointArrivalTime(end_point_arrival_time);
        if (trip_cancellation_time != null) rideEntity.setTripCancellationTime(trip_cancellation_time);
        if (total_trip_distance != null) rideEntity.setTotalTripDistance(total_trip_distance);
        if (oid != null) rideEntity.setOid(oid);

        // Save the updated ride
        try {
            updateById(rideEntity);
        } catch (Exception e) {
            throw new Exception("Failed to update ride: " + e.getMessage(), e);
        }
    }

    @Override
    public ApiResponse getRide(Long rid){
        RideEntity ride =  getById(rid);
        if(ride == null){
            return new ApiResponse("404", "Ride not found");

        }
        try {
            String jsonData = new ObjectMapper().writeValueAsString(ride);
            return new ApiResponse("0", "success");
        } catch (JsonProcessingException e) {
            return new ApiResponse("010", "Failed to convert user to JSON: " + e.getMessage());
        }
    }


}


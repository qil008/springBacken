package com.julian.springbacken.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julian.springbacken.Entity.RideEntity;
import com.julian.springbacken.response.ApiResponse;
import com.julian.springbacken.service.RideService;
import com.julian.springbacken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/ride")
public class RiderController {
    @Autowired
    private RideService rideService;
    @Autowired
    private UserService userService;
    @PostMapping("/post")
    @CrossOrigin(origins = "*")
    // 乘客发单
    public ApiResponse postRide(@RequestParam Long uid,
                                @RequestParam Double pickUpLong,
                                @RequestParam Double pickUpLat,
                                 @RequestParam String pickUpResolvedAddress,
                                @RequestParam Double destLong,
                                @RequestParam Double destLat,
                                @RequestParam String destResolvedAddress,
                                @RequestParam String type,
                                @RequestParam String province,
                                @RequestParam String city) {
        return userService.PostRide(uid,
                pickUpLong,
                pickUpLat,
                pickUpResolvedAddress,
                destLong,
                destLat,
                destResolvedAddress,
                type,
                province,
                city);
    }

    @PutMapping("/accept")
    @CrossOrigin(origins = "*")
    // 司机接单
    public ApiResponse acceptRide(@RequestParam Long rid,
                                  @RequestParam Long uid,
                                  @RequestParam String province,
                                  @RequestParam String city,
                                  @RequestParam String numberPlate){
        return userService.acceptRide(rid,uid,province,city,numberPlate);

    }

    @PutMapping("/cancel")
    @CrossOrigin(origins = "*")
    // 取消订单
    public ApiResponse cancelRide(@RequestParam Long rid) {
        try{
            String status = "cancelled";
            LocalDateTime trip_cancellation_time = LocalDateTime.now();
            rideService.updateRide(rid,
                    null,
                    null,
                    status,
                    null,
                    null,
                    null,
                    trip_cancellation_time,
                    null,
                    null);
            return new ApiResponse("0", "Success");
        }catch (Exception e){
            return new ApiResponse("500", "Server error: " + e.getMessage());
        }
    }

    @GetMapping("/inquire")
    @CrossOrigin(origins = "*")
    // 查询订单状态
    public ApiResponse getRideStatus(@RequestParam Long rid) {
        RideEntity ride = rideService.getRideByID(rid);
        if(ride == null){
            return new ApiResponse("404", "Ride not found");

        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            String jsonData = mapper.writeValueAsString(ride);
            return new ApiResponse("0", jsonData);
        } catch (JsonProcessingException e) {
            return new ApiResponse("010", "Failed to convert user to JSON: " + e.getMessage());
        }

    }
}

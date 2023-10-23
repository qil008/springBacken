package com.julian.springbacken.controller;
import com.julian.springbacken.response.ApiResponse;
import com.julian.springbacken.service.RideService;
import com.julian.springbacken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @CrossOrigin(origins = "http://localhost:8081")
    // 司机接单
    public ApiResponse acceptRide(@RequestParam Long rid,
                                  @RequestParam Long uid,
                                  @RequestParam String province,
                                  @RequestParam String city,
                                  @RequestParam String numberPlate){
        return userService.acceptRide(rid,uid,province,city,numberPlate);

    }

    @PutMapping("/cancel")
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
    // 查询订单状态
    public ApiResponse getRideStatus(@RequestParam Long rid) {
        return rideService.getRide(rid);
    }
}

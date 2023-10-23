package com.julian.springbacken.controller;

import com.julian.springbacken.response.ApiResponse;
import com.julian.springbacken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    // POST /user
    @PostMapping("/signUp")
    @CrossOrigin(origins = "http://localhost:8081")
    public ApiResponse createUser(@RequestParam String phone,
                                  @RequestParam String password,
                                  @RequestParam(required = false) String username,
                                  @RequestParam String role) {

        return userService.createUser(phone, password, username, role);
    }

    // GET /login
    @GetMapping("/login")
    @CrossOrigin(origins = "http://localhost:8081")
    public ApiResponse login(@RequestParam String phone,
                             @RequestParam String password) {
        return userService.getUser(phone);
    }

    // PUT /user/{uid}
    @PutMapping("update/{uid}")
    public ApiResponse updateUser(@PathVariable Long uid,
                                          @RequestParam String phone,
                                          @RequestParam String role,
                                          @RequestParam String password,
                                          @RequestParam String username,
                                          @RequestParam String number_plate,
                                          @RequestParam String vehicle_type,
                                          @RequestParam Double total_trip_length,
                                          @RequestParam String province,
                                          @RequestParam String city) {
        return userService.updateUser(uid,
                phone,
                role,
                password,
                username,
                number_plate,
                vehicle_type,
                total_trip_length,
                province,city);
    }

}

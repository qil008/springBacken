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
    @CrossOrigin(origins = "*")
    public ApiResponse createUser(@RequestParam String phone,
                                  @RequestParam String password,
                                  @RequestParam(required = false) String username,
                                  @RequestParam String role) {

        return userService.createUser(phone, password, username, role);
    }

    // GET /login
    @GetMapping("/login")
    @CrossOrigin(origins = "*")
    public ApiResponse login(@RequestParam String phone,
                             @RequestParam String password) {
        return userService.getUser(phone);
    }

    // PUT /user/{uid}
    @PutMapping("update")
    @CrossOrigin(origins = "*")
    public ApiResponse updateUser(@RequestParam Long uid,
                                          @RequestParam String phone,
                                          @RequestParam(required = false) String role,
                                          @RequestParam(required = false) String password,
                                          @RequestParam(required = false) String username,
                                          @RequestParam(required = false) String numberPlate,
                                          @RequestParam(required = false) String vehicleType,
                                          @RequestParam(required = false) Double totalTripLength,
                                          @RequestParam(required = false) String province,
                                          @RequestParam(required = false) String city) {
        return userService.updateUser(uid,
                phone,
                role,
                password,
                username,
                numberPlate,
                vehicleType,
                totalTripLength,
                province,city);
    }

}

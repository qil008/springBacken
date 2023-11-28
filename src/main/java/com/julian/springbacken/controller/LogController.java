package com.julian.springbacken.controller;
import com.julian.springbacken.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.julian.springbacken.service.LogService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("log")
public class LogController {
    @Autowired
    private LogService logService;

    @PutMapping("/create")
    @CrossOrigin(origins = "*")
    // 取消订单
    public void createLog(@RequestParam String source,
                                 @RequestParam String level,
                                 @RequestParam String content) {
        logService.sendKafkaBuffer(source, level, content);
    }
}

package com.julian.springbacken.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.julian.springbacken.Entity.LogEntity;
import com.julian.springbacken.response.ApiResponse;


import java.util.Optional;

public interface LogService extends IService<LogEntity> {
    void sendKafkaBuffer(String source, String level, String content);
    ApiResponse createLog(String message);
}

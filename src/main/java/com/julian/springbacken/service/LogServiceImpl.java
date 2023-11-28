package com.julian.springbacken.service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.julian.springbacken.Entity.LogEntity;
import com.julian.springbacken.mapper.LogMapper;
import com.julian.springbacken.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, LogEntity> implements LogService {
    private static final String TOPIC = "logging";

    @Autowired
    private KafkaTemplate<?, String> kafkaTemplate;
    public void sendKafkaBuffer(String source, String level, String content){
        String message = source +"/"+level+"/"+content;
        kafkaTemplate.send(TOPIC, message);
    }

    @KafkaListener(topics = TOPIC, groupId = "testGroup", topicPartitions = {})
    public ApiResponse createLog(String message){
//        System.out.println("Consumer= " + message);
        String[] parts = message.split("/");
        if (parts.length != 3) {
            return new ApiResponse("500", "Server error: " + "Invalid message format");
        }
        LogEntity log = new LogEntity();
        log.setSource(parts[0]);
        log.setLevel(parts[1]);
        log.setContent(parts[2]);

        // 保存到数据库
        try{
            save(log);
            return new ApiResponse("0", "Success");
        }catch (Exception e){
            return new ApiResponse("500", "Server error" + e.getMessage());
        }
    }
}

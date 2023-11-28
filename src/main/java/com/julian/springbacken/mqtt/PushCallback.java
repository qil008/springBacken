package com.julian.springbacken.mqtt;

import com.julian.springbacken.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PushCallback implements MqttCallback {
    @Autowired
    private MqttConfiguration mqttConfiguration;

    @Autowired
    private  TrackService trackService;
    @Override
    public void connectionLost(Throwable cause) {        // 连接丢失后，一般在这里面进行重连
        log.info("连接断开，正在重连");
        MqttPushClient mqttPushClient = mqttConfiguration.getMqttPushClient();
        if (null != mqttPushClient) {
            mqttPushClient.connect(mqttConfiguration.getHost(), mqttConfiguration.getClientid(), mqttConfiguration.getUsername(),
                    mqttConfiguration.getPassword(), mqttConfiguration.getTimeout(), mqttConfiguration.getKeepalive());
            log.info("已重连");
        }

    }
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        // subscribe后得到的消息会执行到这里面,这里在控制台有输出
        log.info("接收消息主题 : " + topic);
        log.info("接收消息Qos : " + message.getQos());
        String payload = new String(message.getPayload());
        log.info("接收消息内容 : " + payload);
        if (!topic.contains("-")) {
            // 解析消息内容
            String[] parts = payload.split("/");
            if (parts.length == 5) {
                try {
                    Long rid = Long.parseLong(parts[0]);
                    Double lng = Double.parseDouble(parts[1]);
                    Double lat = Double.parseDouble(parts[2]);
                    Double speed = Double.parseDouble(parts[3]);
                    Double asl = Double.parseDouble(parts[4]);
                    trackService.putTrack(rid, lng, lat, speed, asl);
                } catch (NumberFormatException e) {
                    log.error("解析错误: " + e.getMessage());
                }
            } else {
                log.error("消息格式不正确");
            }
        }

    }

}


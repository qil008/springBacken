package com.julian.springbacken.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttSubClient {

    public MqttSubClient(MqttPushClient mqttPushClient){
        subScribeDataPublishTopic();
    }


    private void subScribeDataPublishTopic(){
        //订阅test_queue主题
//        subscribe("gd-sz");
        subscribe("gd-sz", 2);
    }

    public void subscribe(String topic, int qos) {
        try {
            MqttClient client = MqttPushClient.getClient();
            if (client == null) return;
            client.subscribe(topic, qos);
            log.info("订阅主题:{}",topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }



}



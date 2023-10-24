package com.julian.springbacken.controller;


import com.julian.springbacken.mqtt.MqttPushClient;
import com.julian.springbacken.mqtt.MqttSubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mqtt")
public class MqttController {
    @Autowired
    private MqttPushClient mqttPushClient;
    @Autowired
    private MqttSubClient mqttSubClient;

    @RequestMapping("Publish")
    public void PublishMessage(@RequestParam int qos,
                                    @RequestParam boolean retained,
                                    @RequestParam String topic,
                                    @RequestParam String pushMessage) {
        mqttPushClient.publish(qos,retained,topic,pushMessage);
    }

    @RequestMapping("Subscribe")
    public void subscribeTopic(@RequestParam String topic,
                               @RequestParam int Qos) {
        mqttSubClient.subscribe(topic,Qos); }
}
package com.TandK.core.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "kafka-topic";

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    @Async
    public void sendDefault(String msg) {
        // 发送给默认topic
        kafkaTemplate.send(TOPIC, msg);
    }

    @Async
    public void sendAnotherTopic(String msg) {
        // 发送给默认topic
        kafkaTemplate.send("another", msg);
    }
}
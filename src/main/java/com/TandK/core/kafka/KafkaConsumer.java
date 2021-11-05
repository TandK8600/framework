package com.TandK.core.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author TandK
 */
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "kafka-topic", groupId = "group_id")
    public void consume(String message) {
        log.info("## consume message: {}", message);
    }

    @KafkaListener(topics = "another", groupId = "group_id")
    public void consumeAnother(String message) {
        log.info("## consume another message: {}", message);
    }
}

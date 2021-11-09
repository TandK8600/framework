package com.TandK.service.impl;

import com.TandK.service.KafkaStringMessageService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-01-28
 */
@Service
public class KafkaStringMessageServiceImpl implements KafkaStringMessageService {

    /**
     * KafkaTemplate<String, String>
     */
    private final KafkaTemplate<String, String> stringKafkaTemplate;

    public KafkaStringMessageServiceImpl(KafkaTemplate<String, String> stringKafkaTemplate) {
        this.stringKafkaTemplate = stringKafkaTemplate;
    }

    @Override
    public boolean sendStringMessage(String topic, String key, String message) {
        stringKafkaTemplate.send(topic, key, message);
        return true;
    }
}

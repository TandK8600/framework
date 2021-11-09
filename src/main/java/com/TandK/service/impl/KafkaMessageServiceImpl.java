package com.TandK.service.impl;

import com.TandK.core.kafka.callback.AsyncSendMessageFailureCallback;
import com.TandK.core.kafka.callback.MessageSuccessCallback;
import com.TandK.model.bo.SimpleMessage;
import com.TandK.service.KafkaMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-01-21
 */
@Slf4j
@Service
public class KafkaMessageServiceImpl implements KafkaMessageService {

    private final KafkaTemplate<String, SimpleMessage> kafkaTemplate;

    /**
     * 成功回调
     */
    private final MessageSuccessCallback messageSuccessCallback;

    /**
     * 失败回调
     */
    private final AsyncSendMessageFailureCallback asyncSendMessageFailureCallback;

    public KafkaMessageServiceImpl(KafkaTemplate<String, SimpleMessage> kafkaTemplate, MessageSuccessCallback messageSuccessCallback, AsyncSendMessageFailureCallback asyncSendMessageFailureCallback) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageSuccessCallback = messageSuccessCallback;
        this.asyncSendMessageFailureCallback = asyncSendMessageFailureCallback;
    }

    @Override
    public boolean sendMessageBySimple(String topic, String key, SimpleMessage simpleMessage) {
        // 发送并忘记
        kafkaTemplate.send(topic, key, simpleMessage);
        return true;
    }

    @Override
    public boolean sendMessageBySync(String topic, String key, SimpleMessage simpleMessage) throws ExecutionException, InterruptedException {
        // 同步发送
        SendResult<String, SimpleMessage> sendResult = kafkaTemplate.send(topic, key, simpleMessage).get();
        return sendResult.getRecordMetadata().offset() > 0;
    }

    @Override
    public boolean sendMessageByAsync(String topic, String key, SimpleMessage simpleMessage) {
        // 异步发送
        kafkaTemplate
                .send(topic, key, simpleMessage)
                // 添加异步回调处理
                .addCallback(messageSuccessCallback, asyncSendMessageFailureCallback);
        return true;
    }
}

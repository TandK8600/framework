package com.TandK.core.kafka.callback;

import com.TandK.model.bo.SimpleMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.SuccessCallback;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-01-28
 */
@Slf4j
@Component
public class MessageSuccessCallback implements SuccessCallback<SendResult<String, String>> {

    @Override
    public void onSuccess(SendResult<String, String> result) {
        if (null == result) {
            if (log.isWarnEnabled()) {
                log.warn("current result is null!!!");
            }
            return;
        }
        RecordMetadata recordMetadata = result.getRecordMetadata();
        if (log.isDebugEnabled() && null != recordMetadata) {
            log.debug(
                    "成功发送消息：主题: {}, 分区: {},偏移量: {} ,key: {}, value: {}",
                    recordMetadata.topic(),
                    recordMetadata.partition(),
                    recordMetadata.offset(),
                    result.getProducerRecord().key(),
                    result.getProducerRecord().value()
            );
        }
    }
}
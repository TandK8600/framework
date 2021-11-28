package com.TandK.core.kafka.listener;

import com.TandK.core.constant.CommonConst;
import com.TandK.model.bo.SimpleMessage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-01-21
 */
@Slf4j
@Component
public class KafkaMessageListener {

    @KafkaListener(
            topics = {CommonConst.Kafka.Topic.STRING_MESSAGE},
            groupId = CommonConst.Kafka.ConsumerGroupId.STRING_MESSAGE_GROUP
    )
    public void stringMessageListener(String stringMessage,
                                Acknowledgment acknowledgment) {
        log.info("哈哈哈哈");
        log.info("{} 执行线程: {} 消费消息 ==> 主题: {}",
                CommonConst.FULL_DATE_TIME_FORMATTER.format(LocalDateTime.now()),
                getSpringKafkaConsumerName(Thread.currentThread().getName()),
                stringMessage);
        acknowledgment.acknowledge();
    }

    private static String getSpringKafkaConsumerName(String rowThreadName) {
        if (StringUtils.isBlank(rowThreadName)) {
            return rowThreadName;
        }
        return rowThreadName.split("#")[1];
    }
}
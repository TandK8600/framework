package com.TandK.core.kafka;

import com.TandK.core.constant.CommonConst;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-01-22
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * KafkaProperties
     */
    private final KafkaProperties kafkaProperties;

    /**
     * Kafka 集群数量
     */
    @Value("${spring.kafka.broker-count}")
    private Short brokerCount;

    public KafkaTopicConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>(1 << 5);
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                StringUtils.arrayToCommaDelimitedString(kafkaProperties.getBootstrapServers().toArray()));
        return new KafkaAdmin(configs);
    }

    /**
     * 新建一个kafka主题: hello-spring-kafka 如果需要话
     *
     * @return NewTopic
     */
    @Bean
    public NewTopic stringMessageKafkaTopic() {
        return new NewTopic(
                CommonConst.Kafka.Topic.STRING_MESSAGE,
                kafkaProperties.getListener().getConcurrency(),
                brokerCount);
    }
}
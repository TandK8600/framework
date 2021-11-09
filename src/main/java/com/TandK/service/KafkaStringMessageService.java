package com.TandK.service;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-01-28
 */
public interface KafkaStringMessageService {

    /**
     * 测试 发送 string 类型的 message
     *
     * @param topic   目标 topic
     * @param key     消息 key
     * @param message 需要发送的消息
     * @return 是否发送成功
     */
    boolean sendStringMessage(String topic, String key, String message);
}

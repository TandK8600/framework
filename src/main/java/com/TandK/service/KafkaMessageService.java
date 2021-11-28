package com.TandK.service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-01-21
 */
public interface KafkaMessageService {

    /**
     * 发送 Kafka 消息: 发送并忘记
     *
     * @param topic               目标 topic
     * @param key                 消息 key
     * @param message      需要发送的消息
     * @return 是否执行成功
     */
    boolean sendMessageBySimple(String topic, String key, String message);


    /**
     * 同步发送  Kafka 消息
     *
     * @param topic               目标 topic
     * @param key                 消息 key
     * @param message     需要发送的消息
     * @return 是否执行成功
     * @throws ExecutionException   @see {@link ListenableFuture#get()}
     * @throws InterruptedException @see {@link ListenableFuture#get()}
     */
    boolean sendMessageBySync(String topic, String key, String message) throws ExecutionException, InterruptedException;

    /**
     * 异步发送  Kafka 消息
     *
     * @param topic               目标 topic
     * @param key                 消息 key
     * @param message      需要发送的消息
     * @return 是否执行成功过
     */
    boolean sendMessageByAsync(String topic, String key, String message);
}

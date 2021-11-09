package com.TandK.core.kafka.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-01-28
 */
@Slf4j
@Component
public class AsyncSendMessageFailureCallback implements FailureCallback {

    @Override
    public void onFailure(Throwable ex) {
        Throwable exCause = ex.getCause();
        log.error("异步:发送消息失败 ==>异常原因:{} 异常消息: {}", exCause != null ? exCause.getMessage() : "", ex.getMessage());
    }
}
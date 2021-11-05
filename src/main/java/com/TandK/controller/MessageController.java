package com.TandK.controller;

import com.TandK.core.kafka.KafkaProducer;
import com.TandK.core.support.http.HttpResponseSupport;
import com.TandK.core.support.http.ResponseEntity;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * use for kafka demo
 * @author TandK
 * @since 2021/11/4 16:50
 */
@Api(tags = "消息")
@RestController
@RequestMapping("api/messages")
public class MessageController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @ApiOperation(value = "测试kafka", response = ResponseEntity.class)
    @GetMapping()
    public ResponseEntity<Object> getUsers(String message){
        if(StringUtils.isBlank(message)){
            message = "default message";
        }
        kafkaProducer.sendDefault(message);
        kafkaProducer.sendAnotherTopic(message + "1234567890");
        return HttpResponseSupport.success(message);
    }

}

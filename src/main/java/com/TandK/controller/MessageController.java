package com.TandK.controller;

import cn.hutool.core.util.RandomUtil;
import com.TandK.core.constant.CommonConst;
import com.TandK.core.support.http.HttpResponseSupport;
import com.TandK.core.support.http.ResponseEntity;
import com.TandK.model.bo.SimpleMessage;
import com.TandK.model.po.UserPO;
import com.TandK.model.po.UserTokenPO;
import com.TandK.service.KafkaMessageService;
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
    private KafkaMessageService kafkaMessageService;

    @ApiOperation(value = "测试kafka", response = ResponseEntity.class)
    @GetMapping()
    public ResponseEntity<Object> testKafka(){
        final String key = "test-str-" + RandomUtil.randomInt(10, 500);
        final String message = RandomUtil.randomString(20);
        kafkaMessageService.sendMessageByAsync(CommonConst.Kafka.Topic.STRING_MESSAGE,
                key, message);

        return HttpResponseSupport.success(message);
    }

}

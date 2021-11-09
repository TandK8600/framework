package com.TandK.model.bo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-01-28
 */
@Data
@Accessors(chain = true)
public class SimpleMessage implements Serializable {
    private static final long serialVersionUID = -81606093254258919L;

    /**
     * message is
     */
    private Integer id;

    /**
     * message key
     */
    private String key;

    /**
     * message value
     */
    private String content;

    /**
     * message send time
     * <pre>
     * 因为  {@link JsonSerializer} 默认使用支持 {@link Date} 的序列化和反序列化对于Java8提供的诸如
     * {@link LocalDateTime}
     * {@link LocalDate}
     * {@link LocalTime}
     * 新的时间API需要通过注解指定相关序列化和反序列化器
     * </pre>
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime sendTime;
}
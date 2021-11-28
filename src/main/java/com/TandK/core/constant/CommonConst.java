package com.TandK.core.constant;

import java.time.format.DateTimeFormatter;

/**
 * 常用公共常量
 *
 * @author caood
 * @date 2021/04/06 17:43
 */
public interface CommonConst {
    interface IsDeleted {
        int NO = 0;
        int YES = 1;
    }

    /**
     * Status : 状态 0：正常；1：禁用
     */
    interface Status {
        /**
         * 0
         */
        int NORMAL = 0;
        /**
         * 1
         */
        int DISABLE = 1;

        /**
         * 1
         */
        int ONE = 1;

        /**
         * 2
         */
        int TWO = 2;

        /**
         * 3
         */
        int THREE = 3;
    }

    interface SystemOperator {
        String NAME = "系统操作";
        Long ACCOUNT_ID_LONG = -1L;
        Integer ACCOUNT_ID = -1;

    }

    /**
     * random base string
     */
    String RANDOM_BASE_STRING = "zxcvbnmasdfghjklqwertyuiopQAZWSXEDCRFVTGBYHNIUJKLMOP1234567890-=[],.;'";


    /**
     * format pattern: yyyy-MM-dd HH:mm:ss.SSS
     */
    DateTimeFormatter FULL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * kafka  相关常量
     */
    interface Kafka {
        /**
         * Kafka Consumer group id
         */
        interface ConsumerGroupId {
            /**
             * simple-string-consumer
             */
            String STRING_MESSAGE_GROUP = "string-message-group";
        }

        /**
         * Kafka Topic
         */
        interface Topic {
            /**
             * simple-string-message
             * <p>
             * 简单 String message 主题
             * </p>
             */
            String STRING_MESSAGE = "string-message";
        }
    }

}

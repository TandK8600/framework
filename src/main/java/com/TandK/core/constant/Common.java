package com.TandK.core.constant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import cn.hutool.core.util.StrUtil;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-03-31
 */
public interface Common {
    /**
     * 时区:  Asia/Shanghai
     */
    ZoneId CHINA_ZONE_ID = ZoneId.of("Asia/Shanghai");

    /**
     * UTC+8:00
     */
    ZoneOffset CHINA_ZONE_OFFSET = ZoneOffset.of("+08:00");
    /**
     * {@link DateTimeFormatter} yyyy-MM-dd HH:mm:ss
     */
    DateTimeFormatter NORMAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(CHINA_ZONE_ID);
    /**
     * {@link DateTimeFormatter}yyyyMMddHHmmss
     */
    DateTimeFormatter TIME_STRING_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(CHINA_ZONE_ID);

    /**
     * {@link DateTimeFormatter}yyyy-MM-dd
     */
    DateTimeFormatter LOCAL_DATA_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(CHINA_ZONE_ID);

    /**
     * {@link DateTimeFormatter}yyyyMMdd
     */
    DateTimeFormatter NUM_LOCAL_DATA_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(CHINA_ZONE_ID);

    /**
     * {@link DateTimeFormatter}HH:mm:ss
     */
    DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(CHINA_ZONE_ID);

    /**
     * 后台登陆 token 默认 有效期  单位: days/天
     */
    int BACKGROUND_LOGIN_TOKEN_TIMEOUT = 7;

    /**
     * <pre>
     *      pattern:
     *        etc. /2020-12-24/订单-202104151815-1618481733.jpg
     *        {} 2020-12-24
     *        {} 订单
     *        {} 202104151815
     *        {} 1618481733
     *        {} jpg
     *        {@link StrUtil#format(CharSequence, Object...)}
     *  </pre>
     */
    String PATTERN_OSS_FULL_FILE_NAME = "{}/{}-{}-{}.{}";

    /**
     * <pre>
     *    etc. 2021-04-17/连锁收益-20210417110634.xls
     *    {} 2021-04-17
     *    {} 连锁收益
     *    {} 20210417110634
     * </pre>
     */
    String PATTERN_OSS_EXCEL_FILE_NAME = "{}/{}-{}.xls";

    /**
     * file suffix : xls
     */
    String FILE_SUFFIX_XLS = "xls";

    /**
     * serial version field name :  serialVersionUID
     */
    String FIELD_NAME_SERIAL_VERSION = "serialVersionUID";

    /**
     * 配置文件加密前缀标识: AES@
     */
    String
            PROPERTIES_AES_ENCRYPT_PREFIX_TAG = "AES@";

    /**
     * {@link ObjectMapper}
     */
    ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .registerModule(new JavaTimeModule());

    /**
     * 字符串: &
     */
    String AND_SYMBOL = "&";

    /**
     * 字符串: =
     */
    String EQUALS_SYMBOL = "=";

    /**
     * 请求头: Content-Disposition
     */
    String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * response  content_type: xlsx
     */
    String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8";

    /**
     * response attachment: filename:xlsx
     *
     * @see {@link StrUtil#format(CharSequence, Object...)}
     */
    String ATTACHMENT_XLSX_PATTERN = "attachment;filename={}.xlsx";

    /**
     * 提示有浏览器 需要 Basic 认证
     */
    String EXAMPLE_BASIC_AUTHENTICATION = "Basic realm='username password'";
    /**
     * error message:  Http Basic 鉴权失败
     */
    String ERROR_MESSAGE_NON_BASIC_AUTHENTICATION = "Http Basic 鉴权失败";

    /**
     * 1MB 字节
     */
    int BYTE_OF_ONE_MB = 1048576;

    /**
     * 微信 refreshToken 过期时间: 30  天/days
     */
    int WECHAT_REFRESH_TOKEN_EXPIRE_IN = 30;

    /**
     * 后台用户微信扫码登陆,用户未绑定手机号时,前端传递的 call back url 将会 作为 微信扫码登陆 state 进行传递.
     * 为区分.该 state 会添加此前缀.
     */
    String STATE_WECHAT_BIND_PHONE_PREFIX = "bindUrl:";

    /**
     * str: ?
     */
    String STR_QUESTION_MARK = "?";

    /**
     * str: &
     */
    String STR_AND_SYMBOL = "&";

    /**
     * ContentType:  application/json
     */
    String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

    /**
     * Token payload key
     */
    interface TokenPayloadKey {
        /**
         * tudgo_permission_authority#account#account_id
         */
        String PERMISSION_AUTHORITY_ACCOUNT_ID = "a";

        /**
         * tudgo_permission_authority#member#member_id
         */
        String PERMISSION_AUTHORITY_MEMBER_UUID = "b";

        /**
         * tudgo_permission_authority#member#member_name
         */
        String PERMISSION_AUTHORITY_MEMBER_NAME = "c";

        /**
         * token 类型:{@link TokenType}
         */
        String TOKEN_TYPE = "d";
    }

    interface RsaKey {
        /**
         * token rsa private key
         */
        String TOKEN_PRIVATE_KEY_BASE64 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJXiyP1QJsmAIEikPiRORFKVm3YwAmDkxPaGoerJG8HEkyB2yWi5Grb9iSzyCtFvaaoOwmMPeiehXkwWibM2JH/+Z/sWY7nT6wj91jfCrVpqlkKE4uVl9+NPJpJr/ngdwgNBaLdkVD1wlkzFbIDULsE1cpCLbniQQbwIFHoMvxE/AgMBAAECgYACzAuiJofCxOBVWrNKLCe8Psx65SR2OG6whwyoe/5HnGp+dZkGmkW0WoDf1Nh81g5rvQDFpnlKGgnPc7A4Ju+BaKRasVSEQ1qHAt4aa3meHdRpC1UPQX2soky3l4/b9llERU26Avz61ZV56d++BErfoxsYQE1ZFBGkyB9pbEQJGQJBAM9Ocf12sMBpgB/V2VGawcz7KC8jpOwj3eoumw/mXnPNUIBiSEY4sfvKsHLXuVT1yjEinR4Qmlg5p8QQ/BSLyAMCQQC5F5TiqvmdEu/FRw8yekuzeW2y35LVUPWzY/+slqFCtmZxEJ/y/Tr1doWkf3ONRR7KzhjQCnp+oYr/8ZXmT+MVAkA6X6Ztbrx6kbqIPFlDR0aHB7na/Dh2XeshBBqZsY6rIQcn/4TGcTR86CgjTmdbaxKSH2MKkjZD3YhGB1pUFsAtAkBIi9SgTzFM/noaEbMELujy7HoxrO2pTHUxHdW9eMC2HDXkxQZcQCLWyOCyy/2ndlsOVF9nU2QVtA1+Kdt+DPvNAkEAzN6jC2WWCrQdEdcJ3gBNxNh4/5fLaECcmjELkEbe/oGgyU7ppRy79ccAJOJJ3iiwgAjnNCf4TE00gHcl73yBGA==";
        /**
         * token rsa public key
         */
        String TOKEN_PUBLIC_KEY_BASE64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCV4sj9UCbJgCBIpD4kTkRSlZt2MAJg5MT2hqHqyRvBxJMgdslouRq2/Yks8grRb2mqDsJjD3onoV5MFomzNiR//mf7FmO50+sI/dY3wq1aapZChOLlZffjTyaSa/54HcIDQWi3ZFQ9cJZMxWyA1C7BNXKQi254kEG8CBR6DL8RPwIDAQAB";

        /**
         * token  payload rsa private key
         */
        String TOKEN_PAYLOAD_PRIVATE_KEY_BASE64 = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALCNZjMJTvM/9En389hwdESssXuxm7/2UvKqsdAH7sgYGUAqF3XUqGI508DCd2421+Gep9CtuXpApQ4YGBBvkrDtct2PbmyU19mpIYQbShnDXmZIbpPSZ+NQWhNV+QaxTqdr/K7qX8oD8PHBOHF3U6dAWiedkNsGbWKM+yIoCGTZAgMBAAECgYAozm3GMwsT7v68D/rE1m0RKGcXQ1xP2JOgcfKKNiMrDWLcsT3d5qqKlXoLbgcjqrpn8/8unNCnYISlxszdE6SwLLjd8P5l1YDwI6cdxjw5yhugdgfwVFFyMCF/wWuWl+UH0+GlHFbAWL6vlfekcGKRiyMU1bNxS21DKA30/a0ruQJBAN6BS28FYcXMcf03QntqnMrg2NhcJBiHYFbUlx2/LgJSdbUgWV2qRLNSzV/Nc16ajUfOLlXcri7R73AnXrkpSlUCQQDLITgMpd61aQ56TKTKYtrsWU7pa00vhD5MXUoTyGBaN/v9Oe3dkDOM6wigmt+KQHvjh2cT5i8Kns+69os4R7x1AkA7jbWn+HPL4kWcNZ8os0dWEcpYVokeu5UwiGQOBS6GVpXErezdYgZTTNVFUBMR/iHUVz4VoyRHyc7hYNg8jO2lAkEAu5eWF7gnkQnQoQmfNnlNDPD9e3vo8HUEw6lz/AkVUrxZL2cMUY4WzZFRimD9CMS8pUgq3am6z+gSl+uQbw1w8QJBAKClLWaUsG1wlKDpSv3pdSBv6EHsJ1WuIl29iSFllOwmQq8hQS8iz/yB7+Xj3KhTn3geooKXkce4VmTM8cgEisA=";
        /**
         * token payload rsa public key
         */
        String TOKEN_PAYLOAD_PUBLIC_KEY_BASE64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwjWYzCU7zP/RJ9/PYcHRErLF7sZu/9lLyqrHQB+7IGBlAKhd11KhiOdPAwnduNtfhnqfQrbl6QKUOGBgQb5Kw7XLdj25slNfZqSGEG0oZw15mSG6T0mfjUFoTVfkGsU6na/yu6l/KA/DxwThxd1OnQFonnZDbBm1ijPsiKAhk2QIDAQAB";
    }

    interface AES {
        /**
         * AES encrypt/decrypt key(对称密钥)
         */
        String KEY = "9Zz4SBzd@4HfKkzs";
        /**
         * AES encrypt/decrypt iv(偏移量)
         */
        String IV = "585nNny8fUFbAu2!";
    }

    interface Number {

        /**
         * -1
         */
        int NEGATIVE_ONE = -1;

        /**
         * 0
         */
        int ZERO = 0;
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

        /**
         * 4
         */
        int FOUR = 4;

        /**
         * 5
         */
        int FIVE = 5;

        /**
         * 6
         */
        int SIX = 6;

        /**
         * 7
         */
        int SEVEN = 7;

        /**
         * 8
         */
        int EIGHT = 8;

        /**
         * 9
         */
        int NINE = 9;

        /**
         * 10
         */
        int TEN = 10;

        /**
         * 11
         */
        int ELEVEN = 11;

        /**
         * 12
         */
        int TWELVE = 12;

        /**
         * 24
         */
        int TWENTY_FOUR = 24;

        /**
         * 最大优先级
         */
        int MAX_PRIORITY = 999;

        /**
         * number  string : 100
         */
        String STR_ONE_HUNDRED = "100";

        int THOUSANDS_OF_ONE = 1000;
    }

    /**
     * {@link java.math.BigDecimal#setScale(int, RoundingMode)} )}
     */
    interface Scale {
        /**
         * scale : gross margin
         */
        int SCALE_GROSS_MARGIN = 2;
        /**
         * scale: yield rate
         */
        int SCALE_YIELD_RATE = 2;

        /**
         * scale: commission
         */
        int SCALE_COMMISSION = 2;

        /**
         * scale: risk percentage
         */
        int RISK_PERCENTAGE = 2;
    }

    /**
     * 请求头名称
     */
    interface RequestHeader {
        /**
         * request header: x-permission-account-id
         */
        String HEADER_PERMISSION_ACCOUNT_ID = "x-permission-account-id";
        /**
         * request header: x-permission-member-uuid
         */
        String HEADER_PERMISSION_MEMBER_UUID = "x-permission-member-uuid";
        /**
         * request header: x-permission-member-name
         */
        String HEADER_PERMISSION_MEMBER_NAME = "x-permission-member-name";

        /**
         * request  header: x-authentication-token
         */
        String HEADER_AUTHENTICATION_TOKEN = "x-authentication-token";
    }

    /**
     * 公共字段
     */
    interface CommonField {
        /**
         * 表表主键字段名
         */
        String COMMON_PRIMARY_KEY_NAME = "uuid";

        /**
         * field: creatorAccountId
         */
        String FIELD_CREATOR_ACCOUNT_ID = "creatorAccountId";
        /**
         * field: creatorName
         */
        String FIELD_CREATOR_NAME = "creatorName";

        /**
         * field: updaterAccountId
         */
        String FIELD_UPDATER_ACCOUNT_ID = "updaterAccountId";
        /**
         * field: updaterName
         */
        String FIELD_UPDATER_NAME = "updaterName";
    }


    /**
     * 系统字典数据类型
     */
    interface SystemDictType {
        /**
         * dictType : 商品淘汰条件 : 销售小于  /  商品淘汰条件: 销售额低于
         */
        String PRODUCT_WEED_OUT_RULE = "Product Weed Out Rule";
    }

    /**
     * 系统字典描述
     */
    interface SystemDictDesc {

        /**
         * 商品淘汰规则
         */
        String PRODUCT_WEED_OUT_RULE = "商品淘汰规则";

        /**
         * 商品淘汰规则: 销售数量小于
         */
        String LESS_THAN_SALES_COUNT = "商品淘汰规则: 销售小于";
        /**
         * 商品淘汰规则: 销售额小于
         */
        String LESS_THAN_SALES_AMOUNT = "商品淘汰规则: 销售额小于";
    }

    /**
     * 销售周期
     */
    interface SalesPeriod {
        /**
         * 小时
         */
        int HOURS = 21;
        /**
         * 分钟
         */
        int MINUTES = 0;
        /**
         * 秒
         */
        int SECONDS = 0;

        /**
         * 纳秒
         */
        int NANOSECOND = 0;
    }

    /**
     * token 类型
     */
    interface TokenType {
        /**
         * token type: 系统后台管理用户
         */
        String ADMIN = "admin";

        /**
         * token type: 小程序用户端
         */
        String CUSTOMER = "customer";

        /**
         * token type: 合伙人端
         */
        String PARTNER = "partner";

        /**
         * token type: 供应商端
         */
        String SUPPLIER = "supplier";
    }

    /**
     * 售后风险比例
     * 绿色小于等于1%，黄色大于1%且小于2%，红色售后金额占比大于等于2%
     */
    interface AfterSaleRiskPercentage {
        /**
         * 低风险
         */
        BigDecimal THRESHOLD_LOW = BigDecimal.valueOf(0.01);

        /**
         * 高风险
         */
        BigDecimal THRESHOLD_HIGH = BigDecimal.valueOf(0.02);
    }

    /**
     * 正则表达式
     */
    interface RegexPattern {
        /**
         * regex: 正整数
         */
        String POSITIVE_INTEGER = "[1-9]+[0-9]*";
    }
}

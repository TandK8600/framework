package com.TandK.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Editor;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.TandK.core.constant.Common;
import com.TandK.core.exception.BizCodeEnum;
import com.TandK.core.exception.RRException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 公共工具类
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021/04/04
 */
@Slf4j
public class CommonUtil {

    /**
     * 将 queryStr 转化成 map. 不将 map key 中的 下划线转驼峰
     *
     * <pre>
     *      etc.  last_name=mercyModest
     *         map
     *            key: last_name
     *            value: mercyModest
     * </pre>
     *
     * @param queryStr queryStr
     * @return map or null
     */
    public static Map<String, String> parseQueryStr(String queryStr) {
        return parseQueryStr(false, queryStr);
    }

    /**
     * 将 queryStr 转化成 map
     *
     * <pre>
     *      camelCaseKey: false
     *      etc.  last_name=mercyModest
     *         map
     *            key: last_name
     *            value: mercyModest
     *      camelCaseKey: true
     *      etc.  last_name=mercyModest
     *         map
     *            key: lastName
     *            value: mercyModest
     * </pre>
     *
     * @param camelCaseKey 是否将 map key 的下划线转驼峰
     * @param queryStr     queryStr
     * @return map or null
     */
    public static Map<String, String> parseQueryStr(boolean camelCaseKey, String queryStr) {
        if (StrUtil.isBlank(queryStr)) {
            return null;
        }
        String[] strings = queryStr.split(Common.AND_SYMBOL);
        Map<String, String> resultMap = new HashMap<>(strings.length);
        for (String str : strings) {
            String[] split = str.split(Common.EQUALS_SYMBOL);
            String key = split[0];
            if (camelCaseKey) {
                key = StrUtil.toCamelCase(key);
            }
            resultMap.put(key, URLUtil.decode(split[1], CharsetUtil.CHARSET_UTF_8).replace("'", "").replace("\"", ""));
        }
        return resultMap;
    }

    /**
     * 将 queryStr 转化为 对应 {@link Class} 实例
     *
     * @param queryStr    queryStr
     * @param targetClass 目标 class
     * @param <T>         target type
     * @return null or the install of parameter class
     * @throws RRException {@link BeanUtil#toBean(Object, Class)}
     */
    public static <T> T parseQueryStr(String queryStr, Class<T> targetClass) throws RRException {
        if (StrUtil.isBlank(queryStr)) {
            return null;
        }
        String[] strings = queryStr.split(Common.AND_SYMBOL);
        Map<String, String> queryStrMap = new HashMap<>(strings.length);
        for (String str : strings) {
            String[] split = str.split(Common.EQUALS_SYMBOL);
            String key = split[0];
            queryStrMap.put(key,
                    URLUtil.decode(split[1], CharsetUtil.CHARSET_UTF_8).replace("'", "").replace("\"", ""));
        }
        try {
            return BeanUtil.toBean(queryStrMap, targetClass);
        } catch (Exception e) {
            log.error("CommonUtil:parseQueryStr(String,Class)  ==>  error: {}", e.getMessage());
            throw new RRException(e.getMessage(), BizCodeEnum.SYSTEM_ERROR.getCode());
        }
    }

    /**
     * 获取 {@link LocalDate#now()} 时区: {@link Common#CHINA_ZONE_ID}
     *
     * @return now data string of pattern : yyyy-MM-dd
     */
    public static String getCurrentLocalDateString() {
        return Common.LOCAL_DATA_FORMATTER.format(LocalDate.now(Common.CHINA_ZONE_ID));
    }

    /**
     * 获取 {@link LocalDate#now()} 时区: {@link Common#CHINA_ZONE_ID}
     *
     * @return now data num of pattern : yyyyMMdd
     */
    public static Long getCurrentLocalDateNum() {
        return Long.parseLong(Common.NUM_LOCAL_DATA_FORMATTER.format(LocalDate.now(Common.CHINA_ZONE_ID)));
    }

    /**
     * 获取当前 时间戳 . 时区: {@link Common#CHINA_ZONE_ID}
     *
     * @return 当前时间戳 (秒)
     */
    public static Long getCurrentInstancesOfSeconds() {
        return Instant.now().atZone(Common.CHINA_ZONE_ID).toEpochSecond();
    }

    /**
     * 获取当前时间字符串 pattern: {@link Common#TIME_STRING_DATE_FORMAT}
     *
     * @return 时间字符串 pattern (yyyyMMddHHmmss)
     */
    public static String getCurrentTimeString() {
        return Common.TIME_STRING_DATE_FORMAT.format(LocalDateTime.now(Common.CHINA_ZONE_ID));
    }

    /**
     * 生成 oss 文件名称 etc. /2021-04-15/订单-202104151815-1618481733.jpg
     *
     * @param moduleName 模块名称 etc. 订单
     * @param fileSuffix 文件后缀(不带 .) etc. jpg
     * @return 生成的文件名称 etc. /2021-04-15/订单-202104151815-1618481733.jpg
     */
    public static String generateOssFileName(String moduleName, String fileSuffix) {
        if (StrUtil.isBlank(moduleName)) {
            throw new RRException("CommonUtil#generateOssFileName : moduleName can't be  blank",
                    BizCodeEnum.VALIDATION_EXCEPTION.getCode());
        }
        if (StrUtil.isBlank(fileSuffix)) {
            throw new RRException("CommonUtil#generateOssFileName : fileSuffix can't be  blank",
                    BizCodeEnum.VALIDATION_EXCEPTION.getCode());
        }
        if (fileSuffix.startsWith(StrUtil.DOT)) {
            fileSuffix = StrUtil.removePrefix(fileSuffix, StrUtil.DOT);
        }
        return StrUtil.format(Common.PATTERN_OSS_FULL_FILE_NAME, getCurrentLocalDateString(), moduleName,
                getCurrentTimeString(), getCurrentInstancesOfSeconds(), fileSuffix);
    }

    /**
     * 生成 jpg 文件名称 etc. /2021-04-15/订单-202104151815-1618481733.jpg
     *
     * @param moduleName 模块名称 etc. 订单
     * @return 生成的文件名称 etc. /2021-04-15/订单-202104151815-1618481733.jpg
     */
    public static String generateOssFileNameOfJpg(String moduleName) {
        if (StrUtil.isBlank(moduleName)) {
            throw new RRException("CommonUtil#generateOssFileNameOfJpg : moduleName can't be  blank",
                    BizCodeEnum.VALIDATION_EXCEPTION.getCode());
        }
        return generateOssFileName(moduleName, "jpg");
    }

    /**
     * 生成 xls 文件名称 etc. /2021-04-15/成员列表-202104151815.xls
     *
     * @param excelContentDesc excel 内容描述 etc. 成员列表
     * @return 生成的文件名称 etc. /2021-04-15/成员列表-202104151815.xls
     */
    public static String generateOssFileNameOfXls(String excelContentDesc) {
        if (StrUtil.isBlank(excelContentDesc)) {
            throw new RRException("CommonUtil#generateOssFileNameOfXls : moduleName can't be  blank",
                    BizCodeEnum.VALIDATION_EXCEPTION.getCode());
        }
        return StrUtil.format(Common.PATTERN_OSS_EXCEL_FILE_NAME, getCurrentLocalDateString(), excelContentDesc,
                getCurrentTimeString());
    }

    /**
     * 将 target 转换成 json 字符串。如果转换失败,则使用 {@link Object#toString()}
     *
     * @param target 需要转换的目标对象
     * @return string (json/toString) or null (if target is null)
     */
    public static String getString(Object target) {
        if (target == null) {
            return null;
        }
        try {
            return Common.OBJECT_MAPPER.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            return target.toString();
        }
    }

    /**
     * 获取 最先抛出的异常
     *
     * @param throwable {@link Throwable}
     * @return {@link Throwable} or null (the parameter throwable is null)
     */
    public static Throwable getRootCauseException(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        Throwable causeException = throwable.getCause();
        if (causeException != null) {
            return getRootCauseException(causeException);
        }
        return throwable;
    }

    /**
     * 将 对象 转化成 JSON 对象
     *
     * @param target 需要转换成 JSON 字符串的对象
     * @return json string or "" (if the target is empty or null)
     * @throws RRException {@link JsonProcessingException}
     */
    public static String getJsonString(Object target) throws RRException {
        if (BeanUtil.isEmpty(target)) {
            return "";
        }
        try {
            return Common.OBJECT_MAPPER.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            String errorMessage = getRootCauseException(e).getMessage();
            log.error("CommonUtil:getJsonString: {}", errorMessage);
            throw new RRException(errorMessage, BizCodeEnum.SYSTEM_ERROR.getCode());
        }
    }

    /**
     * 将 JSON 字符串转化成 {@link Object}
     *
     * @param jsonStr json string
     * @param type    {@link Type}
     * @return the object or "" (the json string is empty or type is null)
     * @throws RRException {@link JsonProcessingException}
     */
    public static Object jsonToObject(String jsonStr, Type type) throws RRException {
        if (StrUtil.isBlank(jsonStr)) {
            return null;
        }
        if (type == null) {
            return null;
        }
        try {
            final ObjectMapper objectMapper = Common.OBJECT_MAPPER;
            JavaType javaType = objectMapper.getTypeFactory().constructType(type);
            return objectMapper.readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            String errorMessage = getRootCauseException(e).getMessage();
            log.error("CommonUtil:jsonToObject: {}", errorMessage);
            throw new RRException(errorMessage, BizCodeEnum.SYSTEM_ERROR.getCode());
        }
    }

    /**
     * json 反序列化
     *
     * @param jsonStr       目标 json 字符串
     * @param typeReference {@link TypeReference}
     * @param <T>
     * @return T or null (jsonStr is null/typeReference is null)
     */
    public static <T> T jsonDeserialization(String jsonStr, TypeReference<T> typeReference) {
        try {
            if (StrUtil.isBlank(jsonStr)) {
                return null;
            }
            if (typeReference == null) {
                return null;
            }
            return Common.OBJECT_MAPPER.readValue(jsonStr, typeReference);
        } catch (JsonProcessingException e) {
            String errorMessage = getRootCauseException(e).getMessage();
            log.error("CommonUtil:jsonToObject: {}", errorMessage);
            throw new RRException(errorMessage, BizCodeEnum.SYSTEM_ERROR.getCode());
        }
    }

    public static <T> T parseObject(String jsonStr, Class<T> targetClass) {
        try {
            if (StrUtil.isBlank(jsonStr)) {
                return null;
            }
            if (targetClass == null) {
                return null;
            }
            return Common.OBJECT_MAPPER.readValue(jsonStr, targetClass);
        } catch (JsonProcessingException e) {
            String errorMessage = getRootCauseException(e).getMessage();
            log.error("CommonUtil:jsonToObject: {}", errorMessage);
            throw new RRException(errorMessage, BizCodeEnum.SYSTEM_ERROR.getCode());
        }
    }


    public static Map parseObjectToMap(Object obj) {
        try {
            if (ObjectUtil.isEmpty(obj)) {
                return null;
            }

            return Common.OBJECT_MAPPER.readValue(Common.OBJECT_MAPPER.writeValueAsString(obj), Map.class);
        } catch (JsonProcessingException e) {
            String errorMessage = getRootCauseException(e).getMessage();
            log.error("CommonUtil:parseObjectToMap: {}", errorMessage);
            throw new RRException(errorMessage, BizCodeEnum.SYSTEM_ERROR.getCode());
        }
    }

    public static String getWeedOutCondition1(Integer lessThanSalesCount) {
        return "销售小于" + lessThanSalesCount;
    }

    public static Integer weedOutCondition1Resolve(String weedOutCondition1Str) {
        if (StrUtil.isBlank(weedOutCondition1Str)) {
            return null;
        }
        return Integer.parseInt(weedOutCondition1Str.replace("销售小于", ""));
    }

    public static String getWeedOutCondition2(BigDecimal lessThanSalesAmount) {
        return "销售额小于" + lessThanSalesAmount.toString() + "元";
    }

    public static BigDecimal weedOutCondition2Resolve(String weedOutCondition2Str) {
        if (StrUtil.isBlank(weedOutCondition2Str)) {
            return null;
        }
        weedOutCondition2Str = weedOutCondition2Str.replace("销售额小于", "").replace("元", "");
        return new BigDecimal(weedOutCondition2Str);
    }

    public static String beforeFillNumber(Number number, char fillChar, int length) {
        if (number == null) {
            return null;
        }
        return StrUtil.fillBefore(String.valueOf(number), fillChar, length);
    }

    public static String beforeFillStr(String str, char fillChar, int length) {
        if (str == null) {
            return null;
        }
        return StrUtil.fillBefore(str, fillChar, length);
    }

    /**
     * 将  {@code riskPercentage}   转化成 风险比例文字
     *
     * @param riskPercentage {@code riskPercentage}
     * @return 风险比例文字  (低/中/高风险)
     */
    public static String toRiskPercentageStr(BigDecimal riskPercentage) {
        //绿色小于等于1%，黄色大于1%且小于2%，红色售后金额占比大于等于2%
        if (Common.AfterSaleRiskPercentage.THRESHOLD_LOW.compareTo(riskPercentage) >= 0) {
            return "低风险";
        } else if (Common.AfterSaleRiskPercentage.THRESHOLD_HIGH.compareTo(riskPercentage) >= 0) {
            return "高风险";
        } else {
            return "中风险";
        }
    }
}

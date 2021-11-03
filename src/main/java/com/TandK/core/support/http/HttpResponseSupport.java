package com.TandK.core.support.http;

import com.TandK.core.exception.RRException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseSupport {
    private HttpResponseSupport() {
    }

    private static JSONObject responseJson;
    private static JSONObject responseErrorJson;

    static {
        responseJson = new JSONObject();
        responseErrorJson = new JSONObject();
    }


    public synchronized static ResponseEntity<Object> success(String msg, String reason) {
        responseErrorJson.clear();
        responseJson.put("code", 0);
        responseJson.put("message", msg);
        responseJson.put("reason", reason);
        responseJson.put("data", msg);
        return obtainResponseEntity(HttpStatus.OK, responseJson);
    }
    public synchronized static ResponseEntity<Object> success(Object obj) {
        responseJson = new JSONObject();
        responseJson.put("code", 0);
        responseJson.put("data", obj);
        return obtainResponseEntity(HttpStatus.OK, responseJson);
    }
    public synchronized static ResponseEntity<Object> success(IPage obj) {
        Map<String, Object> map = new HashMap<String, Object>(2) {{
            put("total",obj.getTotal());
            put("data",obj.getRecords());
        }};

        responseJson = new JSONObject();
        responseJson.put("code", 0);
        responseJson.put("data", map);
        return obtainResponseEntity(HttpStatus.OK, responseJson);
    }

    public synchronized static ResponseEntity<Object> error(HttpStatus httpStatus, String msg, Object obj) {
        JSONObject tempJSON = new JSONObject();
        responseErrorJson.clear();
        responseErrorJson.put("type", httpStatus.getReasonPhrase());
        responseErrorJson.put("message", msg);
        responseErrorJson.put("reason", obj);
        responseErrorJson.put("code", "9999");
        return obtainResponseEntity(httpStatus, responseErrorJson);
    }


    private synchronized static ResponseEntity<Object> obtainResponseEntity(HttpStatus httpStatus, Object response) {
        return new ResponseEntity<>(response, httpStatus);
    }


    public synchronized static ResponseEntity<Object> error(HttpStatus httpStatus, RRException e) {
        JSONObject tempJSON = new JSONObject();
        responseErrorJson.clear();
        responseErrorJson.put("type", httpStatus.getReasonPhrase());
        responseErrorJson.put("message", e.getMessage());
        responseErrorJson.put("code", e.getCode());
        return obtainResponseEntity(httpStatus, responseErrorJson);
    }
}

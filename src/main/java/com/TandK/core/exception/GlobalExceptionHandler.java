package com.TandK.core.exception;

import com.TandK.core.constant.HttpMessageConstant;
import com.TandK.core.support.http.HttpResponseSupport;
import com.TandK.core.support.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author TandK
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统异常，返回服务器内部错误
     * @param e
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> systemException(Throwable e, HttpServletRequest request) {
        log.error("出现了 全局Exception异常, 地址是：{}，异常是：{}", request.getRequestURI(), e);
        return HttpResponseSupport.error(HttpStatus.INTERNAL_SERVER_ERROR,
                HttpMessageConstant.HTTP_MESSAGE_INTERNAL_SERVER_ERROR, e.getMessage());
    }


    /**
     * 业务异常
     * @param e
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RRException.class)
    public ResponseEntity<Object> RRException(RRException e, HttpServletRequest request) {
        log.error("RRException, 地址是：{}，异常是：{}", request.getRequestURI(), e);
        return HttpResponseSupport.error(HttpStatus.OK, e);
    }

    /**
     * 用户异常
     * @param e
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> userException(UserException e, HttpServletRequest request) {
        return HttpResponseSupport.error(HttpStatus.UNAUTHORIZED, e);
    }

    /**
     * 校验异常
     * @param e
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> bindException(BindException e, HttpServletRequest request) {
        log.error("BindException, 地址是：{}，异常是：{}", request.getRequestURI(), e);
        StringBuilder errorMessage = new StringBuilder("Invalid Request:");
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (int i = 0; i < fieldErrors.size(); i++) {
            FieldError fieldError = fieldErrors.get(i);
            if (i == fieldErrors.size() - 1) {
                errorMessage.append(fieldError.getDefaultMessage());
            } else {
                errorMessage.append(fieldError.getDefaultMessage()).append(", ");
            }
        }

        log.error(bindingResult.getFieldError().getDefaultMessage());
        return HttpResponseSupport.error(HttpStatus.BAD_REQUEST, HttpMessageConstant.HTTP_MESSAGE_BAG_REQUEST, errorMessage.toString());

    }
}
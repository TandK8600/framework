package com.TandK.core.interceptor;


import com.TandK.core.annotation.IgnoreToken;
import com.TandK.core.exception.BizCodeEnum;
import com.TandK.core.exception.RRException;
import com.TandK.core.support.threadlocal.UserThreadLocal;
import com.TandK.model.po.UserPO;
import com.TandK.service.UserTokenService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * token拦截器
 * @author TandK
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserTokenService userTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断有没有IgnoreToken注解,如果有，直接放行
        if(method.isAnnotationPresent(IgnoreToken.class)){
            return true;
        }

        // 先获取前端传来的token，这里的Authrization是小程序推荐的叫法，具体要和前端传来的变量名一致
        String token = request.getHeader("Authrization");
        if(StringUtils.isBlank(token) || token.equals("[object Undefined]")){
            // 没带token
            throw new RRException(BizCodeEnum.UNAUTHORIZED);
        }

        // 根据token获取用户信息
        UserPO userPO = userTokenService.getUserByToken(token);
        if(userPO == null){
            // 鉴权失败
            throw new RRException(BizCodeEnum.UNAUTHORIZED);
        }

        // 存储用户信息
        UserThreadLocal.set(userPO);
        return true;
    }
}
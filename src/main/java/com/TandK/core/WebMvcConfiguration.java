package com.TandK.core;



import com.TandK.core.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器黑白名单注册的类，这里根据具体业务来，拦截的是/api/**，如果不是这个路由，就不会进入拦截器
 * @author TandK
 *
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

    // 保证单例
    @Bean
    public AuthInterceptor getAuthInterceptor(){
        return new AuthInterceptor();
    }

    /**
     * 拦截器黑白名单注册的具体方法
     * @return
     */
    @Bean
    public WebMvcConfigurer corConfiguration() {
        return new WebMvcConfigurer() {
            // 注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(getAuthInterceptor()).addPathPatterns("/api/**");
            }
        };
    }
}
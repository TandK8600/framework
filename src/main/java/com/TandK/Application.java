package com.TandK;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;

@MapperScan("com.TandK.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

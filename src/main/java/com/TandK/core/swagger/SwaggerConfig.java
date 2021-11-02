package com.TandK.core.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Profile("!pro")
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {


    @Bean
    public Docket createAPI() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authrization").description("Authrization安全验证")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUYW5kSyIsInVzZXJVdWlkIjoiMTQyOTA1MjA5MDQzODI5OTY1MCIsImlhdCI6MTYyOTU0NzU0MywianRpIjoiZTEzNDRiZTktZTg5ZS00YWY2LThlM2ItMjkwZTNmNjNmZWNhIn0.iKTVAZYGK0IavBcjjSVahUPGvvmoWxdDB9Dh0sqncjA")
                .required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_12)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.TandK.controller"))
                //过滤生成链接
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .useDefaultResponseMessages(false)
                .securitySchemes(Collections.singletonList(apiKey()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("TandK", "", "1098446876@qq.com");
        return new ApiInfoBuilder().title("demo🧒")
                .description("服务端接口文档")
                .contact(contact)
                .version("1.0.0")
                .build();
    }

    @Bean
    public SecurityScheme apiKey() {
        return new ApiKey("Authrization", "Authrization", "header");
    }


}


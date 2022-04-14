package studio.banner.forumwebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/13 22:00
 * @role: Swagger 配置类
 */
@Configuration
@EnableOpenApi
public class Swagger3Config {

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("studio.banner.forumwebsite.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContexts()))
                .securitySchemes(Arrays.asList(securitySchemes()))
                .apiInfo(apiInfo())
                .select()
                .build();
    }

        /**
         *
         * enable 是否启动Swagger，如果为False，则Swagger不能在浏览器中访问
         *                  在确定开发环境时用得到，咱现在用不到
         *                  .enable(false)
         *                  RequestHandlerSelectors 配置要扫描的接口方式
         *                  basePackage: 指定要扫描的包 通常使用这个
         *                  any(): 扫描全部
         *                  none(): 不扫描
         *                  withClassAnnotation: 扫描类上的注解，参数是一个注解的反射对象
         *                  withMethodAnnotation: 扫描方法上的注解，参数是一个注解的反射对象
         *                 .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
         *                  path() 过滤什么路径
         *                  .paths(PathSelectors.ant(""))
         */

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "forumWebsite接口",
                "用于测试论坛功能",
                "v1.0",
                "#",
                new Contact("测试", "#", "2952434583@qq.com"),
                "Apache 2.0",
                "#",
                new ArrayList<>());
    }

    private SecurityScheme securitySchemes() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.any())
                        .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("xxx", "描述信息");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }
}


package com.aojun.user.server.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.*;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.Optional;

/**
 * @description: swagger配置文件
 **/
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config
{
    /**
     * 定义分隔符
     */
    private static final String SPLITOR = ";";

    @Value("${spring.profiles.active}")
    private String env;
    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public Docket createDocket(ServletContext servletContext)
    {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(basePackage("com.aojun.user.server.controller" + SPLITOR ))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title(serviceName + "接口文档")
                        .version("1.0")
                        .contact(new Contact("xxx","",""))
                        .license("XXX有限公司")
                        .build())
                // 如果为生产环境，则不创建swagger
                .enable(!"real".equals(env));

        // 在knife4j前端页面的地址路径中添加gateway网关的项目名，解决在调试接口、发送请求出现404的问题
        docket.pathProvider(new RelativePathProvider(servletContext)
        {
            @Override
            public String getApplicationBasePath()
            {
                return "/gateway-server" + super.getApplicationBasePath();
            }
        });

        return docket;
    }

    /**
     * @description 重写basePackage方法，使能够实现多包访问
     * @param basePackage 所有包路径
     * @return Predicate<RequestHandler>
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)::apply).orElse(true);
    }

    /**
     * @description 重写basePackage方法，使能够实现多包访问
     * @param basePackage 所有包路径
     * @return Function<Class<?>, Boolean>
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(SPLITOR)) {
                assert input != null;
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * @description 重写basePackage方法，使能够实现多包访问
     * @param input
     * @return Optional<? extends Class<?>>
     */
    private static Optional<Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }
}

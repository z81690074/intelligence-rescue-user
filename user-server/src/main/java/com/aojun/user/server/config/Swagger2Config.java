package com.aojun.user.server.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.*;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

/**
 * @description: swagger配置文件
 **/
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config
{
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
}

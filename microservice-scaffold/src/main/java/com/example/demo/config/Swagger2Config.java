package com.example.demo.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	
	static final String basePackage = "com.example.demo.web";
	
	@Bean
	public Docket createRestApi(){
		return new Docket(DocumentationType.SWAGGER_2)
			//.groupName("MyGroup")
			//.genericModelSubstitutes(DeferredResult.class)
            //.useDefaultResponseMessages(false)
            //.forCodeGeneration(true)
            //.pathMapping("/springboot")
			.select()
			//.apis(RequestHandlerSelectors.basePackage(basePackage))//匹配包
			//.apis(RequestHandlerSelectors.withClassAnnotßation(Api.class))//匹配类注解
			.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//匹配方法注解
			//.paths(PathSelectors.any())
			//.paths(Predicates.or(PathSelectors.regex("/users/.*")))//过滤的接口
			.build()
			.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API标题位")
                .description("API描述位")
                .termsOfServiceUrl("服务URL的规范描述")
                .contact(new Contact("接口联系人", "联系人网站", "联系人邮箱"))
                .version("1.0")
                .build();
    }
}

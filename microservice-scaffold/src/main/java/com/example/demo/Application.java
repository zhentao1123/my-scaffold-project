package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc //开启springmvc
@ServletComponentScan(basePackages="com.example.demo.web.servlet") //扫描Servlet
@EnableConfigurationProperties
//@Import({Swagger2Config.class, WebMvcConfig.class}) //导入配置类;可显示声明，也可以通过保证放在扫描包目录
//@ImportResource(locations={"classpath:applicationContext.xml"}) //导入配置xml;不能使用默认的根目录下的application.xml，会冲突
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

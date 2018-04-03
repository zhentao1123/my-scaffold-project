package com.example.demo.config.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 为了能使常量环境可配，并且以静态方式调用，应用启动时在该处初始化
 * 在类似非Spring受管的对象里需要读取配置的场景使用
 *
 */
@Configuration
public class ConstantsConfig {
	
	@Value("${app.name}")
	private String appName;
	
	@Bean
	public Constants constants() {
		Constants.APP_NAME = appName;
		return new Constants();
	}
}

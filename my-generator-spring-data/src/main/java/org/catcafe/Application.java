package org.catcafe;

import org.catcafe.util.DBInfoUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class Application {

	@Bean
	@ConfigurationProperties(prefix="datasource")
	public DBInfoUtil getDBInfoUtil() {
		DBInfoUtil bean = new DBInfoUtil();
		return bean;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

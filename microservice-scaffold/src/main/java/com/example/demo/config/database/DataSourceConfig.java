package com.example.demo.config.database;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	
	//@Bean(name="dataSourceDefault")
	//@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSourceDefault() {
		/**
		 * scan and load first exist class in class list below in class path, tomcat pool datasource will be load first
		 * "org.apache.tomcat.jdbc.pool.DataSource",
		 * "com.zaxxer.hikari.HikariDataSource",
		 * "org.apache.commons.dbcp.BasicDataSource", // deprecated
		 * "org.apache.commons.dbcp2.BasicDataSource"
		 */
		DataSource dataSource = DataSourceBuilder.create().build();
		logger.debug("--------- [Current DataSource] : " + dataSource.getClass().getName());
		return dataSource;
	}
	
	@Bean(name="dataSourceHikariCP")
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public DataSource dataSourceHikariCP() {
		HikariDataSource dataSource = new HikariDataSource();
		return dataSource;
	}
}

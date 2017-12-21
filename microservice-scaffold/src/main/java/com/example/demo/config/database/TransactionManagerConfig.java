package com.example.demo.config.database;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages={"com.example.demo.dao"})
public class TransactionManagerConfig {

	@Resource(name="dataSourceHikariCP")
	DataSource dataSource;
	
	@Resource(name="entityManagerFactory")
	EntityManagerFactory entityManagerFactory;
	
	// -- TransactionManager --
	@Bean(name="transactionManager")
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dataSource);
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	// -- EntityManageFactory --
	@Bean(name="entityManagerFactory")
	public EntityManagerFactory entityManagerFactory() 
	{
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setPackagesToScan("com.example.demo.dao");
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setGenerateDdl(true);
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		//factory.setJpaProperties(jpaProperties);
		factory.afterPropertiesSet(); //在完成了其它所有相关的配置加载以及属性设置后,才初始化
		return factory.getObject();
	}
	
	// --JdbcTemplate--
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

	// -- DataSource --
}

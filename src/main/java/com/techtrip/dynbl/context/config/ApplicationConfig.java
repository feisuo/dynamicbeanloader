package com.techtrip.dynbl.context.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
/*@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.techtrip.my.repositories" })*/
@ComponentScan(basePackages = { "com.techtrip.spring.beans.factory",
		"com.techtrip.dynbl.web.controllers" })
public class ApplicationConfig {

/*	@Bean
	public DataSource dataSource() {
		org.apache.commons.dbcp.BasicDataSource dataSource = new org.apache.commons.dbcp.BasicDataSource();
		dataSource.setUrl(AppConfig.DB_URL);
		dataSource.setDriverClassName(AppConfig.DB_DRIVER_CLASS_NAME);
		dataSource.setUsername(AppConfig.DB_USERNAME);
		dataSource.setPassword(AppConfig.DB_PASSWORD);
		return dataSource;
	}*/
}

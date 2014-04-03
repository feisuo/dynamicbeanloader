/*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
* 
* See <http://www.gnu.org/licenses/>.
*/
package com.techtrip.dynbl.context.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
/*
 * @EnableTransactionManagement
 * 
 * @EnableJpaRepositories(basePackages = { "com.techtrip.my.repositories" })
 */
@ComponentScan(basePackages = { "com.techtrip.spring.beans.factory",
		"com.techtrip.dynbl.web.controllers" })
public class ApplicationConfig {

	// -Dspring.beaninfo.ignore
	@Value("${spring.beaninfo.ignore}")
	private String springBeaninfoIgnore;

	// -Dspring.beaninfo.ignore
	@Value("${com.techtrip.labs.lang.SlowClassLoader.delay}")
	private String slowClassLoaderDelay;

	// -Dspring.beaninfo.ignore
	@Value("${com.techtrip.labs.lang.SlowClassLoader.runsilent}")
	private String slowClassLoaderRunsilent;

	/*
	 * @Bean public DataSource dataSource() {
	 * org.apache.commons.dbcp.BasicDataSource dataSource = new
	 * org.apache.commons.dbcp.BasicDataSource();
	 * dataSource.setUrl(AppConfig.DB_URL);
	 * dataSource.setDriverClassName(AppConfig.DB_DRIVER_CLASS_NAME);
	 * dataSource.setUsername(AppConfig.DB_USERNAME);
	 * dataSource.setPassword(AppConfig.DB_PASSWORD); return dataSource; }
	 */
	@Bean
	@Qualifier("springBeaninfo")
	public String springBeaninfo() {
		if (springBeaninfoIgnore == null | springBeaninfoIgnore.isEmpty()) {
			System.out.println("spring.beaninfo.ignore=false");
			return "false";
		}

		System.out.println("spring.beaninfo.ignore=" + springBeaninfoIgnore);
		return springBeaninfoIgnore;
	}

	@Bean
	@Qualifier("slowClassLoaderDelay")
	public String slowClassLoaderDelay() {
		if (slowClassLoaderDelay == null | slowClassLoaderDelay.isEmpty()) {
			return "SlowClassLoader - Class loading Delay is set to: N/A";
		}

		return "SlowClassLoader - Class loading Delay is set to: "
				+ slowClassLoaderDelay + "ms";
	}

	@Bean
	@Qualifier("slowClassLoaderRunsilent")
	public String slowClassLoaderRunsilent() {
		if (slowClassLoaderRunsilent == null
				| slowClassLoaderRunsilent.isEmpty()) {
			return "SlowClassLoader - Silent Mode is set to: QUIET/TRUE";
		}

		return "SlowClassLoader - Silent Mode is set to: "
				+ slowClassLoaderRunsilent;
	}

	/*
	 * @Bean
	 * 
	 * @Scope("prototype") public TripsDemo tripsDemo() { return new
	 * TripsDemo(); }
	 */
}

/*
* © Copyright, Terry Trippany, TechTrip, 2014.
* 
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

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.techtrip.dynbl.consts.WebMvcConsts;

/**
 * The Class WebMvcConfig.
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	/** The Constant VIEW_RESOLVER_PREFIX. */
	public static final String VIEW_RESOLVER_PREFIX = "/views/";
	
	/** The Constant VIEW_RESOLVER_SUFFIX. */
	private static final String VIEW_RESOLVER_SUFFIX = ".jsp";
	
	/**
	 * Configure message source.
	 *
	 * @return the message source
	 */
	@Bean(name = "messageSource")
	public MessageSource configureMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(WebMvcConsts.MESSAGE_SOURCE.getValue());
		messageSource.setCacheSeconds(5);
		return messageSource;
	}
	
    /* 
     * TMT - Resolves views selected for rendering by @Controllers to .jsp resources in the @Code{/WEB-INF/pages} directory
     * Equivalent to the following XML Style equivalent:
     * 
     * <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
     *  <property name="prefix" value="/pages/" />
     *  <property name="suffix" value=".jsp" />
     * </bean>
	 * 
	 */
	 /**
     * Configure internal resource view resolver.
     *
     * @return the internal resource view resolver
     */
    @Bean(name = "viewResolver")
	 public InternalResourceViewResolver configureInternalResourceViewResolver() {
	  InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	  resolver.setPrefix(VIEW_RESOLVER_PREFIX);
	  resolver.setSuffix(VIEW_RESOLVER_SUFFIX);
	  return resolver;
	 }	
	
	/*
	 * TMT - This is the equivalent of @Code{<mvc:resources mapping="/resources/**" location="/resources/" />} 
	 * 
	 * Handles HTTP GET requests for resources by efficiently serving up static resources in the @Code{${webappRoot}/resources} directory
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(WebMvcConsts.RESOURCES_LOCATION.getValue()).addResourceLocations(WebMvcConsts.RESOURCES_MAPPING.getValue());
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureDefaultServletHandling(org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer)
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
/*    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // add message converters...
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setWriteAcceptCharset(false);

		converters.add(new ByteArrayHttpMessageConverter());
		converters.add(stringConverter);
		converters.add(new ResourceHttpMessageConverter());
		converters.add(new SourceHttpMessageConverter<Source>());
    	converters.add(new GsonHttpMessageConverter());
    }*/
}
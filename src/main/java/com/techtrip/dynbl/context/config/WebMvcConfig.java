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

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	public static final String VIEW_RESOLVER_PREFIX = "/views/";
	private static final String VIEW_RESOLVER_SUFFIX = ".jsp";
	
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
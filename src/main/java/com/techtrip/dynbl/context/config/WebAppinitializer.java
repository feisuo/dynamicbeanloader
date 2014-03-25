package com.techtrip.dynbl.context.config;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.SpringVersion;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;

public class WebAppinitializer implements WebApplicationInitializer {

	private static final String ROOT_CONTEXT = "/";
	private static final String APP_SERVLET = "appServlet";
	private static final String APP_CONFIG_LOCATION = "com.techtrip.dynbl.context.config";
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		// Setup Context to Accept Annotated Classes on Input (including plain Spring {@code @Component}
		// Stereotypes in addition to JSR-330 Compliant Classes using {@code javax.inject}
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		
		//context.setConfigLocation(APP_CONFIG_LOCATION);
		context.setConfigLocation(APP_CONFIG_LOCATION);
		
		/* 
		 * Add a Spring Security Filter using the JEE6 Filter Registration Filter Method from {@code FilterRegistration) that allows filters to be registered
		 *  and configured with the specified context
		 */
/*		
		FilterRegistration.Dynamic securityFilter = servletContext.addFilter(ProjectKeyValConsts.SECURITY_FILTER.getKey(), new DelegatingFilterProxy(ProjectKeyValConsts.SECURITY_FILTER.getValue()));
		securityFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, ProjectConsts.BASE_URL_MAPPING_PATTERN.getValue()); // where the filter will be applied
*/		
		// Add a Character Encoding Filter that specifies an encoding for mapped requests
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, ROOT_CONTEXT); // where the filter will be applied
		characterEncodingFilter.setInitParameter("encoding",  "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", Boolean.TRUE.toString());
		characterEncodingFilter.setAsyncSupported(true);
		
		servletContext.addListener(new ContextLoaderListener(context));
		servletContext.setInitParameter("defaultHtmlEscape", Boolean.TRUE.toString());
		
		DispatcherServlet servlet = new DispatcherServlet();
		
		// no explicit configuration reference here: everything is configured in the root container for simplicity
		servlet.setContextConfigLocation("");
		
		/* TMT From JEE 6 API Docs:
		 * Registers the given servlet instance with this ServletContext under the given servletName.
		 * The registered servlet may be further configured via the returned ServletRegistration object. 
		 */

		ServletRegistration.Dynamic appServlet = servletContext.addServlet(APP_SERVLET, servlet);
		appServlet.setLoadOnStartup(1);
		appServlet.setAsyncSupported(true);
		
		Set<String> mappingConflicts = appServlet.addMapping("/");
		
		if (!mappingConflicts.isEmpty()) {
			throw new IllegalStateException(String.format("The servlet named '%s' cannot be mapped to '/' under Tomcat versions <= 7.0.14", APP_SERVLET));
		}		
		
		// TMT
		servletContext.addListener(new Log4jConfigListener());
		
		System.out.println("Application inplemented on Spring Version: " + SpringVersion.getVersion());

	}

}

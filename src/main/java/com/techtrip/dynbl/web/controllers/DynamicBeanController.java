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
package com.techtrip.dynbl.web.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techtrip.spring.beans.factory.ContextAwareBeanFactory;

/**
 * The Class DynamicBeanController.
 */
@Controller
public class DynamicBeanController implements ApplicationContextAware,
		InitializingBean {

	private static Logger LOGGER = LoggerFactory
			.getLogger(DynamicBeanController.class);

	@Autowired(required = true)
	private ContextAwareBeanFactory beanFactory;

	@Autowired(required = true)
	private String slowClassLoaderDelay;

	@Autowired(required = true)
	private String slowClassLoaderRunsilent;

	@Autowired(required = true)
	private String springBeaninfo;

	private ApplicationContext applicationContext;

	private static final String PAGE_HEADER;
	private static final String SPRING_VERSION;
	private static final String BEAN_INFO_HEADER;
	private static final String BEAN_INFO_FORMAT = "Bean Name: %s<br/>Bean Type: %s<br/>Bean ToString() Message: %s";
	// private static final String DEMO_MESSAGE_HEADER;

	private static String BEAN_INFO_MSG;
	private static String CLASSLOADER_DELAY;
	private static String SYS_INFO;

	static {
		PAGE_HEADER = tagHTML("h1", "Bean Registration info!");
		SPRING_VERSION = tagHTML("h2", String.format(
				"Application inplemented on Spring Version: %s",
				SpringVersion.getVersion()));
		BEAN_INFO_HEADER = tagHTML("h2",
				"The following bean was registered and instantiated:");
		/*
		 * DEMO_MESSAGE_HEADER =
		 * tagHTML("h2","The Following Demo Message Was Generated:");
		 */
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		BEAN_INFO_MSG = tagHTML("h2",
				String.format("spring.beaninfo.ignore=%s", springBeaninfo));

		CLASSLOADER_DELAY = tagHTML("h2", slowClassLoaderDelay);

		SYS_INFO = SPRING_VERSION + CLASSLOADER_DELAY + BEAN_INFO_MSG;

	}

	/*
	 * @Autowired(required = true) private TripsDemo tripsDemo;
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String home() {
		return "You are home!!";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/sysInfo")
	@ResponseBody
	private String sysInfo() {
		DateFormat df = new SimpleDateFormat(
				"EEE, d MMM yyyy 'at' HH:mm:ss aaa");

		String[] beanDefNames = applicationContext.getBeanDefinitionNames();

		StringBuilder bldr = new StringBuilder(
				tagHTML("h2",
						String.format(
								"There are %d beans registered in the ApplicationContext Named %s",
								beanDefNames.length,
								applicationContext.getDisplayName())));
		bldr.append(tagHTML("h2",
				"The following beans are registered in this context as named:"));
		bldr.append("<hr>");
		int i = 0;
		for (String beanName : beanDefNames) {
			if ((i++ % 2) == 0){
				bldr.append(tagHTML("p", beanName, "text-indent:50px;font-weight:bold;font-size:110%;line-height:70%"));
			} else {
				bldr.append(tagHTML("p", beanName, "text-indent:50px;font-weight:bold;font-size:110%;color:rgb(255,0,0);line-height:70%"));
			}
		}

		return SYS_INFO
				+ tagHTML(
						"h3",
						String.format("Current time: %s",
								df.format(Calendar.getInstance().getTime())))
				+ bldr.toString();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registerBean")
	@ResponseBody
	private String registerBean(
			@RequestParam(value = "beanName", required = true) String beanName,
			@RequestParam(value = "beanType", required = true) String beanType) {
		try {

			/* String demoMessage = getDemoMessage(); */

			Class<?> c;

			c = Class.forName(beanType);

			CachedIntrospectionResults.clearClassLoader(c.getClassLoader());

			beanFactory.registerBean(c, beanName, "prototype", false, true);

			Object myBean = beanFactory.getBean(beanName);

			String ret = PAGE_HEADER
					+ SPRING_VERSION
					+ BEAN_INFO_MSG
					+ CLASSLOADER_DELAY
					+ BEAN_INFO_HEADER
					+ String.format(BEAN_INFO_FORMAT, beanName, myBean
							.getClass().getName(), myBean.toString()) /*
																	 * +
																	 * DEMO_MESSAGE_HEADER
																	 * +
																	 * tagHTML(
																	 * "p",
																	 * demoMessage
																	 * )
																	 */;

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(beanType);
			}

			/* CachedIntrospectionResults cir; */
			/*
			 * 
			 * if (ClassUtils.isCacheSafe(beanClass,
			 * CachedIntrospectionResults.class.getClassLoader()) ||
			 * isClassLoaderAccepted(beanClass.getClassLoader())) {
			 */
			// CachedIntrospectionResults.clearClassLoader(c.getClassLoader());

			/* tripsDemo.getMessage(); */

			return ret;
		} catch (Exception e) {
			return String
					.format("Unable to create bean of type %s with the following error:</br>%s!",
							beanType, e.getMessage());
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	/*
	 * private String getDemoMessage() {
	 * 
	 * String messageOut = null; try { TripsDemo tripsDemo =
	 * this.applicationContext.getBean(TripsDemo.class);
	 * 
	 * messageOut =
	 * String.format("Trips Demo Message : %s, inner bean date: %s",
	 * tripsDemo.getMessage(),
	 * tripsDemo.getInnerBean().getSomeDate().toString());
	 * LOGGER.info(messageOut); } catch (NoClassDefFoundError ex) { messageOut =
	 * String.format(
	 * "An Error Occurred Instantiating Trip's Demo Bean: NoClassDefFoundError --> %s"
	 * , ex.getMessage()); }
	 * 
	 * return (messageOut); }
	 */

	private static String tagHTML(String tag, String message, String style) {
		if (style == null) {
			return String.format("<%s>%s</%s>", tag, message, tag);
		} else {
			return String.format("<%s style=\"%s\">%s</%s>", tag, style, message, tag);
		}
	}

	private static String tagHTML(String tag, String message) {
		return tagHTML(tag, message, null);
	}
}

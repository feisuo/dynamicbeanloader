package com.techtrip.dynbl.web.controllers;

import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techtrip.spring.beans.factory.ContextAwareBeanFactory;

@Controller
public class DynamicBeanController {

	@Autowired(required = true)
	private ContextAwareBeanFactory beanFactory;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String home() {
		return "You are home!!";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/registerBean")
	@ResponseBody
	private String registerBean(@RequestParam(value = "beanName", required = true) String beanName,@RequestParam(value = "beanType", required = true) String beanType) {
		try {
			Class<?> c;

			c = Class.forName(beanType);

			CachedIntrospectionResults.clearClassLoader(c.getClassLoader());
			
			beanFactory.registerBean(c, beanName, "prototype", false, true);
			
			/*
			 * 
			 * 			if (ClassUtils.isCacheSafe(beanClass, CachedIntrospectionResults.class.getClassLoader()) ||
					isClassLoaderAccepted(beanClass.getClassLoader())) {
			 */
			//CachedIntrospectionResults.clearClassLoader(c.getClassLoader());
			
			return String.format("The following bean was registered and instantiated: Bean Name:  %s, Bean Type: %s",beanName, beanFactory.getBean(beanName).toString());
		} catch (Exception e) {
			return String
					.format("Unable to create bean of type %s with the following error:</br>%s!",
							beanType, e.getMessage());
		}
	}
}

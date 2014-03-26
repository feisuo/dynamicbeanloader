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
package com.techtrip.spring.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * A factory for creating ContextAwareBean objects.
 */
@Component
public class ContextAwareBeanFactory implements ApplicationContextAware,
		BeanFactoryPostProcessor {

	/** The app context. */
	private ApplicationContext appContext;
	
	/** The factory. */
	private ConfigurableListableBeanFactory factory;

	/** The registry. */
	private BeanDefinitionRegistry registry;

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 */
	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		this.factory = beanFactory;

		registry = ((BeanDefinitionRegistry) factory);
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.appContext = applicationContext;
	}

	/**
	 * Gets the app context.
	 *
	 * @return the app context
	 */
	public ApplicationContext getAppContext() {
		return appContext;
	}

	/**
	 * Gets the factory.
	 *
	 * @return the factory
	 */
	public ConfigurableListableBeanFactory getFactory() {
		return factory;
	}

	/**
	 * Register bean.
	 *
	 * @param beanToRegister the bean to register
	 * @param beanName the bean name
	 * @param scope the scope
	 * @param setLazyInit the set lazy init
	 * @param setAutowireCandidate the set autowire candidate
	 */
	public void registerBean(Class<?> beanToRegister, String beanName,
			String scope /* "session" */, boolean setLazyInit,
			boolean setAutowireCandidate) {
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(beanToRegister);
		beanDefinition.setLazyInit(setLazyInit);
		beanDefinition.setAbstract(false);
		beanDefinition.setAutowireCandidate(setAutowireCandidate);
		beanDefinition.setScope(scope);

		registry.registerBeanDefinition(beanName, beanDefinition);
	}

	/**
	 * Gets the bean.
	 *
	 * @param beanName the bean name
	 * @return the bean
	 */
	public Object getBean(String beanName) {
		Object bean = this.appContext.getBean(beanName);

		return bean;
	}
}

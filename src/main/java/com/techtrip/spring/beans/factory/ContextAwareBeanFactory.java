package com.techtrip.spring.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextAwareBeanFactory implements ApplicationContextAware,
		BeanFactoryPostProcessor {

	private ApplicationContext appContext;
	private ConfigurableListableBeanFactory factory;

	private BeanDefinitionRegistry registry;

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		this.factory = beanFactory;

		registry = ((BeanDefinitionRegistry) factory);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.appContext = applicationContext;
	}

	public ApplicationContext getAppContext() {
		return appContext;
	}

	public ConfigurableListableBeanFactory getFactory() {
		return factory;
	}

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

	public Object getBean(String beanName) {
		Object bean = this.appContext.getBean(beanName);

		return bean;
	}
}

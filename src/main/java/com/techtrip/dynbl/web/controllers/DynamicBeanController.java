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
package com.techtrip.dynbl.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DynamicBeanController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(DynamicBeanController.class);
    
    /** The bean factory. */
    @Autowired(required = true)
    private ContextAwareBeanFactory beanFactory;
    
    /**
     * Home.
     *
     * @return the string
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String home() {
            return "You are home!!";
    }
    
    /**
     * Register bean.
     *
     * @param beanName the bean name
     * @param beanType the bean type
     * @return the string
     */
    @RequestMapping(method = RequestMethod.GET, value = "/registerBean")
    @ResponseBody
    private String registerBean(@RequestParam(value = "beanName", required = true) String beanName,@RequestParam(value = "beanType", required = true) String beanType) {
            try {
                    Class<?> c;

                    c = Class.forName(beanType);

                    CachedIntrospectionResults.clearClassLoader(c.getClassLoader());
                    
                    beanFactory.registerBean(c, beanName, "prototype", false, true);
                    
                    
                    String ret = String.format("The following bean was registered and instantiated: Bean Name:  %s, Bean Type: %s",beanName, beanFactory.getBean(beanName).toString());
                    		
                    if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug(beanType);
                    }
                    
                    /*
                    * 
                    *                       if (ClassUtils.isCacheSafe(beanClass, CachedIntrospectionResults.class.getClassLoader()) ||
                                    isClassLoaderAccepted(beanClass.getClassLoader())) {
                    */
                    //CachedIntrospectionResults.clearClassLoader(c.getClassLoader());
                    
                    
                    
                    return ret;
            } catch (Exception e) {
                    return String
                                    .format("Unable to create bean of type %s with the following error:</br>%s!",
                                                    beanType, e.getMessage());
            }
    }
}

/*
 * Copyright (c) 2012.  Mondora srl, Mondora suisse
 * and individual contributors as indicated by the @authors tag.
 * See the copyright.txt in the distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * software; if not see the FSF site: http://www.fsf.org.
 */

package com.mondora.chargify;

import com.mondora.chargify.controller.ChargifyAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 9:02 PM
 */
public class ChargifyFactory {
    static BeanFactory factory = null;
    static ChargifyFactory singleton;

    public static ChargifyFactory getInstance() {
        if (singleton == null) singleton = new ChargifyFactory();
        return singleton;
    }

    private static Logger logger = LoggerFactory.getLogger("ChargifyFactory");

    public ChargifyAdapter getChargify() {
        return getChargify("charge");
    }

    public ChargifyAdapter getChargify(String beanId) {
        return (ChargifyAdapter) getFactory().getBean(beanId, ChargifyAdapter.class);
    }

    public ChargifyService getSenseChargify(String mock) {
        return (ChargifyService) getFactory().getBean(mock, ChargifyService.class);
    }

    public BeanFactory getFactory() {
        if (factory == null) {
            try {
                factory = readFromSenseDirConf();
            } catch (FileNotFoundException e) {
                if (logger.isDebugEnabled()) logger.debug(e.getMessage());
                factory = defaultFactory();
            }
        }
        return factory;
    }

    BeanFactory defaultFactory() {
        logger.info("Using default charge-context.xml from classpath.");
        return new XmlBeanFactory(new ClassPathResource("charge-context.xml"));
    }

    BeanFactory readFromSenseDirConf() throws FileNotFoundException {
        String fPath = System.getProperty("sense.dir.conf") + "/charge-context.xml";
        if (logger.isTraceEnabled()) logger.trace("Try loading charge-context.xml from " + fPath);

        File chargeContext = new File(fPath);
        if (chargeContext.exists()) {
            return new XmlBeanFactory(new FileSystemResource(chargeContext));
        }
        throw new FileNotFoundException("file " + fPath + " don't exists");
    }
}
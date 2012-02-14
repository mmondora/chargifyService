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

package com.mondora.chargify.domain;

import com.mondora.chargify.controller.ChargifyAdapter;
import com.mondora.chargify.mock.ChargifyProductListMock;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 7:29 AM
 */
public class ComponentsTest extends TestCase {
    Logger logger = LoggerFactory.getLogger(ComponentsTest.class);

    public void testCompatibility() throws Exception {
        ChargifyAdapter c = new ChargifyProductListMock();

        Subscription fakeSubscription = new Subscription();
        Component comp = c.listComponents(fakeSubscription).find("mockdomain-amazon-ec2-freemium-t1.micro-billcapping");
        assertEquals(4454, comp.getId().intValue());

        Component comp2 = c.listComponents("").find("mockdomain-amazon-ec2-freemium-t1.micro-billcapping");
        assertEquals(comp.getId().intValue(), comp2.getId().intValue());

        logger.trace(String.valueOf(comp));
        logger.trace(String.valueOf(comp2));
    }

    public void testFindByName() throws Exception {
        ChargifyAdapter c = new ChargifyProductListMock();
        Subscription fakeSubscription = new Subscription();
        Component comp = c.listComponents(fakeSubscription).find("mockdomain-amazon-ec2-freemium-t1.micro-billcapping");
        logger.trace(String.valueOf(comp));
        assertNotNull(comp);
    }

    public void testFindById() throws Exception {
        ChargifyAdapter c = new ChargifyProductListMock();
        Component comp = c.listComponents("a fake family").find("id", 4290);
        logger.trace(String.valueOf(comp));
        assertNotNull(comp);
    }

    public void testFindByError() throws Exception {
        ChargifyAdapter c = new ChargifyProductListMock();
        Component comp = c.listComponents("a fake family").find("a-not-existant-field", 4290);
        logger.trace(String.valueOf(comp));
        assertNull(comp);
    }
}

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

import com.mondora.chargify.domain.Subscription;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 9:04 AM
 *
 * this works only if exists the subscription on the ChargifyAdapter you are going to use
 * if test goes with subscription.id = 579554
 *
 * if integrated test with ChargifyAdapter please create your subscription and configure the
 * subscription.id = xxxx according to chargify
 *
 */
public class ChargifyServiceTest extends TestCase {
    Logger logger = LoggerFactory.getLogger(ChargifyServiceTest.class);

    class ChargifyTestMock extends com.mondora.chargify.controller.ChargifyTest {
        @Override
        public void setUp() throws Exception {
            super.setUp();   
        }
        
        public Subscription getSubscription() {
            return this.integrationSubscription;
        }
    }
    public Subscription getSubscription() throws Exception {
        
        ChargifyTestMock ct = new ChargifyTestMock();
        ct.setUp();
        
        Subscription subscription = ct.getSubscription();
        return subscription;
    }

    public void testCreateMeteredUsageFor1Hour() throws Exception {
        ChargifyService chargifyService = createChargify();

        Subscription subscription = getSubscription();

        ChargeResult chargeResult = chargifyService.createMeteredUsageFor1Hour(String.valueOf(subscription.id), "freemium", "amazon", "ec2","t1.micro","billCapping", "unit testing");
        logger.trace(String.valueOf(chargeResult));

        assertNotNull(chargeResult);
        assertEquals(ChargeResult.OK, chargeResult.resultCode);
    }

    public void testCreateMeteredUsageFor1HourWithCacheHit() throws Exception {
        ChargifyService chargifyService = createChargify();
        chargifyService.componentCache.clear();

        Subscription subscription = getSubscription();
        assertEquals(0, chargifyService.componentCache.size());

        ChargeResult chargeResult = chargifyService.createMeteredUsageFor1Hour(String.valueOf(subscription.id), "freemium", "amazon", "ec2","t1.micro","billCapping", "unit testing");
        assertEquals(1, chargifyService.componentCache.size());

        chargeResult = chargifyService.createMeteredUsageFor1Hour(String.valueOf(subscription.id), "freemium", "amazon", "ec2","t1.micro","billCapping", "unit testing");
        assertEquals(1, chargifyService.componentCache.size());

        logger.trace(String.valueOf(chargifyService.componentCache));
    }

    public void testCreateMeteredUsageFor1HourFailing() throws Exception {
        ChargifyService chargifyService = createFailureSenseChargify();

        Subscription subscription = getSubscription();

        ChargeResult chargeResult = chargifyService.createMeteredUsageFor1Hour(String.valueOf(subscription.id), "freemium", "amazon", "ec2","t1.micro","billCapping", "unit testing");
        logger.trace(String.valueOf(chargeResult));

        assertNotNull(chargeResult);
        assertEquals(ChargeResult.ERR, chargeResult.resultCode);
    }

    public void testCreatingFromSubscription() throws Exception {
//        ChargifyAdapter c = new ChargifySubscriptionListMock();
        Subscription subscription = getSubscription();
        assertNotNull(subscription);
        assertNotNull(subscription.product);
        assertNotNull(subscription.product.name);

        ChargifyService chargifyService = createChargify();
        ChargeResult chargeResult = chargifyService.createMeteredUsageFor1Hour(subscription, "amazon", "ec2","t1.micro","billCapping", "unit testing");
        logger.trace(String.valueOf(chargeResult));

        assertNotNull(chargeResult);
        assertEquals(ChargeResult.OK, chargeResult.resultCode);

    }

    public void testNameCreation() {
        ChargifyService chargifyService = ChargifyFactory.getInstance().getSenseChargify("chargifyService");
        String name = chargifyService.createComponentName("freemium", "amazon", "ec2","t1.micro","billCapping");
        String domain = chargifyService.getChargify().getDomain();

        assertEquals(domain + "-amazon-ec2-freemium-t1.micro-billcapping", name);
    }

    ChargifyService createChargify() {
        return ChargifyFactory.getInstance().getSenseChargify("chargifyService");
    }

    ChargifyService createFailureSenseChargify() {
        return ChargifyFactory.getInstance().getSenseChargify("failing");
    }
}

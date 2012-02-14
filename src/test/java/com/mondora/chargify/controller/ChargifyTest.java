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

package com.mondora.chargify.controller;

import com.mondora.chargify.ChargifyFactory;
import com.mondora.chargify.domain.*;
import com.mondora.chargify.exception.ChargifyException;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Properties;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/24/11 6:04 PM
 * <p/>
 * this works only if online and chargify is configured. It's an "integration test".
 */
public class ChargifyTest extends TestCase {
    Logger logger = LoggerFactory.getLogger(ChargifyTest.class);

    Properties properties = new Properties();

    public void testChargify() {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        assertNotNull(c.getClient());
    }

    // to run a full integrated test
    Customer integrationCustomer;
    public Subscription integrationSubscription;
    Component integrationComponent;

    @Override
    protected void setUp() throws Exception {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();

        Customer customer = new Customer();
        customer.reference = "acme-1";
        customer.email = "unittest@acme.com";
        customer.firstName = "firstName.unit-testing";
        customer.lastName = "lastName.unit-testing";
        try {
             integrationCustomer = c.getCustomerByReference("acme-1");
        } catch (ChargifyException ce ){
            integrationCustomer = c.create(customer);
        }
        
        Product integrationProduct = c.getProductByHandle("unittest");

        integrationSubscription = c.createSubscription( integrationProduct.handle, integrationCustomer.id );
        integrationComponent = c.listComponents( integrationProduct.product_family.id ).items.get(0);

        System.out.println("setUp: Customer " + integrationCustomer);
        System.out.println("setUp: Subscription " + integrationSubscription );
        System.out.println("setUp: Product " + integrationProduct );
        System.out.println("setUp: Component " + integrationComponent );

    }

    @Override
    protected void tearDown() throws Exception {
        // should I remove subscription ?
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        if( integrationSubscription != null ) {
            c.deleteSubscription( integrationSubscription.id, "tear down of unit test");
        }
    }

    public void testInsertingMetered() throws ChargifyException {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        for (int i = 0; i < 1; i++) {
            MeteredUsage res = c.componentCreateUsage(integrationSubscription, integrationComponent, 1, "pureJAVA inserting from API " + i + " " + new Date().toString());
            logger.trace(String.valueOf(res));
        }
    }

    public void testGetCustomerByReference() throws Exception {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        Customer customer = c.getCustomerByReference("acme-1");
        assertNotNull(customer);
        logger.trace(String.valueOf(customer));
    }

    public void testListSubscriptions() throws Exception {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        Customer customer = c.getCustomerByReference("acme-1");
        Subscriptions subscriptions = c.listSubscriptions(customer);
        assertNotNull(subscriptions);
        logger.trace(String.valueOf(subscriptions));
    }

    public void testGetSubscription() throws Exception {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        Subscription subscription = c.getSubscription(integrationSubscription.id);
        logger.trace(String.valueOf(subscription));

        assertNotNull(subscription);
    }

    public void testListComponents() throws Exception {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        Components components = c.listComponents("10064");
        assertNotNull(components);
        logger.trace(String.valueOf(components));
    }

    public void testListComponentsInSubscription() throws Exception {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        Customer customer = c.getCustomerByReference("acme-1");
        Subscriptions subscriptions = c.listSubscriptions(customer);
        Subscription subscription = subscriptions.items.get(0);

        Components components = c.listComponents(subscription);
        assertNotNull(components);
        logger.trace(String.valueOf(components));
    }

    public void testListProducts() throws Exception {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        Products products = c.listProducts();
        assertNotNull(products);
        logger.trace(String.valueOf(products));
    }

    public void testInsertComponenent() throws Exception {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        Customer customer = c.getCustomerByReference("acme-1");

        Subscriptions subscriptions = c.listSubscriptions(customer);
        Subscription subscription = subscriptions.items.get(0);

        Components components = c.listComponents(subscription);
        Component component = components.items.get(0);

        logger.trace(String.valueOf(customer));
        logger.trace(String.valueOf(subscription));
        logger.trace(String.valueOf(component));

        for (int i = 0; i < 5; i++) {
            MeteredUsage meteredUsage = c.componentCreateUsage(subscription, component, 1, "Hei inserting from API " + i + " " + new Date().toString());
            logger.trace(String.valueOf(meteredUsage));
            Thread.sleep(1000);
        }

    }
    
    public Customer testCreateCustomer() throws Exception {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        Customer customer = new Customer();
        customer.email = "breaking@bad.com";
        customer.firstName = "Walter";
        customer.lastName = "White";
        customer.reference = String.valueOf( "UnitTest@"+ System.currentTimeMillis() );
        customer.organization = "Empty ACME";

        Customer created = c.create( customer );
        logger.trace(String.valueOf(customer));
        assertNotNull( created );

        assertEquals( customer.reference, created.reference );
        assertEquals( customer.email, created.email);
        assertEquals( customer.firstName, created.firstName);
        assertEquals( customer.lastName, created.lastName);
        assertEquals( customer.organization, created.organization);
        assertNotNull(created.id );
        
        return created;
    }

    /**
     * the Product "UnitTest" must exists on the beta site, else it fails!
     * and at "all are read only at this time" @see http://docs.chargify.com/api-products
     * 
     * @throws Exception
     */
    public void testCreateSubscription() throws Exception {
        
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();
        
        // first create the customer
        Customer customer = testCreateCustomer();
        assertNotNull( customer );
        System.out.println("Customer " + customer );

        Product product = c.getProductByHandle("unittest");
        assertNotNull( product );
        System.out.println("product " + product);

        Subscription sub = c.createSubscription( product.handle, customer.id );
        System.out.println("Sub: " + sub );
        assertNotNull( sub );
    }

    public void testCreateCustomerFailing() throws Exception {
        ChargifyAdapter c = ChargifyFactory.getInstance().getChargify();

        try {
            Customer created = c.buildACustomer( null, "org", "first", "last", "email" );
            logger.trace(String.valueOf(created));
            fail("null or emtpy reference must make it fail");
        }
        catch ( ChargifyException ce ) {
            // OK
        }

        try {
            Customer created = c.buildACustomer( "aaa", "org", "first", "last", "" );
            logger.trace(String.valueOf(created));
            fail("null or empty email must make it fail");
        }
        catch ( ChargifyException ce ) {
            // OK
        }
    }
}

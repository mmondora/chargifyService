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
import com.mondora.chargify.domain.*;
import com.mondora.chargify.exception.ChargifyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 7:47 AM
 */
public class ChargifyService {
    static Logger logger = LoggerFactory.getLogger(ChargifyService.class);
    Map<String, Component> componentCache = new HashMap<String, Component>();
    ChargifyAdapter chargify = null;

    /**
     * create a customer and a subscription for the customer.
     * if it works return a chargifyresult with the subscription as a result.
     *
     * @param email
     * @param firstName
     * @param lastName
     * @param org
     * @param productHandle
     * @return aChargifyResult with result as a Subscription
     */
    public ChargeResult createSubscription(String email, String firstName, String lastName, String org, String productHandle) {
        ChargeResult result = new ChargeResult();
        try {
            Customer customer = chargify.buildACustomer(email, org, firstName, lastName, email);
            customer = chargify.create(customer);
            result.resultCode = ChargeResult.OK;
            result.message = "Created " + shorten(customer);

            System.out.println("chargify: " + chargify );
            System.out.println("customer: " + customer );
            Subscription subscription = chargify.createSubscription(productHandle, customer.id);
            result.message += ", Created subscription " + shorten(subscription);

            result.result = subscription;
            if( logger.isInfoEnabled() ) {
                logger.info( "createSubscription for " + result.message );
            }
        } catch (ChargifyException e) {
            if (logger.isDebugEnabled()) logger.debug(e.getMessage(), e);
            if (logger.isInfoEnabled()) logger.info(e.getMessage());
            result.resultCode = ChargeResult.ERR;
            result.message = "Cannot create subscription for " + email + " on " + productHandle + " " + e.getMessage();
            result.result = e;
        }
        return result;
    }

    private String shorten(Customer customer) {
        if( customer == null ) return "Customer{null}";
        return "Customer{" +
                "id=" + customer.id +
                ", email='" + customer.email + '\'' +
                ", organization='" + customer.organization + '\'' +
                '}';
    }

    private String shorten(Subscription subscription) {
        if( subscription == null ) return "Subscription{null}";
        return "Subscription{" +
                "id=" + subscription.id +
                ", product='" + subscription.product.handle + '\'' +
                ", state='" + subscription.state + '\'' +
                ", expire='" + subscription.expires_at + '\'' +
                '}';
    }

    public ChargeResult createMeteredUsageFor1Hour(Subscription subscription, String provider, String type, String instanceType, String offer, String description) {
        ChargeResult result = new ChargeResult();

        if (subscription == null) {
            result.resultCode = ChargeResult.ERR;
            result.message = "Subscription is null";
            return result;
        }

        if (subscription.id == null) {
            result.resultCode = ChargeResult.ERR;
            result.message = "Subscription id is null";
            return result;
        }

        if (subscription.product == null) {
            result.resultCode = ChargeResult.ERR;
            result.message = "Subscription product is null";
            return result;
        }

        if (subscription.product.name == null) {
            result.resultCode = ChargeResult.ERR;
            result.message = "Subscription product name is null";
            return result;
        }

        String subscriptionId = String.valueOf(subscription.id);
        String subscriptionType = subscription.product.name;
        return createMeteredUsageFor1Hour(subscriptionId, subscriptionType, provider, type, instanceType, offer, description);
    }

    /**
     * charge 1 hour of usage for a component on ChargifyAdapter
     *
     * @param subscriptionId the subscription id the customer sign up
     * @param productName,   freemium, premium, sandbox
     * @param provider       the provider name, the feeling type ie. amazon
     * @param type           the provider system/subsystem we are monitoring ie. ec2
     * @param instanceType   type of instance(t1.micro)
     * @param offer          control app type(billCapping,watchman...)
     * @param description    the memo appears on the invoice raw
     * @return a ChargeResult that contains ERR or OK
     */
    public ChargeResult createMeteredUsageFor1Hour(String subscriptionId, String productName, String provider, String type, String instanceType, String offer, String description) {
        ChargeResult result = new ChargeResult();
        try {
            String componentName = createComponentName(productName, provider, type, instanceType, offer);
            Component component = componentCache.get(componentName);
            if (component == null)
                try {
                    component = loadComponent(subscriptionId, componentName);
                } catch (ChargifyException chargifyException) {
                    result.resultCode = ChargeResult.ERR;
                    result.message = "Component " + componentName + " error! " + chargifyException.getMessage();
                    if (logger.isTraceEnabled()) logger.trace(result.message, chargifyException);
                    return result;
                }
            if (component != null) {
                result = componentCreateUsage(subscriptionId, component.getId(), 1, description);
                componentCache.put(componentName, component);
            } else {
                result.resultCode = ChargeResult.ERR;
                result.message = "Component " + componentName + " not found!";
            }
        } catch (Exception ex) {
            logger.trace(ex.getMessage(), ex);
            result.resultCode = ChargeResult.ERR;
            result.message = ex.getMessage();
        }
        return result;
    }

    /**
     * produce the chargify product/component name from sense parameteres
     *
     * @param productName  ( freemiun, premium, type )
     * @param provider     ( amazon, azure ... the feeling name/type )
     * @param type         ( the subsystem, ec2 ... )
     * @param instanceType (t1.micro...)
     * @param offer        (billCapping,watchman...)
     * @return
     */
    protected String createComponentName(String productName, String provider, String type, String instanceType, String offer) {
        ChargifyAdapter chargify = getChargify();

        String componentName = chargify.getDomain() + "-" + provider + "-" + type + "-" + productName + "-" + instanceType + "-" + offer;
        return componentName.toLowerCase();
    }

    Component loadComponent(String subscriptionId, String componentName) throws ChargifyException {
        ChargifyAdapter chargify = getChargify();
        Subscription sub = new Subscription();
        try {
            sub.id = Integer.valueOf(subscriptionId);
            Components list = chargify.listComponents(sub);
            if (list != null) return list.find(componentName);
        } catch (NumberFormatException nex) {
            throw new ChargifyException(nex);
        }
        return null;
    }

    ChargeResult componentCreateUsage(String subscriptionId, Integer componentId, Integer quantity, String description) {
        return componentCreateUsage(subscriptionId, String.valueOf(componentId), quantity, description);
    }

    ChargeResult componentCreateUsage(String subscriptionId, String componentId, Integer quantity, String description) {
        ChargifyAdapter chargify = getChargify();
        ChargeResult result = new ChargeResult();
        try {
            MeteredUsage meteredUsage = chargify.componentCreateUsage(subscriptionId, componentId, quantity, description);
            result.resultCode = ChargeResult.OK;
            result.message = meteredUsage.toString();
        } catch (ChargifyException e) {
            if (logger.isDebugEnabled()) logger.debug(e.getMessage(), e);
            if (logger.isInfoEnabled()) logger.info(e.getMessage());
            result.resultCode = ChargeResult.ERR;
            result.message = e.getMessage();
        }
        return result;
    }

    ChargifyAdapter getChargify() {
        if (chargify == null) {
            chargify = ChargifyFactory.getInstance().getChargify();
        }
        return chargify;
    }

    public void setChargify(ChargifyAdapter chargify) {
        this.chargify = chargify;
    }
}

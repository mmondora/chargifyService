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

package com.mondora.chargify.mock;

import com.mondora.chargify.domain.*;
import com.mondora.chargify.exception.ChargifyException;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 8:54 PM
 */
public class ChargifyFullMock extends ChargifyMock {

    private static int prog;
    private static final String SUCCESS = "200";

    @Override
    public Components listComponents(Subscription subscription) throws ChargifyException {
        return (Components) parse(Components.class, new ByteArrayInputStream(componentsXml.getBytes()));
    }

    @Override
    public Components listComponents(String family) throws ChargifyException {
        return (Components) parse(Components.class, new ByteArrayInputStream(familyXml.getBytes()));
    }

    @Override
    public Subscription getSubscription(Integer id) throws ChargifyException {
        return subscriptionMap.get(id);
//        return (Subscription) parse(Subscription.class, new ByteArrayInputStream(subXml.getBytes()));
    }

    @Override
    public MeteredUsage componentCreateUsage(Subscription sub, Component comp, Integer quantity, String memo) throws ChargifyException {
        return (MeteredUsage) parse(MeteredUsage.class, new ByteArrayInputStream(meteredUsageXml.getBytes()));
    }

    @Override
    public MeteredUsage componentCreateUsage(String sub, String comp, Integer quantity, String memo) throws ChargifyException {
        return (MeteredUsage) parse(MeteredUsage.class, new ByteArrayInputStream(meteredUsageXml.getBytes()));
    }

    @Override
    public Customer getCustomerByReference(String reference) throws ChargifyException {
        for( Customer customer: customerMap.values() ) {
            if( reference.equals(customer.reference) ) return customer;
        }
        return (Customer) parse(Customer.class, new ByteArrayInputStream(customer.getBytes()));
    }

    @Override
    public Subscriptions listSubscriptions(Customer customer) throws ChargifyException {
        return (Subscriptions) parse(Subscriptions.class, new ByteArrayInputStream(subscriptionsXml.getBytes()));
    }

    @Override
    public Product getProduct(String id) throws ChargifyException {
        return (Product) parse(Product.class, new ByteArrayInputStream(productXml.getBytes()));
    }

    @Override
    public Product getProductByHandle(String handle) throws ChargifyException {
        return (Product) parse(Product.class, new ByteArrayInputStream(productXml.getBytes()));
    }

    @Override
    public Products listProducts() throws ChargifyException {
        return (Products) parse(Products.class, new ByteArrayInputStream(productXmls.getBytes()));
    }

    @Override
    public Customer create(Customer customer) throws ChargifyException {
        if( customer.id == null ) customer.id = prog++;
        customerMap.put( customer.id, customer );
        return customer;
    }

    @Override
    protected Subscription createSubscription(String xml) throws ChargifyException {
        Subscription sub = (Subscription) parse(Subscription.class, new ByteArrayInputStream(subscriptionXml.getBytes()) );
        if( sub != null ) {
            subscriptionMap.put(sub.id, sub);
        }
        return sub;
    }

    @Override
    public Subscription createSubscription(String productHandle, Integer customerId) throws ChargifyException {
        return createSubscription(subscriptionXml);
    }

    @Override
    public Subscription create(Subscription subscription) throws ChargifyException {
        subscriptionMap.put(subscription.id, subscription);
        return subscription;
    }

    @Override
    public String deleteSubscription(Integer id, String reason) throws ChargifyException {
        subscriptionMap.remove( subscriptionMap.get( id ) );
        return SUCCESS;
    }

    private Map<Integer,Subscription> subscriptionMap = new HashMap<Integer,Subscription>();
    private Map<Integer,Customer> customerMap = new HashMap<Integer,Customer>();

    private String productXmls = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<products type=\"array\">\n" +
            "  <product>\n" +
            "    <accounting_code>freemium</accounting_code>\n" +
            "    <archived_at type=\"datetime\" nil=\"true\"></archived_at>\n" +
            "    <created_at type=\"datetime\">2011-05-23T05:32:06-04:00</created_at>\n" +
            "    <description></description>\n" +
            "    <expiration_interval type=\"integer\">0</expiration_interval>\n" +
            "    <expiration_interval_unit>never</expiration_interval_unit>\n" +
            "    <handle>freemium</handle>\n" +
            "    <id type=\"integer\">38155</id>\n" +
            "    <initial_charge_in_cents type=\"integer\">0</initial_charge_in_cents>\n" +
            "    <interval type=\"integer\">1</interval>\n" +
            "    <interval_unit>month</interval_unit>\n" +
            "    <name>freemium</name>\n" +
            "    <price_in_cents type=\"integer\">0</price_in_cents>\n" +
            "    <request_credit_card type=\"boolean\">false</request_credit_card>\n" +
            "    <require_credit_card type=\"boolean\">false</require_credit_card>\n" +
            "    <return_params>subscription_id={subscription_id}&amp;customer_reference={customer_reference}&amp;product_id={product_id}</return_params>\n" +
            "    <return_url>http://localhost:8080/context/offering/success</return_url>\n" +
            "    <trial_interval type=\"integer\">0</trial_interval>\n" +
            "    <trial_interval_unit>month</trial_interval_unit>\n" +
            "    <trial_price_in_cents type=\"integer\">0</trial_price_in_cents>\n" +
            "    <updated_at type=\"datetime\">2011-05-24T12:00:47-04:00</updated_at>\n" +
            "    <product_family>\n" +
            "      <accounting_code nil=\"true\"></accounting_code>\n" +
            "      <description></description>\n" +
            "      <handle>test-product</handle>\n" +
            "      <id type=\"integer\">10064</id>\n" +
            "      <name>controlmycloud</name>\n" +
            "    </product_family>\n" +
            "  </product>\n" +
            "  <product>\n" +
            "    <accounting_code>premium</accounting_code>\n" +
            "    <archived_at type=\"datetime\" nil=\"true\"></archived_at>\n" +
            "    <created_at type=\"datetime\">2011-05-23T05:32:55-04:00</created_at>\n" +
            "    <description></description>\n" +
            "    <expiration_interval type=\"integer\" nil=\"true\"></expiration_interval>\n" +
            "    <expiration_interval_unit>never</expiration_interval_unit>\n" +
            "    <handle>premium</handle>\n" +
            "    <id type=\"integer\">38156</id>\n" +
            "    <initial_charge_in_cents type=\"integer\" nil=\"true\"></initial_charge_in_cents>\n" +
            "    <interval type=\"integer\">1</interval>\n" +
            "    <interval_unit>month</interval_unit>\n" +
            "    <name>premium</name>\n" +
            "    <price_in_cents type=\"integer\">0</price_in_cents>\n" +
            "    <request_credit_card type=\"boolean\">true</request_credit_card>\n" +
            "    <require_credit_card type=\"boolean\">true</require_credit_card>\n" +
            "    <return_params>subscription_id={subscription_id}&amp;customer_reference={customer_reference}&amp;product_id={product_id}</return_params>\n" +
            "    <return_url>http://localhost:8080/context/offering/success</return_url>\n" +
            "    <trial_interval type=\"integer\" nil=\"true\"></trial_interval>\n" +
            "    <trial_interval_unit>month</trial_interval_unit>\n" +
            "    <trial_price_in_cents type=\"integer\">0</trial_price_in_cents>\n" +
            "    <updated_at type=\"datetime\">2011-05-24T12:00:56-04:00</updated_at>\n" +
            "    <product_family>\n" +
            "      <accounting_code nil=\"true\"></accounting_code>\n" +
            "      <description></description>\n" +
            "      <handle>test-product</handle>\n" +
            "      <id type=\"integer\">10064</id>\n" +
            "      <name>controlmycloud</name>\n" +
            "    </product_family>\n" +
            "  </product>\n" +
            "  <product>\n" +
            "    <accounting_code>sandbox</accounting_code>\n" +
            "    <archived_at type=\"datetime\" nil=\"true\"></archived_at>\n" +
            "    <created_at type=\"datetime\">2011-05-23T05:33:43-04:00</created_at>\n" +
            "    <description></description>\n" +
            "    <expiration_interval type=\"integer\" nil=\"true\"></expiration_interval>\n" +
            "    <expiration_interval_unit>day</expiration_interval_unit>\n" +
            "    <handle>sandbox</handle>\n" +
            "    <id type=\"integer\">38158</id>\n" +
            "    <initial_charge_in_cents type=\"integer\" nil=\"true\"></initial_charge_in_cents>\n" +
            "    <interval type=\"integer\">1</interval>\n" +
            "    <interval_unit>month</interval_unit>\n" +
            "    <name>sandbox</name>\n" +
            "    <price_in_cents type=\"integer\">0</price_in_cents>\n" +
            "    <request_credit_card type=\"boolean\">false</request_credit_card>\n" +
            "    <require_credit_card type=\"boolean\">false</require_credit_card>\n" +
            "    <return_params>subscription_id={subscription_id}&amp;customer_reference={customer_reference}&amp;product_id={product_id}</return_params>\n" +
            "    <return_url>http://localhost:8080/sensesaas/offering/success</return_url>\n" +
            "    <trial_interval type=\"integer\">1</trial_interval>\n" +
            "    <trial_interval_unit>month</trial_interval_unit>\n" +
            "    <trial_price_in_cents type=\"integer\">0</trial_price_in_cents>\n" +
            "    <updated_at type=\"datetime\">2011-05-24T12:01:08-04:00</updated_at>\n" +
            "    <product_family>\n" +
            "      <accounting_code nil=\"true\"></accounting_code>\n" +
            "      <description></description>\n" +
            "      <handle>test-product</handle>\n" +
            "      <id type=\"integer\">10064</id>\n" +
            "      <name>controlmycloud</name>\n" +
            "    </product_family>\n" +
            "  </product>\n" +
            "</products>";

    private String meteredUsageXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<usage>\n" +
            "  <id type=\"integer\">101395</id>\n" +
            "  <memo>Hei inserting from API 0 Wed May 25 20:59:04 CEST 2011</memo>\n" +
            "  <quantity type=\"integer\">1</quantity>\n" +
            "</usage>";

    private String customer = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<customer>\n" +
            "  <address></address>\n" +
            "  <address_2></address_2>\n" +
            "  <city></city>\n" +
            "  <country></country>\n" +
            "  <created_at type=\"datetime\">2011-05-20T06:24:57-04:00</created_at>\n" +
            "  <email>michele.mondora@gmail.com</email>\n" +
            "  <first_name>michele</first_name>\n" +
            "  <id type=\"integer\">587544</id>\n" +
            "  <last_name>mondora</last_name>\n" +
            "  <organization></organization>\n" +
            "  <phone></phone>\n" +
            "  <reference>sense-1</reference>\n" +
            "  <state></state>\n" +
            "  <updated_at type=\"datetime\">2011-05-20T11:53:52-04:00</updated_at>\n" +
            "  <zip></zip>\n" +
            "</customer>";

    private String subscriptionsXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<subscriptions type=\"array\">\n" +
            "  <subscription>\n" +
            "    <activated_at type=\"datetime\" nil=\"true\"></activated_at>\n" +
            "    <balance_in_cents type=\"integer\">0</balance_in_cents>\n" +
            "    <cancel_at_end_of_period type=\"boolean\">false</cancel_at_end_of_period>\n" +
            "    <canceled_at type=\"datetime\" nil=\"true\"></canceled_at>\n" +
            "    <cancellation_message nil=\"true\"></cancellation_message>\n" +
            "    <created_at type=\"datetime\">2011-05-20T10:18:02-04:00</created_at>\n" +
            "    <current_period_ends_at type=\"datetime\">2011-06-20T10:18:02-04:00</current_period_ends_at>\n" +
            "    <expires_at type=\"datetime\" nil=\"true\"></expires_at>\n" +
            "    <id type=\"integer\">579554</id>\n" +
            "    <next_assessment_at type=\"datetime\">2011-06-20T10:18:02-04:00</next_assessment_at>\n" +
            "    <state>trialing</state>\n" +
            "    <trial_ended_at type=\"datetime\">2011-06-20T10:18:02-04:00</trial_ended_at>\n" +
            "    <trial_started_at type=\"datetime\">2011-05-20T10:18:02-04:00</trial_started_at>\n" +
            "    <updated_at type=\"datetime\">2011-05-20T10:18:02-04:00</updated_at>\n" +
            "    <current_period_started_at type=\"datetime\">2011-05-20T10:18:02-04:00</current_period_started_at>\n" +
            "    <previous_state>trialing</previous_state>\n" +
            "    <signup_payment_id type=\"integer\">4767583</signup_payment_id>\n" +
            "    <signup_revenue>0.00</signup_revenue>\n" +
            "    <delayed_cancel_at nil=\"true\"></delayed_cancel_at>\n" +
            "    <credit_card>\n" +
            "      <billing_address>nob</billing_address>\n" +
            "      <billing_address_2>nob</billing_address_2>\n" +
            "      <billing_city>canberra</billing_city>\n" +
            "      <billing_country>AU</billing_country>\n" +
            "      <billing_state>ACT</billing_state>\n" +
            "      <billing_zip>02</billing_zip>\n" +
            "      <card_type>bogus</card_type>\n" +
            "      <current_vault>bogus</current_vault>\n" +
            "      <customer_id type=\"integer\">587544</customer_id>\n" +
            "      <customer_vault_token nil=\"true\"></customer_vault_token>\n" +
            "      <expiration_month type=\"integer\">1</expiration_month>\n" +
            "      <expiration_year type=\"integer\">2012</expiration_year>\n" +
            "      <first_name>michele</first_name>\n" +
            "      <id type=\"integer\">277431</id>\n" +
            "      <last_name>mondora</last_name>\n" +
            "      <masked_card_number>XXXX-XXXX-XXXX-1</masked_card_number>\n" +
            "      <vault_token>1</vault_token>\n" +
            "    </credit_card>\n" +
            "    <customer>\n" +
            "      <address></address>\n" +
            "      <address_2></address_2>\n" +
            "      <city></city>\n" +
            "      <country></country>\n" +
            "      <created_at type=\"datetime\">2011-05-20T06:24:57-04:00</created_at>\n" +
            "      <email>michele.mondora@gmail.com</email>\n" +
            "      <first_name>michele</first_name>\n" +
            "      <id type=\"integer\">587544</id>\n" +
            "      <last_name>mondora</last_name>\n" +
            "      <organization></organization>\n" +
            "      <phone></phone>\n" +
            "      <reference>sense-1</reference>\n" +
            "      <state></state>\n" +
            "      <updated_at type=\"datetime\">2011-05-20T11:53:52-04:00</updated_at>\n" +
            "      <zip></zip>\n" +
            "    </customer>\n" +
            "    <product>\n" +
            "      <accounting_code>controlmycloud</accounting_code>\n" +
            "      <archived_at type=\"datetime\">2011-05-23T05:34:36-04:00</archived_at>\n" +
            "      <created_at type=\"datetime\">2011-05-14T05:39:05-04:00</created_at>\n" +
            "      <description></description>\n" +
            "      <expiration_interval type=\"integer\" nil=\"true\"></expiration_interval>\n" +
            "      <expiration_interval_unit>never</expiration_interval_unit>\n" +
            "      <handle nil=\"true\"></handle>\n" +
            "      <id type=\"integer\">37654</id>\n" +
            "      <initial_charge_in_cents type=\"integer\" nil=\"true\"></initial_charge_in_cents>\n" +
            "      <interval type=\"integer\">1</interval>\n" +
            "      <interval_unit>month</interval_unit>\n" +
            "      <name>controlmycloud</name>\n" +
            "      <price_in_cents type=\"integer\">0</price_in_cents>\n" +
            "      <request_credit_card type=\"boolean\">false</request_credit_card>\n" +
            "      <require_credit_card type=\"boolean\">false</require_credit_card>\n" +
            "      <return_params></return_params>\n" +
            "      <return_url></return_url>\n" +
            "      <trial_interval type=\"integer\">1</trial_interval>\n" +
            "      <trial_interval_unit>month</trial_interval_unit>\n" +
            "      <trial_price_in_cents type=\"integer\">0</trial_price_in_cents>\n" +
            "      <updated_at type=\"datetime\">2011-05-23T05:34:36-04:00</updated_at>\n" +
            "      <product_family>\n" +
            "        <accounting_code nil=\"true\"></accounting_code>\n" +
            "        <description></description>\n" +
            "        <handle>test-product</handle>\n" +
            "        <id type=\"integer\">10064</id>\n" +
            "        <name>controlmycloud</name>\n" +
            "      </product_family>\n" +
            "    </product>\n" +
            "  </subscription>\n" +
            "</subscriptions>";

    private String productXml = "<product>\n" +
            "    <accounting_code>premium</accounting_code>\n" +
            "    <archived_at type=\"datetime\" nil=\"true\"></archived_at>\n" +
            "    <created_at type=\"datetime\">2011-05-23T05:32:55-04:00</created_at>\n" +
            "    <description></description>\n" +
            "    <expiration_interval type=\"integer\" nil=\"true\"></expiration_interval>\n" +
            "    <expiration_interval_unit>never</expiration_interval_unit>\n" +
            "    <handle>premium</handle>\n" +
            "    <id type=\"integer\">38156</id>\n" +
            "    <initial_charge_in_cents type=\"integer\" nil=\"true\"></initial_charge_in_cents>\n" +
            "    <interval type=\"integer\">1</interval>\n" +
            "    <interval_unit>month</interval_unit>\n" +
            "    <name>premium</name>\n" +
            "    <price_in_cents type=\"integer\">0</price_in_cents>\n" +
            "    <request_credit_card type=\"boolean\">true</request_credit_card>\n" +
            "    <require_credit_card type=\"boolean\">true</require_credit_card>\n" +
            "    <return_params>subscription_id={subscription_id}&amp;customer_reference={customer_reference}&amp;product_id={product_id}</return_params>\n" +
            "    <return_url>http://localhost:8080/sensesaas/offering/success</return_url>\n" +
            "    <trial_interval type=\"integer\" nil=\"true\"></trial_interval>\n" +
            "    <trial_interval_unit>month</trial_interval_unit>\n" +
            "    <trial_price_in_cents type=\"integer\">0</trial_price_in_cents>\n" +
            "    <updated_at type=\"datetime\">2011-05-24T12:00:56-04:00</updated_at>\n" +
            "    <product_family>\n" +
            "      <accounting_code nil=\"true\"></accounting_code>\n" +
            "      <description></description>\n" +
            "      <handle>test-product</handle>\n" +
            "      <id type=\"integer\">10064</id>\n" +
            "      <name>controlmycloud</name>\n" +
            "    </product_family>\n" +
            "  </product>";

    String subscriptionXml = "<subscription>\n" +
            "  <activated_at type=\"datetime\" nil=\"true\"></activated_at>\n" +
            "  <balance_in_cents type=\"integer\">0</balance_in_cents>\n" +
            "  <cancel_at_end_of_period type=\"boolean\">false</cancel_at_end_of_period>\n" +
            "  <canceled_at type=\"datetime\" nil=\"true\"></canceled_at>\n" +
            "  <cancellation_message nil=\"true\"></cancellation_message>\n" +
            "  <created_at type=\"datetime\">2011-05-20T10:18:02-04:00</created_at>\n" +
            "  <current_period_ends_at type=\"datetime\">2011-06-20T10:18:02-04:00</current_period_ends_at>\n" +
            "  <expires_at type=\"datetime\" nil=\"true\"></expires_at>\n" +
            "  <id type=\"integer\">579554</id>\n" +
            "  <next_assessment_at type=\"datetime\">2011-06-20T10:18:02-04:00</next_assessment_at>\n" +
            "  <state>trialing</state>\n" +
            "  <trial_ended_at type=\"datetime\">2011-06-20T10:18:02-04:00</trial_ended_at>\n" +
            "  <trial_started_at type=\"datetime\">2011-05-20T10:18:02-04:00</trial_started_at>\n" +
            "  <updated_at type=\"datetime\">2011-05-20T10:18:02-04:00</updated_at>\n" +
            "  <current_period_started_at type=\"datetime\">2011-05-20T10:18:02-04:00</current_period_started_at>\n" +
            "  <previous_state>trialing</previous_state>\n" +
            "  <signup_payment_id type=\"integer\">4767583</signup_payment_id>\n" +
            "  <signup_revenue>0.00</signup_revenue>\n" +
            "  <delayed_cancel_at nil=\"true\"></delayed_cancel_at>\n" +
            "  <credit_card>\n" +
            "    <billing_address>nob</billing_address>\n" +
            "    <billing_address_2>nob</billing_address_2>\n" +
            "    <billing_city>canberra</billing_city>\n" +
            "    <billing_country>AU</billing_country>\n" +
            "    <billing_state>ACT</billing_state>\n" +
            "    <billing_zip>02</billing_zip>\n" +
            "    <card_type>bogus</card_type>\n" +
            "    <current_vault>bogus</current_vault>\n" +
            "    <customer_id type=\"integer\">587544</customer_id>\n" +
            "    <customer_vault_token nil=\"true\"></customer_vault_token>\n" +
            "    <expiration_month type=\"integer\">1</expiration_month>\n" +
            "    <expiration_year type=\"integer\">2012</expiration_year>\n" +
            "    <first_name>michele</first_name>\n" +
            "    <id type=\"integer\">277431</id>\n" +
            "    <last_name>mondora</last_name>\n" +
            "    <masked_card_number>XXXX-XXXX-XXXX-1</masked_card_number>\n" +
            "    <vault_token>1</vault_token>\n" +
            "  </credit_card>\n" +
            "  <customer>\n" +
            "    <address></address>\n" +
            "    <address_2></address_2>\n" +
            "    <city></city>\n" +
            "    <country></country>\n" +
            "    <created_at type=\"datetime\">2011-05-20T06:24:57-04:00</created_at>\n" +
            "    <email>michele.mondora@gmail.com</email>\n" +
            "    <first_name>michele</first_name>\n" +
            "    <id type=\"integer\">587544</id>\n" +
            "    <last_name>mondora</last_name>\n" +
            "    <organization></organization>\n" +
            "    <phone></phone>\n" +
            "    <reference>sense-1</reference>\n" +
            "    <state></state>\n" +
            "    <updated_at type=\"datetime\">2011-05-20T11:53:52-04:00</updated_at>\n" +
            "    <zip></zip>\n" +
            "  </customer>\n" +
            "  <product>\n" +
            "    <accounting_code>controlmycloud</accounting_code>\n" +
            "    <archived_at type=\"datetime\">2011-05-23T05:34:36-04:00</archived_at>\n" +
            "    <created_at type=\"datetime\">2011-05-14T05:39:05-04:00</created_at>\n" +
            "    <description></description>\n" +
            "    <expiration_interval type=\"integer\" nil=\"true\"></expiration_interval>\n" +
            "    <expiration_interval_unit>never</expiration_interval_unit>\n" +
            "    <handle nil=\"true\"></handle>\n" +
            "    <id type=\"integer\">37654</id>\n" +
            "    <initial_charge_in_cents type=\"integer\" nil=\"true\"></initial_charge_in_cents>\n" +
            "    <interval type=\"integer\">1</interval>\n" +
            "    <interval_unit>month</interval_unit>\n" +
            "    <name>premium</name>\n" +
            "    <price_in_cents type=\"integer\">0</price_in_cents>\n" +
            "    <request_credit_card type=\"boolean\">false</request_credit_card>\n" +
            "    <require_credit_card type=\"boolean\">false</require_credit_card>\n" +
            "    <return_params></return_params>\n" +
            "    <return_url></return_url>\n" +
            "    <trial_interval type=\"integer\">1</trial_interval>\n" +
            "    <trial_interval_unit>month</trial_interval_unit>\n" +
            "    <trial_price_in_cents type=\"integer\">0</trial_price_in_cents>\n" +
            "    <updated_at type=\"datetime\">2011-05-23T05:34:36-04:00</updated_at>\n" +
            "    <product_family>\n" +
            "      <accounting_code nil=\"true\"></accounting_code>\n" +
            "      <description></description>\n" +
            "      <handle>test-product</handle>\n" +
            "      <id type=\"integer\">10064</id>\n" +
            "      <name>controlmycloud</name>\n" +
            "    </product_family>\n" +
            "  </product>\n" +
            "</subscription>";

    String familyXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<components type=\"array\">\n" +
            "  <component>\n" +
            "    <id type=\"integer\">4290</id>\n" +
            "    <name>mockdomain-amazon-machine-component</name>\n" +
            "    <price_per_unit_in_cents type=\"integer\" nil=\"true\"></price_per_unit_in_cents>\n" +
            "    <pricing_scheme>per_unit</pricing_scheme>\n" +
            "    <product_family_id type=\"integer\">10064</product_family_id>\n" +
            "    <unit_name>hour of management</unit_name>\n" +
            "    <unit_price type=\"decimal\">0.07</unit_price>\n" +
            "    <kind>metered_component</kind>\n" +
            "    <prices type=\"array\"/>\n" +
            "  </component>\n" +
            "  <component>\n" +
            "    <id type=\"integer\">4427</id>\n" +
            "    <name>mockdomain-amazon-free-component</name>\n" +
            "    <price_per_unit_in_cents type=\"integer\" nil=\"true\"></price_per_unit_in_cents>\n" +
            "    <pricing_scheme>per_unit</pricing_scheme>\n" +
            "    <product_family_id type=\"integer\">10064</product_family_id>\n" +
            "    <unit_name>instance</unit_name>\n" +
            "    <unit_price type=\"decimal\">0.0</unit_price>\n" +
            "    <kind>metered_component</kind>\n" +
            "    <prices type=\"array\"/>\n" +
            "  </component>\n" +
            "  <component>\n" +
            "    <id type=\"integer\">4454</id>\n" +
            "    <name>mockdomain-amazon-ec2-freemium-t1.micro-billcapping</name>\n" +
            "    <price_per_unit_in_cents type=\"integer\" nil=\"true\"></price_per_unit_in_cents>\n" +
            "    <pricing_scheme>per_unit</pricing_scheme>\n" +
            "    <product_family_id type=\"integer\">10064</product_family_id>\n" +
            "    <unit_name>hour of management</unit_name>\n" +
            "    <unit_price type=\"decimal\">0.0</unit_price>\n" +
            "    <kind>metered_component</kind>\n" +
            "    <prices type=\"array\"/>\n" +
            "  </component>\n" +
            "  <component>\n" +
            "    <id type=\"integer\">4455</id>\n" +
            "    <name>mockdomain-amazon-ec2-premium-t1.micro-billcapping</name>\n" +
            "    <price_per_unit_in_cents type=\"integer\" nil=\"true\"></price_per_unit_in_cents>\n" +
            "    <pricing_scheme>per_unit</pricing_scheme>\n" +
            "    <product_family_id type=\"integer\">10064</product_family_id>\n" +
            "    <unit_name>hour of management</unit_name>\n" +
            "    <unit_price type=\"decimal\">0.07</unit_price>\n" +
            "    <kind>metered_component</kind>\n" +
            "    <prices type=\"array\"/>\n" +
            "  </component>\n" +
            "  <component>\n" +
            "    <id type=\"integer\">4456</id>\n" +
            "    <name>mockdomain-amazon-ec2-sandbox</name>\n" +
            "    <price_per_unit_in_cents type=\"integer\" nil=\"true\"></price_per_unit_in_cents>\n" +
            "    <pricing_scheme>per_unit</pricing_scheme>\n" +
            "    <product_family_id type=\"integer\">10064</product_family_id>\n" +
            "    <unit_name>hour of management</unit_name>\n" +
            "    <unit_price type=\"decimal\">0.0</unit_price>\n" +
            "    <kind>metered_component</kind>\n" +
            "    <prices type=\"array\"/>\n" +
            "  </component>\n" +
            "</components>";

    String componentsXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<components type=\"array\">\n" +
            "  <component>\n" +
            "    <component_id type=\"integer\">4454</component_id>\n" +
            "    <subscription_id type=\"integer\">579554</subscription_id>\n" +
            "    <unit_balance type=\"integer\">490</unit_balance>\n" +
            "    <name>mockdomain-amazon-ec2-freemium-t1.micro-billcapping</name>\n" +
            "    <unit_name>hour of management</unit_name>\n" +
            "    <kind>metered_component</kind>\n" +
            "  </component>\n" +
            "  <component>\n" +
            "    <component_id type=\"integer\">4455</component_id>\n" +
            "    <subscription_id type=\"integer\">579554</subscription_id>\n" +
            "    <unit_balance type=\"integer\">0</unit_balance>\n" +
            "    <name>mockdomain-amazon-ec2-premium-t1.micro-billcapping</name>\n" +
            "    <unit_name>hour of management</unit_name>\n" +
            "    <kind>metered_component</kind>\n" +
            "  </component>\n" +
            "  <component>\n" +
            "    <component_id type=\"integer\">4456</component_id>\n" +
            "    <subscription_id type=\"integer\">579554</subscription_id>\n" +
            "    <unit_balance type=\"integer\">0</unit_balance>\n" +
            "    <name>mockdomain-amazon-ec2-sandbox</name>\n" +
            "    <unit_name>hour of management</unit_name>\n" +
            "    <kind>metered_component</kind>\n" +
            "  </component>\n" +
            "  <component>\n" +
            "    <component_id type=\"integer\">4427</component_id>\n" +
            "    <subscription_id type=\"integer\">579554</subscription_id>\n" +
            "    <unit_balance type=\"integer\">203</unit_balance>\n" +
            "    <name>mockdomain-amazon-free-component</name>\n" +
            "    <unit_name>instance</unit_name>\n" +
            "    <kind>metered_component</kind>\n" +
            "  </component>\n" +
            "  <component>\n" +
            "    <component_id type=\"integer\">4290</component_id>\n" +
            "    <subscription_id type=\"integer\">579554</subscription_id>\n" +
            "    <unit_balance type=\"integer\">2</unit_balance>\n" +
            "    <name>mockdomain-amazon-machine-component</name>\n" +
            "    <unit_name>hour of management</unit_name>\n" +
            "    <kind>metered_component</kind>\n" +
            "  </component>\n" +
            "</components>";

}

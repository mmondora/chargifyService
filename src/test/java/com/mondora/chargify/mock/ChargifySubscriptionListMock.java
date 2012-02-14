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

import com.mondora.chargify.domain.Subscription;
import com.mondora.chargify.exception.ChargifyException;

import java.io.ByteArrayInputStream;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 10:26 AM
 */
public class ChargifySubscriptionListMock extends ChargifyMock {

    @Override
    protected void setup(String key, String domain) {
        super.setup(key, domain);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Subscription getSubscription(Integer id) throws ChargifyException {
        return  (Subscription) parse(Subscription.class, new ByteArrayInputStream(subXml.getBytes()));
    }

    String subXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<subscription>\n" +
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
}

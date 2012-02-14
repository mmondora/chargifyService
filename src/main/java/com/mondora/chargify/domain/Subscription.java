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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/24/11 11:37 PM
 */
@XmlRootElement(name="subscription")
public class Subscription {
    @XmlElement( name="id")
    public Integer id;
    @XmlElement( name="activated_at")
    public Date activated_at;
    @XmlElement( name="balance_in_cents")
    public Integer balance_in_cents;
    @XmlElement( name="cancel_at_end_of_period")
    public Boolean cancel_at_end_of_period;
    @XmlElement( name="canceled_at")
    public Date canceled_at;
    @XmlElement( name="cancellation_message")
    public String cancellation_message;
    @XmlElement( name="created_at")
    public Date created_at;
    @XmlElement( name="current_period_ends_at")
    public Date current_period_ends_at;
    @XmlElement( name="expires_at")
    public Date expires_at;
    @XmlElement( name="next_assessment_at")
    public Date next_assessment_at;
    @XmlElement( name="state")
    public String state;
    @XmlElement( name="trial_ended_at")
    public Date trial_ended_at;
    @XmlElement( name="trial_started_at")
    public Date trial_started_at;
    @XmlElement( name="updated_at")
    public Date updated_at;
    @XmlElement( name="current_period_started_at")
    public Date current_period_started_at;
    @XmlElement( name="previous_state")
    public String previous_state;
    @XmlElement( name="signup_payment_id")
    public Integer signup_payment_id;
    @XmlElement( name="signup_revenue")
    public String signup_revenue;
    @XmlElement( name="delayed_cancel_at")
    public String delayed_cancel_at;

    @XmlElement( name="customer")
    public Customer customer;

    @XmlElement( name="product")
    public Product product;

    @XmlElement( name="credit_card")
    public CreditCard creditcard;

    /*
    <credit_card>
      <billing_address>nob</billing_address>
      <billing_address_2>nob</billing_address_2>
      <billing_city>canberra</billing_city>
      <billing_country>AU</billing_country>
      <billing_state>ACT</billing_state>
      <billing_zip>02</billing_zip>
      <card_type>bogus</card_type>
      <current_vault>bogus</current_vault>
      <customer_id type="integer">587544</customer_id>
      <customer_vault_token nil="true"></customer_vault_token>
      <expiration_month type="integer">1</expiration_month>
      <expiration_year type="integer">2012</expiration_year>
      <first_name>michele</first_name>
      <id type="integer">277431</id>
      <last_name>mondora</last_name>
      <masked_card_number>XXXX-XXXX-XXXX-1</masked_card_number>
      <vault_token>1</vault_token>
    </credit_card>
    <customer>
      <address></address>
      <address_2></address_2>
      <city></city>
      <country></country>
      <created_at type="datetime">2011-05-20T06:24:57-04:00</created_at>
      <email>michele.mondora@gmail.com</email>
      <first_name>michele</first_name>
      <id type="integer">587544</id>
      <last_name>mondora</last_name>
      <organization></organization>
      <phone></phone>
      <reference>acme-1</reference>
      <state></state>
      <updated_at type="datetime">2011-05-20T11:53:52-04:00</updated_at>
      <zip></zip>
    </customer>
    <product>
      <accounting_code>controlmycloud</accounting_code>
      <archived_at type="datetime">2011-05-23T05:34:36-04:00</archived_at>
      <created_at type="datetime">2011-05-14T05:39:05-04:00</created_at>
      <description></description>
      <expiration_interval type="integer" nil="true"></expiration_interval>
      <expiration_interval_unit>never</expiration_interval_unit>
      <handle nil="true"></handle>
      <id type="integer">37654</id>
      <initial_charge_in_cents type="integer" nil="true"></initial_charge_in_cents>
      <interval type="integer">1</interval>
      <interval_unit>month</interval_unit>
      <name>controlmycloud</name>
      <price_in_cents type="integer">0</price_in_cents>
      <request_credit_card type="boolean">false</request_credit_card>
      <require_credit_card type="boolean">false</require_credit_card>
      <return_params></return_params>
      <return_url></return_url>
      <trial_interval type="integer">1</trial_interval>
      <trial_interval_unit>month</trial_interval_unit>
      <trial_price_in_cents type="integer">0</trial_price_in_cents>
      <updated_at type="datetime">2011-05-23T05:34:36-04:00</updated_at>
      <product_family>
        <accounting_code nil="true"></accounting_code>
        <description></description>
        <handle>test-product</handle>
        <id type="integer">10064</id>
        <name>controlmycloud</name>
      </product_family>
    </product>
  </subscription>
  */

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", activated_at=" + activated_at +
                ", balance_in_cents=" + balance_in_cents +
                ", state='" + state + '\'' +
                ", Customer='" + customer + "\'" +
                ", Product='" + product + "\'" +
                ", CreditCard='" + creditcard + "\'" +
                '}';
    }
}

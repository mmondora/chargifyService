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

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/24/11 11:53 PM
 */
@XmlRootElement( name="credit_card")
public class CreditCard {
    @XmlElement(name="billing_address")
    public String billing_address;
    @XmlElement(name="first_name")
    public String first_name;
    @XmlElement(name="last_name")
    public String last_name;
    @XmlElement(name="expiration_month")
    public Integer expiration_month;
    @XmlElement(name="expiration_year")
    public Integer expiration_year;
    @XmlElement(name="card_type")
    public Integer card_type;

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
      <id type="integer">277431</id>
      <masked_card_number>XXXX-XXXX-XXXX-1</masked_card_number>
      <vault_token>1</vault_token>
    </credit_card>

     */

    @Override
    public String toString() {
        return "CreditCard{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", expiration_month=" + expiration_month +
                ", expiration_year=" + expiration_year +
                ", card_type=" + card_type +
                '}';
    }
}

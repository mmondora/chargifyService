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
 * Date: 5/24/11 5:59 PM
 */
@XmlRootElement(name="product")
public class Product {
    @XmlElement(name = "id")
    public Integer id;
    @XmlElement(name = "price_in_cents")
    public Integer priceInCents;
    @XmlElement(name = "name")
    public String name;
    @XmlElement(name = "handle")
    public String handle;
    @XmlElement(name = "description")
    public String description;
    @XmlElement(name = "product_family")
    public ProductFamily product_family;
    @XmlElement(name = "accounting_code")
    public String accountingCode;
    @XmlElement(name = "interval_unit")
    public String intervalUnit;
    @XmlElement(name = "interval")
    public Integer interval;
    @XmlElement(name = "initial_charge_in_cents")
    public String initial_charge_in_cents;
    @XmlElement(name = "trial_price_in_cents")
    public String trial_price_in_cents;
    @XmlElement(name = "trial_interval")
    public String trial_interval;
    @XmlElement(name = "trial_interval_unit")
    public String trial_interval_unit;
    @XmlElement(name = "expiration_interval")
    public Integer expiration_interval;
    @XmlElement(name = "expiration_interval_unit")
    public String expiration_interval_unit;
    @XmlElement(name = "return_url")
    public String return_url;
    @XmlElement(name = "return_params")
    public String return_params;
    @XmlElement(name = "require_credit_card")
    public Boolean require_credit_card;
    @XmlElement(name = "request_credit_card")
    public Boolean request_credit_card;
//    @XmlElement(name = "type")
//    public Integer type;
    @XmlElement(name = "created_at")
    public Date created_at;
    @XmlElement(name = "updated_at")
    public Date updated_at;
    @XmlElement(name = "archived_at")
    public Date archived_at;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", handle='" + handle + '\'' +
                ", description='" + description + '\'' +
                ", product_family=" + product_family +
                '}';
    }
}
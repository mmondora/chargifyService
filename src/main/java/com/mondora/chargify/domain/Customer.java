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
 * Date: 5/24/11 11:15 PM
 */
@XmlRootElement(name="customer")
public class Customer {
    @XmlElement(name="id")
    public Integer id;
    @XmlElement(name="first_name")
    public String firstName;
    @XmlElement(name="last_name")
    public String lastName;
    @XmlElement(name="email")
    public String email;
    @XmlElement(name="address")
    public String address;
    @XmlElement(name="address_2")
    public String address_2;
    @XmlElement(name="phone")
    public String phone;
    @XmlElement(name="zip")
    public String zip;
    @XmlElement(name="city")
    public String city;
    @XmlElement(name="state")
    public String state;
    @XmlElement(name="country")
    public String country;
    @XmlElement(name="organization")
    public String organization;
    @XmlElement(name="reference")
    public String reference;
    @XmlElement(name="created_at")
    public Date createdAt;
    @XmlElement(name="updated_at")
    public Date updatedAt;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", address_2='" + address_2 + '\'' +
                ", phone='" + phone + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", organization='" + organization + '\'' +
                ", reference='" + reference + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

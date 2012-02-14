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
 * Date: 5/25/11 6:58 AM
 *
 * <product_family>
          <accounting_code>`your value`</accounting_code>
          <description >`your value`</description>
          <handle>`your value`</handle>
          <name>`your value`</name>
        </product_family>
 */
@XmlRootElement(name="product_family")
public class ProductFamily {
    @XmlElement(name="name")
    public String name;
    @XmlElement(name="id")
    public String id;
    @XmlElement(name="accounting_code")
    public String accounting_code;
    @XmlElement(name="description")
    public String description;
    @XmlElement(name="handle")
    public String handle;

    @Override
    public String toString() {
        return "ProductFamily{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", accounting_code='" + accounting_code + '\'' +
                ", description='" + description + '\'' +
                ", handle='" + handle + '\'' +
                '}';
    }
}

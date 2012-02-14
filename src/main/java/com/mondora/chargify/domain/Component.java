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
 * Date: 5/25/11 12:12 AM
 */
@XmlRootElement(name="component")
public class Component {
    @XmlElement(name="id")
    Integer id;
    @XmlElement(name="component_id")
    Integer component_id = id;
    @XmlElement(name="subscription_id")
    public Integer subscription;
    @XmlElement(name="unit_balance")
    public Integer unitBalance;
    @XmlElement(name="name")
    public String name;
    @XmlElement(name="unit_name")
    public String unitName;
    @XmlElement(name="kind")
    public String kind;

    /**
     * need the getter because of inconsistency between xml produced by chargify from
     * listing components from product families and
     * <pre>
     *     <component>
     *      <id type="integer">4290</id>
     *      <name>amazon-machine-component</name>
     *      <price_per_unit_in_cents type="integer" nil="true"></price_per_unit_in_cents>
     *      <pricing_scheme>per_unit</pricing_scheme>
     *      <product_family_id type="integer">10064</product_family_id>
     *      <unit_name>hour of management</unit_name>
     *      <unit_price type="decimal">0.07</unit_price>
     *      <kind>metered_component</kind>
     *      <prices type="array"/>
     *     </component>
     * </pre>
     *
     * listing componenst from subscription
     *  <component>
     *      <component_id type="integer">4290</component_id>
     *      <subscription_id type="integer">579554</subscription_id>
     *      <unit_balance type="integer">2</unit_balance>
     *      <name>amazon-machine-component</name>
     *      <unit_name>hour of management</unit_name>
     *      <kind>metered_component</kind>
     *  </component>
     *
     * @return Integer the id of component
     */
    public Integer getId() {
        if( id == null ) return component_id;
        return id;
    }

    @Override
    public String toString() {
        return "Component{" +
                "name='" + name + '\'' +
                ", unitName='" + unitName + '\'' +
                ", id=" + getId() +
                ", kind='" + kind + '\'' +
                '}';
    }
}

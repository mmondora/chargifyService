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

import com.mondora.chargify.domain.Components;
import com.mondora.chargify.domain.Subscription;
import com.mondora.chargify.exception.ChargifyException;

import java.io.ByteArrayInputStream;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 9:22 AM
 */
public class ChargifyProductListMock extends ChargifyMock {

    @Override
    protected void setup(String key, String domain) {
        super.setup(key, domain);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Components listComponents(Subscription subscription) throws ChargifyException {
        return (Components) parse(Components.class, new ByteArrayInputStream(subscriptionXml.getBytes()));
    }

    @Override
    public Components listComponents(String family) throws ChargifyException {
        return (Components) parse(Components.class, new ByteArrayInputStream(familyXml.getBytes()));
    }

    String familyXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<components type=\"array\">\n" +
            "  <component>\n" +
            "    <id type=\"integer\">4290</id>\n" +
            "    <name>mockdomain-mockdomain-amazon-machine-component</name>\n" +
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

    String subscriptionXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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

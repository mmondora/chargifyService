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

import com.mondora.chargify.controller.ChargifyAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.Field;
import java.util.List;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 12:11 AM
 */
@XmlRootElement(name = "components")
public class Components {
    static Logger logger = LoggerFactory.getLogger(ChargifyAdapter.class);

    @XmlElement(name = "component")
    public List<Component> items;

    public Component find(String name) {
        return find("name", name);
    }

    Component find(String field, Object value) {
        try {
            Field f = Component.class.getDeclaredField(field);
            if (f != null)
                for (Component c : items) {
                    Object field_value = f.get(c);
                    if (value == null && field_value == null) return c;
                    if (field_value != null && value.equals(field_value)) return c;
                }
        } catch (NoSuchFieldException e) {
            if (logger.isDebugEnabled()) logger.debug("Field " + field + " do not exists in Component.class");
        } catch (IllegalAccessException e) {
            if (logger.isDebugEnabled()) logger.debug("IllegalAccess for field " + field + " in Component.class");
        }
        return null;
    }

    @Override
    public String toString() {
        return "Components{" +
                "items=" + items +
                '}';
    }
}

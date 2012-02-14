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

package com.mondora.chargify;

import com.mondora.chargify.controller.ChargifyAdapter;
import junit.framework.TestCase;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 9:18 PM
 */
public class ChargifyFactoryTest extends TestCase {

    public void testFactory() {
        ChargifyAdapter chargify = ChargifyFactory.getInstance().getChargify();
        assertNotNull(chargify);
        System.out.println(chargify);
    }

    public void testFactoryFromAnotherLocation() {
        System.setProperty("sense.dir.conf", "/error/path/");
        ChargifyAdapter chargify = ChargifyFactory.getInstance().getChargify();
        assertNotNull(chargify);
        System.out.println(chargify);
    }
}

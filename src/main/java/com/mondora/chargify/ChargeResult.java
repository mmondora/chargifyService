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

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 8:48 AM
 * <p/>
 * container for ChargifyAdapter execution status
 */
public class ChargeResult {
    public static String OK = "OK";
    public static String ERR = "ERR";
    public static String DUP = "DUP";

    public String resultCode = ERR; // default is error
    public String message = "";
    public Object result = null;        // the object returned

    @Override
    public String toString() {
        return "ChargeResult{" +
                "resultCode='" + resultCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

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

package com.mondora.chargify.exception;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/24/11 11:17 PM
 */
public class ChargifyException extends Exception {
    public ChargifyException(String s) {
        super(s);
    }

    public ChargifyException( Throwable cause ){
        super(cause);
    }

    public ChargifyException(String s, Exception e) {
        super(s,e);
    }
}

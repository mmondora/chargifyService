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
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mmondora
 * Date: 11/28/11
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="errors")
public class Errors {
    @XmlElement(name = "error")
    public List<String> error;

    @Override
    public String toString() {
        if( error.size()>1 ) {
            return "Errors " +
                    error.toString() ;
        } else {
            if( error.isEmpty() ) {
                return "No Errors";
            } else {
                return error.get(0);
            }
        }

    }
}

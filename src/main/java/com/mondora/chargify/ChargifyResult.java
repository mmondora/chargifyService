/*
 * Copyright (c)
 * 2006 .. 2010 Mondora.com
 * 2010 .. 2011 Sensiblecloud.com
 */

package com.mondora.chargify;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 8:48 AM
 * <p/>
 * container for ChargifyService execution status
 */
public class ChargifyResult {
    public static String OK = "OK";
    public static String ERR = "ERR";
    public static String DUP = "DUP";

    public String resultCode = ERR; // default is error
    public String message = "";
    public Object result = null;        // the object returned

    @Override
    public String toString() {
        return "ChargifyResult{" +
                "resultCode='" + resultCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

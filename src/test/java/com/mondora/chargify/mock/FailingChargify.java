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

import com.mondora.chargify.controller.ChargifyAdapter;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Random;

/**
 * mondora.com
 * User: michele.mondora@mondora.com
 * Date: 5/25/11 10:54 AM
 */
public class FailingChargify extends ChargifyAdapter {

    @Override
    protected HttpClient getClient() {
        return org.mockito.Mockito.mock(HttpClient.class);
    }

    public FailingChargify() {
        super(null, "ChargifyMock");
        logger.info("creating a null chargify ");
    }

    @Override
    protected void setup(String key, String domain) {
    }

    int[] codes = new int[]{401, 402, 403, 404, 500, 422};
    Random randomizer = new Random();

    @Override
    protected HttpResponse executeHttpMethod(HttpRequestBase method) throws IOException {
        randomizer.setSeed(System.currentTimeMillis());

        final int code = codes[randomizer.nextInt(codes.length)];
        final String errorMessage = "Simulated error " + code;
        HttpResponse response = Mockito.mock(HttpResponse.class);
        Mockito.stub(response.getStatusLine()).toReturn(
                new StatusLine() {
                    @Override
                    public ProtocolVersion getProtocolVersion() {
                        return null;
                    }

                    @Override
                    public int getStatusCode() {
                        return code;
                    }

                    @Override
                    public String getReasonPhrase() {
                        return errorMessage;
                    }
                });
        return response;
    }
}
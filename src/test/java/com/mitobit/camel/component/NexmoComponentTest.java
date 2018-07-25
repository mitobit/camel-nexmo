/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mitobit.camel.component;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

public class NexmoComponentTest extends CamelTestSupport {

	private Properties config = new Properties();

	@Test
	public void testNexmo() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:result");
		mock.expectedMinimumMessageCount(1);
		sendBody("direct:nexmotest", "Nexmo test!");
		assertMockEndpointsSatisfied();
	}

	@Override
	protected void doPreSetup() throws Exception {
		config.load(getClass().getResourceAsStream("/application.properties"));
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() {
				from("direct:nexmotest").
				  to(String.format("nexmo://send?apiKey=%s&apiSecret=%s&to=+39000000",
						  config.getProperty("nexmo.apiKey"),
						  config.getProperty("nexmo.apiSecret"))).process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						log.debug("", exchange);

					}
				  }).
				  to("mock:result");
			}
		};
	}
}

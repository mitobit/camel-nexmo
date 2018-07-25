package com.mitobit.camel.component.nexmo;

import com.nexmo.client.sms.messages.BinaryMessage;
import com.nexmo.client.sms.messages.Message;
import com.nexmo.client.sms.messages.TextMessage;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.util.ObjectHelper;

/**
 * Sms Binding
 *
 * @author <a href="mailto:michele.blasi@mitobit.com">Michele Blasi</a>
 */
public class SmsBinding {

	public Message createSmsMessage(NexmoEndpoint endpoint, Exchange exchange) {
		String from = exchange.getIn().getHeader(NexmoConstants.NEXMO_FROM, endpoint.getFrom(), String.class);
		String to = exchange.getIn().getHeader(NexmoConstants.NEXMO_TO, endpoint.getTo(), String.class);
		if (ObjectHelper.isEmpty(from)) {
			from = NexmoConstants.NEXMO_DEFAULT_FROM;
		}
		if (ObjectHelper.isEmpty(to)) {
			throw new RuntimeCamelException("Missing 'to' param");
		}
		final Object messageBody = exchange.getIn().getBody();
		if (messageBody instanceof String) {
			return new TextMessage(from, to, (String) messageBody);
		} else if (messageBody instanceof byte[]) {
			return new BinaryMessage(from, to, (byte[]) messageBody, null);
		}
		throw new RuntimeCamelException("Cannot create a Nexmo message by this message body " + messageBody);
	}

}

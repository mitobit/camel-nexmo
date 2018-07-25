package com.mitobit.camel.component.nexmo;

import com.mitobit.camel.component.nexmo.error.NexmoOperationFailedException;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.Message;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Nexmo producer.
 *
 * @author <a href="mailto:michele.blasi@mitobit.com">Michele Blasi</a>
 */
public class NexmoProducer extends DefaultProducer {

	private static final transient Logger LOG = LoggerFactory.getLogger(NexmoProducer.class);

	private final NexmoClient smsClient;

	private boolean throwException;

	public NexmoProducer(NexmoEndpoint endpoint) {
		super(endpoint);
		this.smsClient = endpoint.createClient();
		this.throwException = endpoint.isThrowExceptionOnFailure();
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message;
		final Object body = exchange.getIn().getBody();
		if (body instanceof Message) {
			// Body is directly a Message
			message = (Message) body;
		} else {
			// Create a message with exchange data
			message = getEndpoint().getBinding().createSmsMessage(getEndpoint(), exchange);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Sending Message: {}", message);
		}
		SmsSubmissionResult[] results = smsClient.getSmsClient().submitMessage(message);
		if (throwException) {
			for (SmsSubmissionResult result : results) {
				if (result.getStatus() > SmsSubmissionResult.STATUS_OK && result.getErrorText() != null) {
					throw new NexmoOperationFailedException(result.getErrorText(), results);
				}
			}
		}
		exchange.getOut().setBody(results);
		// copy headers from IN to OUT to propagate them
		exchange.getOut().setHeaders(exchange.getIn().getHeaders());
	}

	@Override
	public NexmoEndpoint getEndpoint() {
		return (NexmoEndpoint) super.getEndpoint();
	}

}

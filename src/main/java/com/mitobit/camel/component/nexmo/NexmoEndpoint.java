package com.mitobit.camel.component.nexmo;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriParam;

/**
 * Represents a Nexmo endpoint.
 *
 * @author <a href="mailto:michele.blasi@mitobit.com">Michele Blasi</a>
 */
public class NexmoEndpoint extends DefaultEndpoint {

	private SmsBinding binding;
	@UriParam
	private String apiKey;
	@UriParam
	private String apiSecret;
	@UriParam
	private String from;
	@UriParam
	private String to;
	@UriParam
	private boolean ssl = true;
	@UriParam(label = "producer", defaultValue = "true",
			description = "Option to disable throwing the NexmoOperationFailedException in case of failed responses from the remote server."
					+ " This allows you to get all responses regardless of the HTTP status code.")
	boolean throwExceptionOnFailure = true;

	public NexmoEndpoint() {
	}

	public NexmoEndpoint(String uri, NexmoComponent component) {
		super(uri, component);
	}

	@Override
	public Producer createProducer() throws Exception {
		return new NexmoProducer(this);
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		throw new UnsupportedOperationException("Nexmo endpoints are not meant to be consumed from.");
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public NexmoClient createClient() {
		NexmoClient client;
		AuthMethod auth = new TokenAuthMethod(apiKey, apiSecret);
		try {
			if (ssl) {
				client = new NexmoClient(auth);
			} else {
				client = new NexmoClient(auth);
			}
		} catch (Exception e) {
			throw new RuntimeCamelException("Failed to instantiate the Nexmo client", e);
		}
		return client;
	}

	// Properties
	// -------------------------------------------------------------------------

	public SmsBinding getBinding() {
		if (binding == null) {
			binding = new SmsBinding();
		}
		return binding;
	}

	public void setBinding(SmsBinding binding) {
		this.binding = binding;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public boolean isThrowExceptionOnFailure() {
		return throwExceptionOnFailure;
	}

	/**
	 * Option to disable throwing the {@link com.mitobit.camel.component.nexmo.error.NexmoOperationFailedException} in case of failed responses from the remote server.
	 * This allows you to get all responses regardless of the HTTP status code.
	 */
	public void setThrowExceptionOnFailure(boolean throwExceptionOnFailure) {
		this.throwExceptionOnFailure = throwExceptionOnFailure;
	}
}

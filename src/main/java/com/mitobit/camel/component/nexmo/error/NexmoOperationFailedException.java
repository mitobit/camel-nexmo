package com.mitobit.camel.component.nexmo.error;

import com.nexmo.client.sms.SmsSubmissionResult;
import org.apache.camel.CamelException;

/**
 * Nexmo Operation Failed Exception
 *
 * @author <a href="mailto:michele.blasi@mitobit.com">Michele Blasi</a>
 */
public class NexmoOperationFailedException extends CamelException {

    private SmsSubmissionResult[] errors;

    public NexmoOperationFailedException() {
        super();
    }

    public NexmoOperationFailedException(String message, SmsSubmissionResult[] errors) {
        super(message);
        this.errors = errors;
    }

    public NexmoOperationFailedException(String message) {
        super(message);
    }

    public NexmoOperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NexmoOperationFailedException(Throwable cause) {
        super(cause);
    }

    public SmsSubmissionResult[] getErrors() {
        return errors;
    }

    public void setErrors(SmsSubmissionResult[] errors) {
        this.errors = errors;
    }

}

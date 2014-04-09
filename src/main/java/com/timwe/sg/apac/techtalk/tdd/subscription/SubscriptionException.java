package com.timwe.sg.apac.techtalk.tdd.subscription;

/**
 * @author Rashidi Zin
 */
public class SubscriptionException extends RuntimeException {

    public SubscriptionException() {
        super();
    }

    public SubscriptionException(String message) {
        super(message);
    }

    public SubscriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubscriptionException(Throwable cause) {
        super(cause);
    }
}

package org.okq550.ticketing.exceptions;

public class TicketsSoldOutException extends RuntimeException {
    public TicketsSoldOutException() {
    }

    public TicketsSoldOutException(String message) {
        super(message);
    }

    public TicketsSoldOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketsSoldOutException(Throwable cause) {
        super(cause);
    }

    public TicketsSoldOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

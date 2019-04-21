package com.murk.telegram.ping.handler.core.exception;

import lombok.AllArgsConstructor;


public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException() {
    }

    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthorizedException(Throwable cause) {
        super(cause);
    }

}

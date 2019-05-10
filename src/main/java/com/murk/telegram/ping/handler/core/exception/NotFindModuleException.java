package com.murk.telegram.ping.handler.core.exception;

import lombok.AllArgsConstructor;


public class NotFindModuleException extends RuntimeException {

    public NotFindModuleException() {
    }

    public NotFindModuleException(String message) {
        super(message);
    }

    public NotFindModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFindModuleException(Throwable cause) {
        super(cause);
    }

}

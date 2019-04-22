package com.murk.telegram.ping.handler.core.exception;

public class ClientNotFoundException extends NotAuthorizedException {
    public ClientNotFoundException() {}

    public ClientNotFoundException(String message)
    {
        super(message);
    }

    public ClientNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

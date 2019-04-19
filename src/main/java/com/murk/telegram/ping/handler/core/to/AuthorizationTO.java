package com.murk.telegram.ping.handler.core.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorizationTO extends AbstractStatus {
    private String message;

    public AuthorizationTO(@NonNull STATUS status, String message) {
        super(status);
        this.message = message;
    }
}

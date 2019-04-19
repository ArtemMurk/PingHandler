package com.murk.telegram.ping.handler.core.to;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class PingTO extends AbstractStatus {
    private String message;

    public PingTO(@NonNull STATUS status, String message) {
        super(status);
        this.message = message;
    }
}

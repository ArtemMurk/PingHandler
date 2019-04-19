package com.murk.telegram.ping.handler.core.to;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class PingResponseTO{
    private @NonNull STATUS status;
    private @NonNull String message;
}

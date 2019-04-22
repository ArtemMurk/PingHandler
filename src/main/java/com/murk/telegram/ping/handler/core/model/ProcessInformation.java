package com.murk.telegram.ping.handler.core.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class ProcessInformation {
    private @NonNull String name;
    private @NonNull long checkTime;
    private @NonNull long lastPingTime;

    public ProcessInformation(@NonNull String name, @NonNull long checkTime) {
        this.name = name;
        this.checkTime = checkTime;
        lastPingTime = System.currentTimeMillis();
    }
}

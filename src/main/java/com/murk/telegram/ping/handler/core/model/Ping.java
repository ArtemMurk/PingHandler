package com.murk.telegram.ping.handler.core.model;

import lombok.Data;

@Data
public class Ping {
    private String clientId;
    private String moduleId;
    private String processId;

    public Ping(String clientId, String moduleId, String processId) {
        this.clientId = clientId;
        this.moduleId = moduleId;
        this.processId = processId;
    }
}

package com.murk.telegram.ping.handler.core.model;

import lombok.Data;

@Data
public class Ping {
    private String clientId;
    private String moduleId;
    private String processId;
}

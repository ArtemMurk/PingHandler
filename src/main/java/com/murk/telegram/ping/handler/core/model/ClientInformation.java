package com.murk.telegram.ping.handler.core.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@RequiredArgsConstructor()
public class ClientInformation {
    private String name;
    private Map<String,ModuleInformation> modules = new ConcurrentHashMap<>();

    public void setModule(@NonNull ModuleInformation module)
    {
        modules.put(module.getName(),module);
    }
}

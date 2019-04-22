package com.murk.telegram.ping.handler.core.model;

import lombok.Data;
import lombok.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class ClientInformation {
    private String name;
    private Map<String,ModuleInformation> modules = new ConcurrentHashMap<>();

    public ClientInformation(String name) {
        this.name = name;
    }

    public void setModule(@NonNull ModuleInformation module)
    {
        modules.put(module.getName(),module);
    }

    public ModuleInformation getModule(@NonNull String modelName) {
        return modules.get(modelName);
    }
}

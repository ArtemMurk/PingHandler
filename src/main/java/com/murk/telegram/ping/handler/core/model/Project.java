package com.murk.telegram.ping.handler.core.model;

import lombok.NonNull;
import lombok.Value;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Value
public class Project {
    private String name;
    private Set< String> modules = ConcurrentHashMap.newKeySet();

    public Project(String name) {
        this.name = name;
    }

    public void setModule(@NonNull String moduleKey)
    {
        modules.add(moduleKey);
    }

    public boolean containsModule(@NonNull String moduleKey) {
        return modules.contains(moduleKey);
    }
}

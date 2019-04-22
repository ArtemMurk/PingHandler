package com.murk.telegram.ping.handler.core.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@RequiredArgsConstructor
public class ModuleInformation {
    private @NonNull String name;
    private Map<String, ProcessInformation> processes = new ConcurrentHashMap<>();

    public void setProcess(@NonNull ProcessInformation process)
    {
        processes.put(process.getName(),process);
    }

    public ProcessInformation getProcess(String processName) {
        return processes.get(processName);
    }

    public boolean containsProcess(String process) {
        return processes.containsKey(process);
    }
}

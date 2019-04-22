package com.murk.telegram.ping.handler.core.cache;


import com.murk.telegram.ping.handler.core.model.ProcessInformation;
import com.murk.telegram.ping.handler.core.to.PingResponseTO;

public interface PingCache {

    boolean containsClient(String clientKey);

    boolean containsProcess(String clientKey, String moduleName, String processName);

    ProcessInformation getProcessInformation(String clientKey, String moduleName, String processName);

    void putProcessInformation(String clientKey, String moduleName, ProcessInformation processInformation);

    void putPing(String clientKey, String moduleName, String processName);
}

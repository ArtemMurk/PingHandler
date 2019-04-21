package com.murk.telegram.ping.handler.core.cache;


import com.murk.telegram.ping.handler.core.to.PingResponseTO;

public interface PingCache {
    PingResponseTO authorize(String clientKey, String moduleName, String processName, long checkTime);

    PingResponseTO ping(String clientKey, String moduleName, String processName);
}

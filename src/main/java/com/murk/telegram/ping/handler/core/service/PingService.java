package com.murk.telegram.ping.handler.core.service;

import com.murk.telegram.ping.handler.core.to.PingResponseTO;

public interface PingService {

    PingResponseTO authorization(String clientKey, String moduleName, String processName);

    PingResponseTO ping(String clientKey, String moduleName, String processName);

}

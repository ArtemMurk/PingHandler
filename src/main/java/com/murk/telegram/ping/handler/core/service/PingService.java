package com.murk.telegram.ping.handler.core.service;

import com.murk.telegram.ping.handler.core.to.PingTO;

public interface PingService {

    PingTO ping(String projectName, String moduleKey);

}

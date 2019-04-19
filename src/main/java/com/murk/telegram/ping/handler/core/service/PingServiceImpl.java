package com.murk.telegram.ping.handler.core.service;

import com.murk.telegram.ping.handler.core.to.PingResponseTO;
import com.murk.telegram.ping.handler.core.to.STATUS;
import com.murk.telegram.ping.handler.core.utils.ValidationUtil;
import org.springframework.stereotype.Service;

@Service
public class PingServiceImpl implements PingService {


    @Override
    public PingResponseTO authorization(String clientKey, String moduleName, String processName) {
        ValidationUtil.validate(clientKey,moduleName,processName);
        return new PingResponseTO(STATUS.SUCCESS,processName);
    }

    @Override
    public PingResponseTO ping(String clientKey, String moduleName, String processName) {
        ValidationUtil.validate(clientKey,moduleName,processName);
        return new PingResponseTO(STATUS.SUCCESS,processName);
    }
}

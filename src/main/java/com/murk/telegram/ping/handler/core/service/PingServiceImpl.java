package com.murk.telegram.ping.handler.core.service;

import com.murk.telegram.ping.handler.core.cache.PingCache;
import com.murk.telegram.ping.handler.core.to.PingResponseTO;
import com.murk.telegram.ping.handler.core.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PingServiceImpl implements PingService {

    private PingCache cache;

    @Autowired
    public PingServiceImpl(PingCache cache) {
        this.cache = cache;
    }

    @Override
    public PingResponseTO authorization(String clientKey, String moduleName, String processName, long checkTime) {
        PingResponseTO authorization;
        try {
            ValidationUtil.validate(clientKey,moduleName,processName);
            authorization = cache.authorize(clientKey,moduleName,processName,checkTime);

        } catch (RuntimeException e) {
            log.warn("Authorization is fail, clineKey={}, cause={}",clientKey,e.getMessage());
            throw e;
        }

        return authorization;
    }

    @Override
    public PingResponseTO ping(String clientKey, String moduleName, String processName) {
        PingResponseTO ping;
        try {
            ValidationUtil.validate(clientKey,moduleName,processName);
            ping = cache.ping(clientKey,moduleName,processName);

        } catch (RuntimeException e) {
            log.warn("Ping is fail, clineKey={}, cause={}",clientKey,e.getMessage());
            throw e;
        }

        return ping;
    }
}

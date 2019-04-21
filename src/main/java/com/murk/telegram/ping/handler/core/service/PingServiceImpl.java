package com.murk.telegram.ping.handler.core.service;

import com.murk.telegram.ping.handler.core.cache.PingCache;
import com.murk.telegram.ping.handler.core.dao.PingDao;
import com.murk.telegram.ping.handler.core.to.PingResponseTO;
import com.murk.telegram.ping.handler.core.to.STATUS;
import com.murk.telegram.ping.handler.core.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PingServiceImpl implements PingService {

    private PingCache cache;
    private PingDao dao;

    @Autowired
    public PingServiceImpl(PingCache cache,PingDao dao) {
        this.cache = cache;
        this.dao = dao;
    }

    @Override
    public PingResponseTO authorization(String clientKey, String moduleName, String processName) {
        try {
            ValidationUtil.validate(clientKey,moduleName,processName);
        } catch (IllegalArgumentException e) {
            log.warn("Authorization is fail, clineKey={}, cause={}",clientKey,e.getMessage());
            throw e;
        }

        return new PingResponseTO(STATUS.SUCCESS,processName);
    }

    @Override
    public PingResponseTO ping(String clientKey, String moduleName, String processName) {
        try {
            ValidationUtil.validate(clientKey,moduleName,processName);
        } catch (IllegalArgumentException e) {
            log.warn("Ping is fail, clineKey={}, cause={}",clientKey,e.getMessage());
            throw e;
        }

        return new PingResponseTO(STATUS.SUCCESS,processName);
    }
}

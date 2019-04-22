package com.murk.telegram.ping.handler.core.service;

import com.murk.telegram.ping.handler.core.cache.PingCache;
import com.murk.telegram.ping.handler.core.exception.ClientNotFoundException;
import com.murk.telegram.ping.handler.core.exception.NotAuthorizedException;
import com.murk.telegram.ping.handler.core.model.ProcessInformation;
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

    @Autowired
    public PingServiceImpl(PingCache cache) {
        this.cache = cache;
    }

    @Override
    public PingResponseTO authorization(String clientKey, String moduleName, String processName, long checkTime) {
        PingResponseTO authorization  = new PingResponseTO(STATUS.SUCCESS,processName);
        try {
            ValidationUtil.validate(clientKey,moduleName,processName);

            if (cache.containsClient(clientKey)){
                ProcessInformation processInformation = cache.getProcessInformation(clientKey,moduleName,processName);
                if (processInformation == null || processInformation.getCheckTime() != checkTime)
                {
                    processInformation = new ProcessInformation(processName,checkTime,System.currentTimeMillis());
                    cache.putProcessInformation(clientKey,moduleName,processInformation);
                }
            } else
            {
                throw new ClientNotFoundException("Client not found");
            }
        } catch (RuntimeException e) {
            log.error("Authorization is fail, clineKey={}, cause={}",clientKey,e.getMessage());
            throw e;
        }

        return authorization;
    }

    @Override
    public PingResponseTO ping(String clientKey, String moduleName, String processName) {
        PingResponseTO ping = new PingResponseTO(STATUS.SUCCESS,processName);
        try {
            ValidationUtil.validate(clientKey,moduleName,processName);
            if (cache.containsProcess(clientKey,moduleName,processName))
            {
                cache.putPing(clientKey,moduleName,processName);
            }
            else
                {
                    throw new NotAuthorizedException("Not authorized ping for process"+ processName);
                }
        } catch (RuntimeException e) {
            log.error("Ping is fail, clineKey={}, cause={}",clientKey,e.getMessage());
            throw e;
        }

        return ping;
    }
}

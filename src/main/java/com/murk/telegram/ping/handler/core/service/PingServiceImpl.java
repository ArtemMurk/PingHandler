package com.murk.telegram.ping.handler.core.service;

import com.murk.telegram.ping.handler.core.model.Application;
import org.springframework.stereotype.Service;

@Service()
public class PingServiceImpl implements PingService {


    @Override
    public Application getException() {
        throw new IllegalArgumentException();
    }
}

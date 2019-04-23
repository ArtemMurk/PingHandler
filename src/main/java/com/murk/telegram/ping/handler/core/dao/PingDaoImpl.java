package com.murk.telegram.ping.handler.core.dao;

import com.murk.telegram.ping.handler.core.model.ClientInformation;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PingDaoImpl  implements PingDao{
    @Override
    public Map<String, ClientInformation> getAllClients() {
        return null;
    }
}

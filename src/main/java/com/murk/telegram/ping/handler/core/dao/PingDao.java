package com.murk.telegram.ping.handler.core.dao;

import com.murk.telegram.ping.handler.core.model.ClientInformation;
import com.murk.telegram.ping.handler.core.model.Ping;
import com.murk.telegram.ping.handler.core.model.ProcessInformation;

import java.util.Map;

public interface  PingDao {
    Map<String, ClientInformation> getAllClients();

    void saveProcess(String clientName, String moduleName, ProcessInformation process);

    void savePing(Ping ping);
}

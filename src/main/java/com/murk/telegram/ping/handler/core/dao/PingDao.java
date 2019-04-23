package com.murk.telegram.ping.handler.core.dao;

import com.murk.telegram.ping.handler.core.model.ClientInformation;

import java.util.Map;

public interface  PingDao {
    Map<String, ClientInformation> getAllClients();

}

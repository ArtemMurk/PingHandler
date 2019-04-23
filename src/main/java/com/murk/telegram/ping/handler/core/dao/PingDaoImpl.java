package com.murk.telegram.ping.handler.core.dao;

import com.murk.telegram.ping.handler.core.model.ClientInformation;
import com.murk.telegram.ping.handler.core.model.ModuleInformation;
import com.murk.telegram.ping.handler.core.model.Ping;
import com.murk.telegram.ping.handler.core.model.ProcessInformation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PingDaoImpl  implements PingDao{

    private Map<String,ClientInformation> clientCache = new ConcurrentHashMap<>();
    private Map<Ping,Long> pingCache = new ConcurrentHashMap<>();

    @Override
    public Map<String, ClientInformation> getAllClients() {
        return null;
    }

    @Override
    public void saveProcess(String clientName, String moduleName, ProcessInformation process)
    {
        ClientInformation client = clientCache.getOrDefault(clientName, new ClientInformation(clientName));
        ModuleInformation module = client.getModule(moduleName);
        if (module == null)
        {
            module = new ModuleInformation(moduleName);
        }

        module.setProcess(process);
        client.setModule(module);
        clientCache.put(clientName,client);
    }

    @Override
    public void savePing(Ping ping)
    {
        long pingTime = System.currentTimeMillis();
        pingCache.put(ping,pingTime);
    }

    private class SaveDaoProcess implements Runnable
    {
        @Override
        public void run() {

        }
    }
}

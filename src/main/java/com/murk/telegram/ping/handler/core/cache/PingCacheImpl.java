package com.murk.telegram.ping.handler.core.cache;

import com.murk.telegram.ping.handler.core.dao.PingDao;
import com.murk.telegram.ping.handler.core.exception.ClientNotFoundException;
import com.murk.telegram.ping.handler.core.exception.NotAuthorizedException;
import com.murk.telegram.ping.handler.core.model.ClientInformation;
import com.murk.telegram.ping.handler.core.model.ModuleInformation;
import com.murk.telegram.ping.handler.core.model.Ping;
import com.murk.telegram.ping.handler.core.model.ProcessInformation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class PingCacheImpl implements PingCache {


    private Map<String, ClientInformation> cache = new ConcurrentHashMap<>();

    private PingDao dao;

    @Autowired
    public PingCacheImpl(PingDao dao) {
        this.dao = dao;
    }

    @PostConstruct
    private void initCaches()
    {
        cache = dao.getAllClients();
        log.info("init clients caches success");
    }

    @Override
    public boolean containsClient(String clientId)
    {
        return cache.containsKey(clientId);
    }

    @Override
    public boolean containsProcess(String clientKey, String moduleName, String process)
    {
        boolean contains = false;
        ProcessInformation result = null;
        ClientInformation clientInformation = cache.get(clientKey);
        if (clientInformation != null) {
            Map<String, ModuleInformation> modules = clientInformation.getModules();
            ModuleInformation module = modules.get(moduleName);
            if (module != null) {
                contains= module.containsProcess(process);
            }
        }

        return contains;
    }

    @Override
    public ProcessInformation getProcessInformation(String clientKey, String moduleName, String processName)
    {
        ProcessInformation result = null;
        ClientInformation clientInformation = cache.get(clientKey);
        if (clientInformation != null) {
            Map<String, ModuleInformation> modules = clientInformation.getModules();
            ModuleInformation module = modules.get(moduleName);
            if (module != null) {
                result = module.getProcess(processName);
            }
        }

        return result;
    }

    @Override
    public void putProcessInformation(String clientKey, String moduleName, ProcessInformation process)
    {
        ClientInformation client = cache.get(clientKey);
        if (client != null) {
            ModuleInformation module = client.getModule(moduleName);
            if (module == null) {
                module = new ModuleInformation(moduleName);
            }
            module.setProcess(process);
            client.setModule(module);
        } else
            {
                log.error("Client not found  clientKey = {}, moduleName = {}, processName = {}",clientKey,moduleName,process.getName());
                throw new ClientNotFoundException("Client not found");
            }

        dao.saveProcess(clientKey,moduleName,process);
    }

    @Override
    public void putPing(String clientKey, String moduleName, String processName)
    {
        ProcessInformation process = getProcessInformation(clientKey,moduleName,processName);
        if (process!= null)
        {
            process.setLastPingTime(System.currentTimeMillis());
        } else
            {
                log.error("Not authorized ping for process = {}, clientKey = {}, moduleName = {}",processName,clientKey,moduleName);
                throw new NotAuthorizedException("Not authorized ping for process"+ processName);
            }

        Ping ping = new Ping(clientKey,moduleName,processName);
        dao.savePing(ping);
    }

}

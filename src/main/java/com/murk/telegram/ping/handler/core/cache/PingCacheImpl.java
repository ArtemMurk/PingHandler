package com.murk.telegram.ping.handler.core.cache;

import com.murk.telegram.ping.handler.core.dao.PingDao;
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


    private Map<String, ClientInformation> pingCache = new ConcurrentHashMap<>();

    private Map<String,ClientInformation> tmpModuleInfoCache = new ConcurrentHashMap<>();
    private Map<Ping,Long> tmpPingCache = new ConcurrentHashMap<>();

    private PingDao dao;

    @Autowired
    public PingCacheImpl(PingDao dao) {
        this.dao = dao;
    }

    @PostConstruct
    @Override
    public void initCaches()
    {
        Map<String,ClientInformation> test = dao.getAllClients();
        log.warn("test = {}",test);
    }

    @Override
    public boolean containsClient(String clientId)
    {
        return pingCache.containsKey(clientId);
    }

    @Override
    public boolean containsProcess(String clientKey, String moduleName, String process)
    {
        boolean contains = false;
        ProcessInformation result = null;
        ClientInformation clientInformation = pingCache.get(clientKey);
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
        ClientInformation clientInformation = pingCache.get(clientKey);
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
        ClientInformation client = pingCache.get(clientKey);
        if (client != null) {
            ModuleInformation module = client.getModule(moduleName);
            if (module == null) {
                module = new ModuleInformation(moduleName);
            }
            module.setProcess(process);
            client.setModule(module);
        }

        putInfoTmpDaoCache(clientKey,moduleName,process);
    }

    @Override
    public void putPing(String clientKey, String moduleName, String processName)
    {
        ProcessInformation process = getProcessInformation(clientKey,moduleName,processName);
        if (process!= null)
        {
            process.setLastPingTime(System.currentTimeMillis());
        }
        putPingTmpDaoCache(clientKey,moduleName,processName);

    }



    private void putInfoTmpDaoCache(String clientKey, String moduleName, ProcessInformation process)
    {
        ClientInformation client = tmpModuleInfoCache.getOrDefault(clientKey, new ClientInformation(clientKey));
        ModuleInformation module = client.getModule(moduleName);
        if (module == null)
        {
            module = new ModuleInformation(moduleName);
        }

        module.setProcess(process);
        client.setModule(module);
        tmpModuleInfoCache.put(clientKey,client);
    }

    private void putPingTmpDaoCache(String clientKey, String moduleName, String processName)
    {
        long pingTime = System.currentTimeMillis();
        tmpPingCache.put(new Ping(clientKey,moduleName,processName),pingTime);
    }


}

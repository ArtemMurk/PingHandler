package com.murk.telegram.ping.handler.core.service;

import com.murk.telegram.ping.handler.core.dao.PingDao;
import com.murk.telegram.ping.handler.core.exception.NotFindModuleException;
import com.murk.telegram.ping.handler.core.model.Project;
import com.murk.telegram.ping.handler.core.to.PingTO;
import com.murk.telegram.ping.handler.core.to.STATUS;
import com.murk.telegram.ping.handler.core.utils.ValidationUtil;
import com.murk.telegram.ping.handler.core.utils.WeakConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;


@Service
@Slf4j
public class PingServiceImpl implements PingService {

    private static final PingTO PING_SUCCESS  = new PingTO(STATUS.SUCCESS,"success ping");

    private PingDao dao;
    private Map<String, Project> cache;

    @Autowired
    public PingServiceImpl(PingDao dao, WeakConcurrentHashMap<String,Project> cache)
    {
        this.dao = dao;
        this.cache = cache;
    }

    @PostConstruct
    private void initCaches()
    {
        Map<String,Project> allProjects = dao.getAllProjects();
        if (allProjects!= null)
        {
            cache.putAll(allProjects);
        }
    }


    @Override
    public PingTO ping(String projectName, String moduleKey) {
        ValidationUtil.validate(projectName,moduleKey);

        if (cacheContains(projectName,moduleKey))
        {
            dao.ping(projectName,moduleKey);
        } else
            {
                throw  new NotFindModuleException("Not find module for "+projectName);
            }

        log.info("Ping project{}, mkey={}",projectName,moduleKey);
        return PING_SUCCESS;
    }

    private boolean cacheContains(String projectName, String moduleKey) {
        boolean moduleExist = false;
        Project project = cache.get(projectName);
        if (project != null)
        {
            if (project.containsModule(moduleKey))
            {
                moduleExist = true;
            } else
                {
                    Project projectFromDao = dao.getProjInfo(projectName,moduleKey);
                    if (projectFromDao!= null)
                    {
                        moduleExist = true;
                        project.setModule(moduleKey);
                    }
                }
        } else
        {
            Project projectFromDao = dao.getProjInfo(projectName,moduleKey);
            if (projectFromDao!= null)
            {
                moduleExist = true;
                cache.put(projectName,projectFromDao);
            }
        }

        return moduleExist;
    }
}

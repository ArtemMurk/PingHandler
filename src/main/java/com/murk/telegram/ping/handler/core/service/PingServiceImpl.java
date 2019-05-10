package com.murk.telegram.ping.handler.core.service;

import com.murk.telegram.ping.handler.core.dao.PingDao;
import com.murk.telegram.ping.handler.core.exception.NotFindModuleException;
import com.murk.telegram.ping.handler.core.model.Project;
import com.murk.telegram.ping.handler.core.to.PingTO;
import com.murk.telegram.ping.handler.core.to.STATUS;
import com.murk.telegram.ping.handler.core.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
public class PingServiceImpl implements PingService {

    private PingDao dao;
    private Map<String, Project> projects = new ConcurrentHashMap<>();

    @Autowired
    public PingServiceImpl(PingDao dao) {
        this.dao = dao;
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
        return new PingTO(STATUS.SUCCESS,"success ping");
    }

    private boolean cacheContains(String projectName, String moduleKey) {
        boolean moduleExist = false;
        Project project = projects.get(projectName);
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
                projects.put(projectName,projectFromDao);
            }
        }

        return moduleExist;
    }
}

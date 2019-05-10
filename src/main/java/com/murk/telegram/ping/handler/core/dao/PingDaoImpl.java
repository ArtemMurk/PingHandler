package com.murk.telegram.ping.handler.core.dao;

import com.murk.telegram.ping.handler.core.model.Project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class PingDaoImpl  implements PingDao{

    @Override
    public Map<String, Project> getAllProjects() {
        // TODO: 25.04.2019
        return null;
    }

    @Override
    public Project getProjInfo(String projectName, String moduleKey) {
        // TODO: 10.05.2019
        return null;
    }

    @Override
    public void ping(String prjectName, String moduleKey) {
        // TODO: 10.05.2019
    }

}

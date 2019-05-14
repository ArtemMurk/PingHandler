package com.murk.telegram.ping.handler.core.dao;

import com.murk.telegram.ping.handler.core.model.Module;
import com.murk.telegram.ping.handler.core.model.Project;


import java.util.Map;

public interface  PingDao {
    Map<String, Project> getAllProjects();

    Project getProjInfo(String projectName, String moduleKey);

    void ping(Module module);

}

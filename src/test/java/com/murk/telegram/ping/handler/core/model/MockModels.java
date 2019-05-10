package com.murk.telegram.ping.handler.core.model;

import java.util.HashMap;
import java.util.Map;

import static com.murk.telegram.ping.handler.core.model.RequestModelConstants.*;


public class MockModels
{
    public static final Project PROJECT_1;
    public static final Project PROJECT_2;

    public static final Map<String, Project> ALL_CLIENTS_INFO = new HashMap<>();
    static {


        PROJECT_1 = new Project(PROJECT_NAME_1);
        PROJECT_2 = new Project(PROJECT_NAME_2);

        PROJECT_1.setModule(MODULE_KEY_1);
        PROJECT_2.setModule(MODULE_KEY_2);


        ALL_CLIENTS_INFO.put(PROJECT_1.getName(), PROJECT_1);
        ALL_CLIENTS_INFO.put(PROJECT_2.getName(), PROJECT_2);

    }


}

package com.murk.telegram.ping.handler.core.model;

import java.util.HashMap;
import java.util.Map;

import static com.murk.telegram.ping.handler.core.model.RequestModelConstants.*;


public class MockModels
{
    public static final long LAST_PING_TIME = 1234;
    public static final ClientInformation CLIENT_1;
    public static final ClientInformation CLIENT_2;
    public static final ModuleInformation MODULE_1;
    public static final ProcessInformation PROCESS_1;
    public static final ModuleInformation MODULE_2;
    public static final ProcessInformation PROCESS_2;

    public static final Ping PING_1;

    public static final Map<String,ClientInformation> ALL_CLIENTS_INFO = new HashMap<>();
    static {
        PROCESS_1 = new ProcessInformation(PROCESS_NAME_1,CHECK_TIME,LAST_PING_TIME);
        PROCESS_2 = new ProcessInformation(PROCESS_NAME_2,CHECK_TIME,LAST_PING_TIME);

        MODULE_1 = new ModuleInformation(MODULE_NAME_1);
        MODULE_2 = new ModuleInformation(MODULE_NAME_2);

        MODULE_1.setProcess(PROCESS_1);

        MODULE_2.setProcess(PROCESS_2);

        CLIENT_1 = new ClientInformation(CLIENT_KEY_1);
        CLIENT_2 = new ClientInformation(CLIENT_KEY_2);

        CLIENT_1.setModule(MODULE_1);
        CLIENT_2.setModule(MODULE_2);


        ALL_CLIENTS_INFO.put(CLIENT_1.getName(),CLIENT_1);
        ALL_CLIENTS_INFO.put(CLIENT_2.getName(),CLIENT_2);

        PING_1 = new Ping(CLIENT_KEY_1,MODULE_NAME_1,PROCESS_NAME_1);
    }


}

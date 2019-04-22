package com.murk.telegram.ping.handler.core.model;

import static com.murk.telegram.ping.handler.core.model.RequestModelConstants.*;


public class MockModels
{
    public static final long LAST_PING_TIME = 1234;
    public static final ClientInformation CLIENT;
    public static final ModuleInformation MODULE;
    public static final ProcessInformation PROCESS;

    static {
        PROCESS = new ProcessInformation(PROCESS_NAME,CHECK_TIME,LAST_PING_TIME);
        MODULE = new ModuleInformation(MODULE_NAME);
        MODULE.setProcess(PROCESS);
        CLIENT = new ClientInformation(CLIENT_KEY);
        CLIENT.setModule(MODULE);
    }
}

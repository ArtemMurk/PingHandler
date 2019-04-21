package com.murk.telegram.ping.handler.core.controller;

public class ControllerTestHelper {

    static final String CLIENT_KEY = "client id mock";
    static final String MODULE_NAME = "module name mock";
    static final String PROCESS_NAME = "process name mock";
    static final long CHECK_TIME = 30*60*1000;

    static final String EXCEPTION_MESSAGE = "Exception message mock";

    static final String AUTHORIZATION_URI_TEMPLATE = "/rest/authorization?" +
            "clientKey="+ CLIENT_KEY +"&"+
            "moduleName="+ MODULE_NAME +"&"+
            "processName="+ PROCESS_NAME+"&"+
            "checkTime="+ CHECK_TIME;

    static final String PING_URI_TEMPLATE = "/rest/ping?" +
            "clientKey="+ CLIENT_KEY +"&"+
            "moduleName="+ MODULE_NAME +"&"+
            "processName="+ PROCESS_NAME;

}

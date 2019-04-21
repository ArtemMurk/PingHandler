package com.murk.telegram.ping.handler.core.controller;

import static com.murk.telegram.ping.handler.core.model.RequestModelConstants.*;

class ControllerTestHelper {

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

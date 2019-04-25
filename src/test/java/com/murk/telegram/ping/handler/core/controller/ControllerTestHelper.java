package com.murk.telegram.ping.handler.core.controller;

import static com.murk.telegram.ping.handler.core.model.RequestModelConstants.*;

class ControllerTestHelper {

    static final String AUTHORIZATION_URI_TEMPLATE = "/rest/authorization?" +
            "clientKey="+ CLIENT_KEY_1 +"&"+
            "moduleName="+ MODULE_NAME_1 +"&"+
            "processName="+ PROCESS_NAME_1 +"&"+
            "checkTime="+ CHECK_TIME;

    static final String PING_URI_TEMPLATE = "/rest/ping?" +
            "clientKey="+ CLIENT_KEY_1 +"&"+
            "moduleName="+ MODULE_NAME_1 +"&"+
            "processName="+ PROCESS_NAME_1;

}

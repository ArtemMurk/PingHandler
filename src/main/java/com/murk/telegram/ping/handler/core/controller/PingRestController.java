package com.murk.telegram.ping.handler.core.controller;

import com.murk.telegram.ping.handler.core.service.PingService;
import com.murk.telegram.ping.handler.core.to.AuthorizationTO;
import com.murk.telegram.ping.handler.core.to.PingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PingRestController {
    private PingService service;

    @Autowired
    public PingRestController(PingService service) {
        this.service = service;
    }

    @RequestMapping(value = "/rest/authorization/", method = RequestMethod.POST)
    public ResponseEntity<AuthorizationTO> authorization(@RequestParam("clientKey") String clientKey, @RequestParam("moduleName") String moduleName, @RequestParam("processName") String processName )
    {
        AuthorizationTO authorizationResponse = service.authorization(clientKey,moduleName,processName);
        return new ResponseEntity<>(authorizationResponse, HttpStatus.OK);
    }


    @RequestMapping(value = "/rest/ping/", method = RequestMethod.POST)
    public ResponseEntity<PingTO> ping(@RequestParam("clientKey") String clientKey, @RequestParam("moduleName") String moduleName, @RequestParam("processName") String processName )
    {
        PingTO pingResponse = service.ping(clientKey,moduleName,processName);
        return new ResponseEntity<>(pingResponse, HttpStatus.OK);
    }

}

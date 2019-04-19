package com.murk.telegram.ping.handler.core.controller;

import com.murk.telegram.ping.handler.core.model.Application;
import com.murk.telegram.ping.handler.core.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PingRestController {
    private PingService service;

    @Autowired
    public PingRestController(PingService service) {
        this.service = service;
    }

    @RequestMapping(value = "/rest/authorization/", method = RequestMethod.GET)
    public ResponseEntity<Application> authorization()
    {
        return new ResponseEntity<>(new Application(2), HttpStatus.OK);
    }


    @RequestMapping(value = "/rest/authorization2/", method = RequestMethod.GET)
    public Application authorization2()
    {
        return new Application(2);
    }


    @RequestMapping(value = "/getException", method = RequestMethod.GET)
    public Application getException()
    {
        return service.getException();
    }



    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

}

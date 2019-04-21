package com.murk.telegram.ping.handler.core.controller;

import com.murk.telegram.ping.handler.core.service.PingService;
import com.murk.telegram.ping.handler.core.to.PingResponseTO;
import com.murk.telegram.ping.handler.core.to.STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


@RestController
public class PingRestController {
    private PingService service;

    @Autowired
    public PingRestController(PingService service) {
        this.service = service;
    }

    @RequestMapping(value = "/rest/authorization", method = RequestMethod.POST)
    public ResponseEntity<PingResponseTO> authorization(@RequestParam("clientKey") String clientKey, @RequestParam("moduleName") String moduleName, @RequestParam("processName") String processName )
    {
        PingResponseTO authorizationResponse = service.authorization(clientKey,moduleName,processName);
        return new ResponseEntity<>(authorizationResponse, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/rest/ping", method = RequestMethod.POST)
    public ResponseEntity<PingResponseTO> ping(@RequestParam("clientKey") String clientKey, @RequestParam("moduleName") String moduleName, @RequestParam("processName") String processName )
    {
        PingResponseTO pingResponse = service.ping(clientKey,moduleName,processName);
        return new ResponseEntity<>(pingResponse, HttpStatus.OK);
    }

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<PingResponseTO> handleValidation(
            RuntimeException ex, WebRequest request) {

        PingResponseTO failedResponse = new PingResponseTO(STATUS.FAIL,ex.getMessage());
        return new ResponseEntity<>( failedResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value
            = { Exception.class })
    protected ResponseEntity<PingResponseTO> handleErrors(
            RuntimeException ex, WebRequest request) {

        PingResponseTO failedResponse = new PingResponseTO(STATUS.ERROR,ex.getMessage());
        return new ResponseEntity<>( failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

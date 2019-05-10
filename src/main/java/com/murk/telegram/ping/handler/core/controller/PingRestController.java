package com.murk.telegram.ping.handler.core.controller;

import com.murk.telegram.ping.handler.core.exception.NotFindModuleException;
import com.murk.telegram.ping.handler.core.service.PingService;
import com.murk.telegram.ping.handler.core.to.PingTO;
import com.murk.telegram.ping.handler.core.to.STATUS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


@RestController
@Slf4j
public class PingRestController {
    private PingService service;

    @Autowired
    public PingRestController(PingService service) {
        this.service = service;
    }



    @RequestMapping(value = "/rest/ping", method = RequestMethod.POST)
    public ResponseEntity<PingTO> ping(@RequestParam("project") String projectName,
                                       @RequestParam("mkey") String moduleKey )
    {
        PingTO pingResponse = service.ping(projectName,moduleKey);
        return new ResponseEntity<>(pingResponse, HttpStatus.OK);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<PingTO> handleValidation(RuntimeException ex, WebRequest request) {

        String messageError = ex.getMessage();
        log.warn(messageError);
        PingTO failedResponse = new PingTO(STATUS.ERROR,messageError);
        return new ResponseEntity<>( failedResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<PingTO> handleErrors(RuntimeException ex, WebRequest request)
    {
        String messageError = ex.getMessage();
        log.error(messageError);
        PingTO failedResponse = new PingTO(STATUS.ERROR,messageError);
        return new ResponseEntity<>( failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = { NotFindModuleException.class })
    protected ResponseEntity<PingTO> handleUnauthorized(NotFindModuleException ex, WebRequest request)
    {
        String messageError = ex.getMessage();
        log.warn(messageError);
        PingTO failedResponse = new PingTO(STATUS.ERROR,messageError);
        return new ResponseEntity<>( failedResponse, HttpStatus.UNAUTHORIZED);
    }

}

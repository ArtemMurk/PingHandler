package com.murk.telegram.ping.handler.core.utils;


import com.murk.telegram.ping.handler.core.to.PingResponseTO;
import com.murk.telegram.ping.handler.core.to.STATUS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PingResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<PingResponseTO> handleConflict(
            RuntimeException ex, WebRequest request) {

        PingResponseTO failedResponse = new PingResponseTO(STATUS.FAIL,ex.getMessage());
        return new ResponseEntity<>( failedResponse, HttpStatus.FORBIDDEN);
    }
}
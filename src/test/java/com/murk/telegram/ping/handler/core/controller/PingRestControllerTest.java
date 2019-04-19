package com.murk.telegram.ping.handler.core.controller;

import com.murk.telegram.ping.handler.core.service.PingService;
import com.murk.telegram.ping.handler.core.to.PingResponseTO;
import com.murk.telegram.ping.handler.core.to.STATUS;

import org.junit.Before;
import org.junit.Test;

import org.mockito.MockitoAnnotations;


import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PingRestControllerTest {


    private MockMvc mockMvc;

    private PingService service;
    private PingRestController controller;

    @Before
    public void setUp() throws Exception {
        service = mock(PingService.class);
        controller = new PingRestController(service);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void contexLoads(){
        assertThat(service).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(controller).isNotNull();
    }

    @Test
    public void authorizationSuccess() throws Exception{

        String clientKey = "client id mock";
        String moduleName = "module name mock";
        String processName = "process name mock";

        PingResponseTO pingResponseTO = new PingResponseTO(STATUS.SUCCESS,processName);

        when(service.authorization(clientKey,moduleName,processName)).thenReturn(pingResponseTO);

         mockMvc.perform(post("/rest/authorization?clientKey="+clientKey+"&moduleName="+moduleName+"&"+"processName="+processName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(STATUS.SUCCESS.name())))
                .andExpect(jsonPath("message", is(processName)));

        verify(service, times(1)).authorization(clientKey,moduleName,processName);
        verifyNoMoreInteractions(service);
    }


    @Test
    public void pingSuccess() throws Exception {
        String clientKey = "client id mock";
        String moduleName = "module name mock";
        String processName = "process name mock";

        PingResponseTO pingResponseTO = new PingResponseTO(STATUS.SUCCESS,processName);

        when(service.ping(clientKey,moduleName,processName)).thenReturn(pingResponseTO);

        mockMvc.perform(post("/rest/ping?clientKey="+clientKey+"&moduleName="+moduleName+"&"+"processName="+processName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(STATUS.SUCCESS.name())))
                .andExpect(jsonPath("message", is(processName)));

        verify(service, times(1)).ping(clientKey,moduleName,processName);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void pingFail() throws Exception
    {
        String clientKey = "client id mock";
        String moduleName = "module name mock";
        String processName = "process name mock";

        String exceptionMessage = "Some Dumb Runtime Exception";


        when(service.ping(clientKey,moduleName,processName)).thenThrow(new IllegalArgumentException(exceptionMessage));

        mockMvc.perform(post("/rest/ping?clientKey="+clientKey+"&moduleName="+moduleName+"&"+"processName="+processName))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("status", is(STATUS.FAIL.name())))
                .andExpect(jsonPath("message", is(exceptionMessage)));

        verify(service, times(1)).ping(clientKey,moduleName,processName);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void authorizationFail() throws Exception
    {
        String clientKey = "client id mock";
        String moduleName = "module name mock";
        String processName = "process name mock";

        String exceptionMessage = "Some Dumb Runtime Exception";


        when(service.authorization(clientKey,moduleName,processName)).thenThrow(new IllegalArgumentException(exceptionMessage));

        mockMvc.perform(post("/rest/authorization?clientKey="+clientKey+"&moduleName="+moduleName+"&"+"processName="+processName))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("status", is(STATUS.FAIL.name())))
                .andExpect(jsonPath("message", is(exceptionMessage)));

        verify(service, times(1)).authorization(clientKey,moduleName,processName);
        verifyNoMoreInteractions(service);
    }

}
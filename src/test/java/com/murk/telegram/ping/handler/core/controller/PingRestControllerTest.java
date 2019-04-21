package com.murk.telegram.ping.handler.core.controller;

import com.murk.telegram.ping.handler.core.exception.NotAuthorizedException;
import com.murk.telegram.ping.handler.core.service.PingService;
import com.murk.telegram.ping.handler.core.to.PingResponseTO;
import com.murk.telegram.ping.handler.core.to.STATUS;

import org.junit.Before;
import org.junit.Test;

import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.murk.telegram.ping.handler.core.model.RequestModelConstants.*;
import static com.murk.telegram.ping.handler.core.controller.ControllerTestHelper.*;
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
    public void setUp(){
        service = mock(PingService.class);
        controller = new PingRestController(service);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void contextLoad(){
        assertThat(service).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(controller).isNotNull();
    }

    @Test
    public void authorizationSuccess() throws Exception{


        PingResponseTO pingResponseTO = new PingResponseTO(STATUS.SUCCESS,PROCESS_NAME);

        when(service.authorization(CLIENT_KEY,MODULE_NAME,PROCESS_NAME,CHECK_TIME)).thenReturn(pingResponseTO);

         mockMvc.perform(post(AUTHORIZATION_URI_TEMPLATE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("status", is(STATUS.SUCCESS.name())))
                .andExpect(jsonPath("message", is(PROCESS_NAME)));

        verify(service, times(1)).authorization(CLIENT_KEY,MODULE_NAME,PROCESS_NAME,CHECK_TIME);
        verifyNoMoreInteractions(service);
    }


    @Test
    public void pingSuccess() throws Exception {

        PingResponseTO pingResponseTO = new PingResponseTO(STATUS.SUCCESS,PROCESS_NAME);

        when(service.ping(CLIENT_KEY,MODULE_NAME,PROCESS_NAME)).thenReturn(pingResponseTO);

        mockMvc.perform(post(PING_URI_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(STATUS.SUCCESS.name())))
                .andExpect(jsonPath("message", is(PROCESS_NAME)));

        verify(service, times(1)).ping(CLIENT_KEY,MODULE_NAME,PROCESS_NAME);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void pingFail() throws Exception
    {

        when(service.ping(CLIENT_KEY,MODULE_NAME,PROCESS_NAME)).thenThrow(new IllegalArgumentException(EXCEPTION_MESSAGE));

        mockMvc.perform(post(PING_URI_TEMPLATE))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("status", is(STATUS.FAIL.name())))
                .andExpect(jsonPath("message", is(EXCEPTION_MESSAGE)));

        verify(service, times(1)).ping(CLIENT_KEY,MODULE_NAME,PROCESS_NAME);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void authorizationFail() throws Exception
    {

        when(service.authorization(CLIENT_KEY,MODULE_NAME,PROCESS_NAME,CHECK_TIME)).thenThrow(new IllegalArgumentException(EXCEPTION_MESSAGE));

        mockMvc.perform(post(AUTHORIZATION_URI_TEMPLATE))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("status", is(STATUS.FAIL.name())))
                .andExpect(jsonPath("message", is(EXCEPTION_MESSAGE)));

        verify(service, times(1)).authorization(CLIENT_KEY,MODULE_NAME,PROCESS_NAME,CHECK_TIME);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void internalServerErrors() throws Exception
    {

        when(service.authorization(CLIENT_KEY,MODULE_NAME,PROCESS_NAME, CHECK_TIME)).thenThrow(new RuntimeException(EXCEPTION_MESSAGE));

        mockMvc.perform(post(AUTHORIZATION_URI_TEMPLATE))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("status", is(STATUS.ERROR.name())))
                .andExpect(jsonPath("message", is(EXCEPTION_MESSAGE)));

        verify(service, times(1)).authorization(CLIENT_KEY,MODULE_NAME,PROCESS_NAME, CHECK_TIME);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void notAuthorizedModule() throws Exception
    {

        when(service.ping(CLIENT_KEY, MODULE_NAME, PROCESS_NAME)).thenThrow(new NotAuthorizedException(EXCEPTION_MESSAGE));

        mockMvc.perform(post(PING_URI_TEMPLATE))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("status", is(STATUS.FAIL.name())))
                .andExpect(jsonPath("message", is(EXCEPTION_MESSAGE)));

        verify(service, times(1)).ping(CLIENT_KEY, MODULE_NAME, PROCESS_NAME);
        verifyNoMoreInteractions(service);
    }

}
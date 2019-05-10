package com.murk.telegram.ping.handler.core.controller;

import com.murk.telegram.ping.handler.core.exception.NotFindModuleException;
import com.murk.telegram.ping.handler.core.service.PingService;
import com.murk.telegram.ping.handler.core.to.PingTO;
import com.murk.telegram.ping.handler.core.to.STATUS;

import org.junit.Before;
import org.junit.Test;

import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static mocks.model.MockModels.*;
import static mocks.model.ControllerTestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;
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
    public void pingSuccess() throws Exception {

        PingTO pingTO = new PingTO(STATUS.SUCCESS, PROJECT_NAME_1);

        when(service.ping(PROJECT_NAME_1, MODULE_KEY_1)).thenReturn(pingTO);

        mockMvc.perform(post(PING_URI_TEMPLATE_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(STATUS.SUCCESS.name())))
                .andExpect(jsonPath("message", is(PROJECT_NAME_1)));

        verify(service, times(1)).ping(PROJECT_NAME_1, MODULE_KEY_1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void pingNotValid() throws Exception
    {

        when(service.ping(PROJECT_NAME_1, MODULE_KEY_1))
                .thenThrow(new IllegalArgumentException(EXCEPTION_MESSAGE));

        mockMvc.perform(post(PING_URI_TEMPLATE_1))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("status", is(STATUS.FAIL.name())))
                .andExpect(jsonPath("message", is(EXCEPTION_MESSAGE)));

        verify(service, times(1)).ping(PROJECT_NAME_1, MODULE_KEY_1);
        verifyNoMoreInteractions(service);
    }


    @Test
    public void internalServerErrors() throws Exception
    {

        when(service.ping(PROJECT_NAME_1, MODULE_KEY_1)).thenThrow(new RuntimeException(EXCEPTION_MESSAGE));

        mockMvc.perform(post(PING_URI_TEMPLATE_1))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("status", is(STATUS.ERROR.name())))
                .andExpect(jsonPath("message", is(EXCEPTION_MESSAGE)));

        verify(service, times(1)).ping(PROJECT_NAME_1, MODULE_KEY_1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void notFindModule() throws Exception
    {

        when(service.ping(PROJECT_NAME_1, MODULE_KEY_1))
                .thenThrow(new NotFindModuleException(EXCEPTION_MESSAGE));

        mockMvc.perform(post(PING_URI_TEMPLATE_1))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("status", is(STATUS.FAIL.name())))
                .andExpect(jsonPath("message", is(EXCEPTION_MESSAGE)));

        verify(service, times(1)).ping(PROJECT_NAME_1, MODULE_KEY_1);
        verifyNoMoreInteractions(service);
    }

}
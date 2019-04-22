package com.murk.telegram.ping.handler.core.service;

import com.murk.telegram.ping.handler.core.cache.PingCache;
import com.murk.telegram.ping.handler.core.exception.ClientNotFoundException;
import com.murk.telegram.ping.handler.core.exception.NotAuthorizedException;
import com.murk.telegram.ping.handler.core.model.ClientInformation;
import com.murk.telegram.ping.handler.core.model.MockModels;
import com.murk.telegram.ping.handler.core.model.ProcessInformation;
import com.murk.telegram.ping.handler.core.to.PingResponseTO;
import com.murk.telegram.ping.handler.core.to.STATUS;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.murk.telegram.ping.handler.core.model.MockModels.PROCESS;
import static com.murk.telegram.ping.handler.core.model.RequestModelConstants.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class PingServiceTest {

    @Spy
    private PingCache cache;

    @Autowired
    @InjectMocks

    private PingService service;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void contextLoad(){
        assertThat(service).isNotNull();
        assertThat(cache).isNotNull();
    }

    @Test
    public void authorizationSuccess()
    {
        PingResponseTO expectedAuthorizationResponse = new PingResponseTO(STATUS.SUCCESS,PROCESS_NAME);
        when(cache.containsClient(CLIENT_KEY)).thenReturn(true);
        when(cache.getProcessInformation(CLIENT_KEY,MODULE_NAME,PROCESS_NAME)).thenReturn(PROCESS);

        PingResponseTO actualAuthorizationResponse = service.authorization(CLIENT_KEY,MODULE_NAME,PROCESS_NAME,CHECK_TIME);
        assertThat(expectedAuthorizationResponse).isEqualToComparingFieldByField(actualAuthorizationResponse);

        verify(cache, times(1)).containsClient(CLIENT_KEY);
        verify(cache, times(1)).getProcessInformation(CLIENT_KEY,MODULE_NAME,PROCESS_NAME);
        verifyNoMoreInteractions(cache);
    }

    @Test
    public void pingSucess()
    {
        PingResponseTO expectedPingResponse = new PingResponseTO(STATUS.SUCCESS,PROCESS_NAME);
        when(cache.containsProcess(CLIENT_KEY,MODULE_NAME,PROCESS_NAME)).thenReturn(true);

        PingResponseTO actualPingResponse = service.ping(CLIENT_KEY,MODULE_NAME,PROCESS_NAME);
        assertThat(expectedPingResponse).isEqualToComparingFieldByField(actualPingResponse);

        verify(cache, times(1)).containsProcess(CLIENT_KEY,MODULE_NAME,PROCESS_NAME);
        verify(cache, times(1)).putPing(CLIENT_KEY,MODULE_NAME,PROCESS_NAME);
        verifyNoMoreInteractions(cache);
    }



    @Test(expected = ClientNotFoundException.class)
    public void clientNotFound()
    {
        when(cache.containsProcess(CLIENT_KEY,MODULE_NAME,PROCESS_NAME)).thenReturn(false);
        service.authorization(CLIENT_KEY,MODULE_NAME,PROCESS_NAME,CHECK_TIME);
    }

    @Test(expected = NotAuthorizedException.class)
    public void notAutorizedProcess()
    {
        when(cache.containsProcess(CLIENT_KEY,MODULE_NAME,PROCESS_NAME)).thenReturn(false);
        service.authorization(CLIENT_KEY,MODULE_NAME,PROCESS_NAME,CHECK_TIME);
    }


    @Test(expected = RuntimeException.class)
    public void authorizationFail()
    {
        when(cache.containsClient(CLIENT_KEY)).thenReturn(true);
        doThrow().when(cache).putProcessInformation(CLIENT_KEY,MODULE_NAME,PROCESS);
        service.authorization(CLIENT_KEY,MODULE_NAME,PROCESS_NAME,CHECK_TIME);
    }

    @Test(expected = RuntimeException.class)
    public void pingFail()
    {
        when(cache.containsProcess(CLIENT_KEY,MODULE_NAME,PROCESS_NAME)).thenReturn(true);
        doThrow().when(cache).putPing(CLIENT_KEY,MODULE_NAME,PROCESS_NAME);
        service.ping(CLIENT_KEY,MODULE_NAME,PROCESS_NAME);
    }
}
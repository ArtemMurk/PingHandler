package com.murk.telegram.ping.handler.core.cache;

import com.murk.telegram.ping.handler.core.dao.PingDao;
import com.murk.telegram.ping.handler.core.exception.ClientNotFoundException;
import com.murk.telegram.ping.handler.core.exception.NotAuthorizedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import static com.murk.telegram.ping.handler.core.model.MockModels.*;
import static com.murk.telegram.ping.handler.core.model.RequestModelConstants.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class PingCacheImplTest {


    @Spy
    private PingDao dao;

    @Autowired
    @InjectMocks
    private PingCache cache;

    @Before
    public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        MockitoAnnotations.initMocks(this);
        when(dao.getAllClients()).thenReturn(ALL_CLIENTS_INFO);

        Method postConstruct =  cache.getClass().getDeclaredMethod("initCaches",null);
        postConstruct.setAccessible(true);
        postConstruct.invoke(cache);

    }

    @Test
    public void cachesInitOneTime(){
        verify(dao, times(1)).getAllClients();
        verifyNoMoreInteractions(dao);
    }

    @Test
    public void contextLoad(){
        assertThat(cache).isNotNull();
        assertThat(dao).isNotNull();
    }

    @Test
    public void containsClientSuccess()
    {
        assertTrue(cache.containsClient(CLIENT_1.getName()));
    }

    @Test
    public void containsProcess()
    {
        assertTrue(cache.containsProcess(CLIENT_KEY_1,MODULE_NAME_1,PROCESS_NAME_1));
    }

    @Test
    public void getProcessInformationSuccess()
    {
        assertThat(cache.getProcessInformation(CLIENT_KEY_1,MODULE_NAME_1, PROCESS_NAME_1))
                .isEqualToComparingFieldByField(PROCESS_1);
    }

    @Test
    public void getProcessInformationFailClient()
    {
        assertThat(cache.getProcessInformation("fail Client",MODULE_NAME_1, PROCESS_NAME_1))
                .isNull();
    }

    @Test
    public void getProcessInformationFailModule()
    {
        assertThat(cache.getProcessInformation(CLIENT_KEY_1,"fail module", PROCESS_NAME_1))
                .isNull();
    }

    @Test
    public void getProcessInformationFailProcess()
    {
        assertThat(cache.getProcessInformation(CLIENT_KEY_1,MODULE_NAME_1, "fail process"))
                .isNull();
    }

    @Test
    public void putProcessInformationSuccess()
    {
        cache.putProcessInformation(CLIENT_KEY_1,MODULE_NAME_1,PROCESS_1);
        verify(dao, times(1)).saveProcess(CLIENT_KEY_1,MODULE_NAME_1,PROCESS_1);
    }

    @Test(expected = ClientNotFoundException.class)
    public void putProcessInformationFail()
    {
        cache.putProcessInformation("fake client",MODULE_NAME_1,PROCESS_1);
    }

    @Test
    public void putPingSuccess()
    {
        cache.putPing(CLIENT_KEY_1,MODULE_NAME_1,PROCESS_NAME_1);
        verify(dao, times(1)).savePing(PING_1);
    }

    @Test (expected = NotAuthorizedException.class)
    public void putPingFail()
    {
        cache.putPing("fake client",MODULE_NAME_1,PROCESS_NAME_1);
    }
}
package com.murk.telegram.ping.handler.core.service;

import com.murk.telegram.ping.handler.core.dao.PingDao;
import com.murk.telegram.ping.handler.core.exception.NotFindModuleException;
import com.murk.telegram.ping.handler.core.model.Module;
import com.murk.telegram.ping.handler.core.model.Project;
import com.murk.telegram.ping.handler.core.to.PingTO;
import com.murk.telegram.ping.handler.core.to.STATUS;
import com.murk.telegram.ping.handler.core.utils.WeakConcurrentHashMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static mocks.model.MockModels.ALL_PROJECTS_INFO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static mocks.model.MockModels.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/service-spring-config.xml")
public class PingServiceTest {

    @Mock
    private PingDao dao;

    @Mock
    private WeakConcurrentHashMap<String,Project> cache;

    @InjectMocks
    @Autowired
    private PingService service;

    @Before
    public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {


        MockitoAnnotations.initMocks(this);

        when(dao.getAllProjects()).thenReturn(ALL_PROJECTS_INFO);

        Method postConstruct =  service.getClass().getDeclaredMethod("initCaches",null);
        postConstruct.setAccessible(true);
        postConstruct.invoke(service);


    }

    @Test
    public void contextLoad(){
        assertThat(service).isNotNull();
        assertThat(cache).isNotNull();
        assertThat(dao).isNotNull();
    }

    @Test
    public void pingSucessProjectInCache()
    {
        PingTO expectedPingResponse = new PingTO(STATUS.SUCCESS, "success ping");

        when(cache.get(PROJECT_NAME_1)).thenReturn(PROJECT_1);

        PingTO actualPingResponse = service.ping(PROJECT_NAME_1, MODULE_KEY_1);
        assertThat(expectedPingResponse).isEqualToComparingFieldByField(actualPingResponse);

        verify(dao, times(1)).getAllProjects();
        verify(cache, times(1)).putAll(ALL_PROJECTS_INFO);
        verify(cache, times(1)).get(PROJECT_NAME_1);
        verify(dao, times(1)).ping(MODULE_1_PROJ_1);

        verifyNoMoreInteractions(cache);
        verifyNoMoreInteractions(dao);
    }

    @Test
    public void pingSucessNewProjectInDao()
    {

    PingTO expectedPingResponse = new PingTO(STATUS.SUCCESS, "success ping");

        Project newProjectInDao = new Project("project3");
        String newModuleInDaoKey = "54123456abcdef123456abcdef123456";
        Module newModuleInDao = new Module(newModuleInDaoKey);
        newProjectInDao.setModule(newModuleInDao);


        when(cache.get(newProjectInDao.getName())).thenReturn(null);
        when(dao.getProjInfo(newProjectInDao.getName(),newModuleInDaoKey)).thenReturn(newProjectInDao);

        PingTO actualPingResponse = service.ping(newProjectInDao.getName(),newModuleInDaoKey);
        assertThat(expectedPingResponse).isEqualToComparingFieldByField(actualPingResponse);


        verify(dao, times(1)).getAllProjects();
        verify(cache, times(1)).putAll(ALL_PROJECTS_INFO);

        verify(dao, times(1)).getProjInfo(newProjectInDao.getName(),newModuleInDaoKey);
        verify(cache, times(1)).get(newProjectInDao.getName());
        verify(cache, times(1)).put(newProjectInDao.getName(),newProjectInDao);
        verify(dao, times(1)).ping(newModuleInDao);

        verifyNoMoreInteractions(cache);
        verifyNoMoreInteractions(dao);


    }

//
//
//
    @Test()
    public void notFindModule()
    {
        Project notFindProj = new Project("project3");
        String notFindModuleKey = "54123456abcdef123456abcdef123456";
        Module notFindModule = new Module(notFindModuleKey);
        notFindProj.setModule(notFindModule);


        when(cache.get(notFindProj.getName())).thenReturn(null);
        when(dao.getProjInfo(notFindProj.getName(),notFindModuleKey)).thenReturn(null);

        try {
            PingTO actualPingResponse = service.ping(notFindProj.getName(),notFindModuleKey);
        } catch (NotFindModuleException e) {
            assertThat(e.getMessage().equals("Not find module for "+notFindProj.getName()));
        }


        verify(dao, times(1)).getAllProjects();
        verify(cache, times(1)).putAll(ALL_PROJECTS_INFO);

        verify(dao, times(1)).getProjInfo(notFindProj.getName(),notFindModuleKey);
        verify(cache, times(1)).get(notFindProj.getName());

        verifyNoMoreInteractions(cache);
        verifyNoMoreInteractions(dao);

    }
}
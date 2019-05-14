package com.murk.telegram.ping.handler.core.dao;

import com.murk.telegram.ping.handler.core.model.Module;
import com.murk.telegram.ping.handler.core.model.Project;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static mocks.model.MockModels.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = "file:src/test/resources/dao-spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PingDaoTest {


    @Autowired
    private BasicDataSource ds;

    @Autowired
    private PingDao pingDao;

    private static String pathToTruncate;
    private static String pathToInsert;

    @BeforeClass
    public static void initPaths()
    {
        URL pathToTruncateUrl = PingDaoTest.class.getClassLoader().getResource("truncate_db.sql");
        URL pathToInsertUrl = PingDaoTest.class.getClassLoader().getResource("mock_insert_db.sql");

        pathToTruncate = pathToTruncateUrl.getPath();
        pathToInsert = pathToInsertUrl.getPath();
    }

    @Before
    public void initDb()
    {
        try(Connection connection = ds.getConnection())
        {

            InputStream createTableStream  = new FileInputStream(pathToTruncate);
            DBHelper.importSQL(connection,createTableStream);

            InputStream insertMocksStream  = new FileInputStream(pathToInsert);
            DBHelper.importSQL(connection,insertMocksStream);


        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllProjectsSuccess()
    {
        Map<String, Project> projects = pingDao.getAllProjects();
        assertThat(projects).isEqualTo(ALL_PROJECTS_INFO);
    }

    @Test
    public void getProjInfoSuccess()
    {
        Project project = pingDao.getProjInfo(PROJECT_NAME_1,MODULE_KEY_1);

        assertThat(project).isEqualToComparingFieldByField(PROJECT_1);
    }

    @Test
    public void getProjInfoNotFound()
    {
        Project project = pingDao.getProjInfo("not Found proj","not found module");

        assertThat(project).isNull();
    }

    @Test
    public void pingSuccess()
    {
        long pingTime = System.currentTimeMillis();
        String moduleKey = MODULE_KEY_1;

        Module module = new Module(moduleKey);
        module.setPingTime(pingTime);

        pingDao.ping(module);

        Project project = pingDao.getProjInfo(PROJECT_NAME_1,MODULE_KEY_1);
        Module moduleFromDao  = project.getModule(moduleKey);

        assertThat(moduleFromDao).isEqualToComparingFieldByField(module);

    }
}
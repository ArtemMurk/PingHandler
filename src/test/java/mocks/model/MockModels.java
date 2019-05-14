package mocks.model;

import com.murk.telegram.ping.handler.core.model.Module;
import com.murk.telegram.ping.handler.core.model.Project;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MockModels
{
    public static final String PROJECT_NAME_1 = "ProjectMock1";
    public static final String MODULE_KEY_1 = "123456abcde123456abcde123456ab55";

    public static final String PROJECT_NAME_2= "ProjectMock2";
    public static final String MODULE_KEY_2 = "54123456abcdef123456abcdef123456";

    public static final String EXCEPTION_MESSAGE = "Exception message mock";

    public static final Project PROJECT_1;
    public static final Project PROJECT_2;

    public static final Module MODULE_1_PROJ_1;
    public static final Module MODULE_2_PROJ_2;


    public static final Map<String, Project> ALL_PROJECTS_INFO = new HashMap<>();
    static {


        PROJECT_1 = new Project(PROJECT_NAME_1);
        PROJECT_2 = new Project(PROJECT_NAME_2);
        PROJECT_1.setId(1);
        MODULE_1_PROJ_1 = new Module(MODULE_KEY_1);
        MODULE_2_PROJ_2 = new Module(MODULE_KEY_2);

        PROJECT_1.setModule(MODULE_1_PROJ_1);
        PROJECT_2.setModule(MODULE_2_PROJ_2);
        PROJECT_1.setId(1);
        PROJECT_2.setId(2);


        ALL_PROJECTS_INFO.put(PROJECT_1.getName(), PROJECT_1);
        ALL_PROJECTS_INFO.put(PROJECT_2.getName(), PROJECT_2);

    }


}

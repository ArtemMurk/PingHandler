package com.murk.telegram.ping.handler.core.dao;

import com.murk.telegram.ping.handler.core.model.Module;
import com.murk.telegram.ping.handler.core.model.Project;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
@Transactional(propagation = Propagation.REQUIRED,
                    rollbackFor = {ObjectNotFoundException.class,ConstraintViolationException.class})
public class  PingDaoImpl  implements PingDao{

    private SessionFactory sessionFactory;

    @Autowired
    public PingDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override

    public Map<String, Project> getAllProjects() {
        Map<String,Project> result = new ConcurrentHashMap<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Project> projects = session.createCriteria(Project.class).list();
            projects.forEach(project ->
            {
                result.put(project.getName(),project);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override

    public Project getProjInfo(String projectName, String moduleKey) {
        Project project = null;
        try
        {
            Session session = sessionFactory.getCurrentSession();
            String queryString = "select  a FROM Project a INNER JOIN a.modules as b WHERE  a.name=:prName AND b.key = :mKey";

            Query query = session.createQuery(queryString)
                    .setParameter("prName",projectName)
                    .setParameter("mKey",moduleKey);
            if (query.iterate().hasNext())
            {
                project = (Project) query.iterate().next();

            }

        } catch (Exception ex)
        {
            log.error(ex.getMessage());
        }

        return project;
    }

    @Override
    public void ping(Module module) {
        try {

            Session session = sessionFactory.getCurrentSession();
            session.update(module);
        }  catch (Exception ex)
        {
            log.error(ex.getMessage());
        }

    }

}

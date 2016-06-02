package com.app.server.repository.organization.contactmanagement;
import com.app.server.repository.core.SearchInterfaceImpl;
import com.app.shared.organization.contactmanagement.Title;
import org.springframework.stereotype.Repository;
import com.app.config.annotation.Complexity;
import com.app.config.annotation.SourceCodeAuthorClass;
import com.athena.server.pluggable.utils.helper.ResourceFactoryManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import com.spartan.pluggable.logger.alarms.AppAlarm;
import com.spartan.pluggable.logger.api.LogManagerFactory;
import com.athena.server.pluggable.utils.AppLoggerConstant;
import com.spartan.pluggable.logger.api.LogManager;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
import java.lang.Override;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Repository for Title Master table Entity", complexity = Complexity.LOW)
public class TitleRepositoryImpl extends SearchInterfaceImpl implements TitleRepository<Title> {

    @Autowired
    private ResourceFactoryManagerHelper emfResource;

    private LogManager Log = LogManagerFactory.getInstance(AppLoggerConstant.LOGGER_ID);

    @Autowired
    private RuntimeLogInfoHelper runtimeLogInfoHelper;

    @Override
    @Transactional
    public List<Title> findAll() throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        java.util.List<com.app.shared.organization.contactmanagement.Title> query = emanager.createQuery("select u from Title u where u.systemInfo.activeStatus=1").getResultList();
        Log.out.println("ORGCM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "TitleRepositoryImpl", "findAll", "Total Records Fetched = " + query.size());
        return query;
    }

    @Override
    @Transactional
    public Title save(Title entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        emanager.persist(entity);
        Log.out.println("ORGCM322990200", runtimeLogInfoHelper.getRequestHeaderBean(), "TitleRepositoryImpl", "save", entity);
        return entity;
    }

    @Override
    @Transactional
    public List<Title> save(List<Title> entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (int i = 0; i < entity.size(); i++) {
            com.app.shared.organization.contactmanagement.Title obj = entity.get(i);
            emanager.persist(obj);
        }
        Log.out.println("ORGCM322990200", runtimeLogInfoHelper.getRequestHeaderBean(), "TitleRepositoryImpl", "saveAll", "Total Records saved = " + entity.size());
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        com.app.shared.organization.contactmanagement.Title s = emanager.find(com.app.shared.organization.contactmanagement.Title.class, id);
        emanager.remove(s);
        Log.out.println("ORGCM328990200", runtimeLogInfoHelper.getRequestHeaderBean(), "TitleRepositoryImpl", "delete", "Record Deleted");
    }

    @Override
    @Transactional
    public void update(Title entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        emanager.merge(entity);
        Log.out.println("ORGCM321990200", runtimeLogInfoHelper.getRequestHeaderBean(), "TitleRepositoryImpl", "update", entity);
    }

    @Override
    @Transactional
    public void update(List<Title> entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (int i = 0; i < entity.size(); i++) {
            com.app.shared.organization.contactmanagement.Title obj = entity.get(i);
            emanager.merge(obj);
        }
        Log.out.println("ORGCM321990200", runtimeLogInfoHelper.getRequestHeaderBean(), "TitleRepositoryImpl", "updateAll", "Total Records updated = " + entity.size());
    }

    @Transactional
    public Title findById(String titleId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("Title.findById");
        query.setParameter("titleId", titleId);
        com.app.shared.organization.contactmanagement.Title listOfTitle = (com.app.shared.organization.contactmanagement.Title) query.getSingleResult();
        Log.out.println("ORGCM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "TitleRepositoryImpl", "findById", "Total Records Fetched = " + listOfTitle);
        return listOfTitle;
    }
}

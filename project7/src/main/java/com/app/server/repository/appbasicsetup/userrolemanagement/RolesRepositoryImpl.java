package com.app.server.repository.appbasicsetup.userrolemanagement;
import com.app.server.repository.core.SearchInterfaceImpl;
import com.app.shared.appbasicsetup.userrolemanagement.Roles;
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
import com.app.shared.appbasicsetup.userrolemanagement.RoleMenuBridge;

@Repository
@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Repository for Roles Transaction table", complexity = Complexity.MEDIUM)
public class RolesRepositoryImpl extends SearchInterfaceImpl implements RolesRepository<Roles> {

    @Autowired
    private ResourceFactoryManagerHelper emfResource;

    private LogManager Log = LogManagerFactory.getInstance(AppLoggerConstant.LOGGER_ID);

    @Autowired
    private RuntimeLogInfoHelper runtimeLogInfoHelper;

    @Override
    @Transactional
    public List<Roles> findAll() throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        java.util.List<com.app.shared.appbasicsetup.userrolemanagement.Roles> query = emanager.createQuery("select u from Roles u where u.systemInfo.activeStatus=1").getResultList();
        Log.out.println("ABSRM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "RolesRepositoryImpl", "findAll", "Total Records Fetched = " + query.size());
        return query;
    }

    @Override
    @Transactional
    public Roles save(Roles entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        emanager.persist(entity);
        Log.out.println("ABSRM322990200", runtimeLogInfoHelper.getRequestHeaderBean(), "RolesRepositoryImpl", "save", entity);
        return entity;
    }

    @Override
    @Transactional
    public List<Roles> save(List<Roles> entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (int i = 0; i < entity.size(); i++) {
            com.app.shared.appbasicsetup.userrolemanagement.Roles obj = entity.get(i);
            emanager.persist(obj);
        }
        Log.out.println("ABSRM322990200", runtimeLogInfoHelper.getRequestHeaderBean(), "RolesRepositoryImpl", "saveAll", "Total Records saved = " + entity.size());
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        com.app.shared.appbasicsetup.userrolemanagement.Roles s = emanager.find(com.app.shared.appbasicsetup.userrolemanagement.Roles.class, id);
        emanager.remove(s);
        Log.out.println("ABSRM328990200", runtimeLogInfoHelper.getRequestHeaderBean(), "RolesRepositoryImpl", "delete", "Record Deleted");
    }

    @Override
    @Transactional
    public void deleteRoleMenuBridge(List<RoleMenuBridge> rolemenubridge) {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (com.app.shared.appbasicsetup.userrolemanagement.RoleMenuBridge _rolemenubridge : rolemenubridge) {
            com.app.shared.appbasicsetup.userrolemanagement.RoleMenuBridge s = emanager.find(com.app.shared.appbasicsetup.userrolemanagement.RoleMenuBridge.class, _rolemenubridge.getRoleMenuMapId());
            emanager.remove(s);
        }
    }

    @Override
    @Transactional
    public void update(Roles entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        emanager.merge(entity);
        Log.out.println("ABSRM321990200", runtimeLogInfoHelper.getRequestHeaderBean(), "RolesRepositoryImpl", "update", entity);
    }

    @Override
    @Transactional
    public void update(List<Roles> entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (int i = 0; i < entity.size(); i++) {
            com.app.shared.appbasicsetup.userrolemanagement.Roles obj = entity.get(i);
            emanager.merge(obj);
        }
        Log.out.println("ABSRM321990200", runtimeLogInfoHelper.getRequestHeaderBean(), "RolesRepositoryImpl", "updateAll", "Total Records updated = " + entity.size());
    }

    @Transactional
    public Roles findById(String roleId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("Roles.findById");
        query.setParameter("roleId", roleId);
        com.app.shared.appbasicsetup.userrolemanagement.Roles listOfRoles = (com.app.shared.appbasicsetup.userrolemanagement.Roles) query.getSingleResult();
        Log.out.println("ABSRM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "RolesRepositoryImpl", "findById", "Total Records Fetched = " + listOfRoles);
        return listOfRoles;
    }
}

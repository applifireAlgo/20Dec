package com.app.server.repository.appbasicsetup.usermanagement;
import com.app.server.repository.core.SearchInterfaceImpl;
import org.springframework.stereotype.Repository;
import com.athena.server.pluggable.utils.helper.ResourceFactoryManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import com.spartan.server.interfaces.UserInterface;
import com.spartan.server.interfaces.UserRepositoryInterface;
import java.lang.Override;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.spartan.pluggable.logger.alarms.AppAlarm;
import com.spartan.pluggable.logger.api.LogManagerFactory;
import com.athena.server.pluggable.utils.AppLoggerConstant;
import com.spartan.pluggable.logger.api.LogManager;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
import com.app.shared.appbasicsetup.usermanagement.User;
import com.app.shared.appbasicsetup.usermanagement.PassRecovery;

@Repository
public class UserRepositoryImpl extends SearchInterfaceImpl implements UserRepository<User> {

    @Autowired
    private ResourceFactoryManagerHelper emfResource;

    @Transactional
    @Override
    public void update(List<UserInterface> entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (int i = 0; i < entity.size(); i++) {
            com.spartan.server.interfaces.UserInterface obj = entity.get(i);
            emanager.merge(obj);
        }
    }

    @Override
    @Transactional
    public UserInterface getByUserId(String userId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("User.findById");
        query.setParameter("userId", userId);
        return (com.spartan.server.interfaces.UserInterface) query.getSingleResult();
    }

    private LogManager Log = LogManagerFactory.getInstance(AppLoggerConstant.LOGGER_ID);

    @Autowired
    private RuntimeLogInfoHelper runtimeLogInfoHelper;

    @Override
    @Transactional
    public List<User> findAll() throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        java.util.List<com.app.shared.appbasicsetup.usermanagement.User> query = emanager.createQuery("select u from User u where u.systemInfo.activeStatus=1").getResultList();
        Log.out.println("ABSUM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "UserRepositoryImpl", "findAll", "Total Records Fetched = " + query.size());
        return query;
    }

    @Override
    @Transactional
    public User save(User entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        emanager.persist(entity);
        Log.out.println("ABSUM321990200", runtimeLogInfoHelper.getRequestHeaderBean(), "UserRepositoryImpl", "save", entity);
        return entity;
    }

    @Override
    @Transactional
    public List<User> save(List<User> entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (int i = 0; i < entity.size(); i++) {
            com.app.shared.appbasicsetup.usermanagement.User obj = entity.get(i);
            emanager.persist(obj);
        }
        Log.out.println("ABSUM321990200", runtimeLogInfoHelper.getRequestHeaderBean(), "UserRepositoryImpl", "saveAll", "Total Records saved = " + entity.size());
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        com.app.shared.appbasicsetup.usermanagement.User s = emanager.find(com.app.shared.appbasicsetup.usermanagement.User.class, id);
        emanager.remove(s);
        Log.out.println("ABSUM328990200", runtimeLogInfoHelper.getRequestHeaderBean(), "UserRepositoryImpl", "delete", "Record Deleted");
    }

    @Override
    @Transactional
    public void deletePassRecovery(List<PassRecovery> passrecovery) {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (com.app.shared.appbasicsetup.usermanagement.PassRecovery _passrecovery : passrecovery) {
            com.app.shared.appbasicsetup.usermanagement.PassRecovery s = emanager.find(com.app.shared.appbasicsetup.usermanagement.PassRecovery.class, _passrecovery.getPassRecoveryId());
            emanager.remove(s);
        }
    }

    @Override
    @Transactional
    public void update(User entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        emanager.merge(entity);
        Log.out.println("ABSUM322990200", runtimeLogInfoHelper.getRequestHeaderBean(), "UserRepositoryImpl", "update", entity);
    }

    @Transactional
    public List<User> findByUserAccessLevelId(String userAccessLevelId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("User.findByUserAccessLevelId");
        query.setParameter("userAccessLevelId", userAccessLevelId);
        java.util.List<com.app.shared.appbasicsetup.usermanagement.User> listOfUser = query.getResultList();
        Log.out.println("ABSUM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "UserRepositoryImpl", "findByUserAccessLevelId", "Total Records Fetched = " + listOfUser.size());
        return listOfUser;
    }

    @Transactional
    public List<User> findByUserAccessDomainId(String userAccessDomainId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("User.findByUserAccessDomainId");
        query.setParameter("userAccessDomainId", userAccessDomainId);
        java.util.List<com.app.shared.appbasicsetup.usermanagement.User> listOfUser = query.getResultList();
        Log.out.println("ABSUM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "UserRepositoryImpl", "findByUserAccessDomainId", "Total Records Fetched = " + listOfUser.size());
        return listOfUser;
    }

    @Transactional
    public User findById(String userId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("User.findById");
        query.setParameter("userId", userId);
        com.app.shared.appbasicsetup.usermanagement.User listOfUser = (com.app.shared.appbasicsetup.usermanagement.User) query.getSingleResult();
        Log.out.println("ABSUM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "UserRepositoryImpl", "findById", "Total Records Fetched = " + listOfUser);
        return listOfUser;
    }
}

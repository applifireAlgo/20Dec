package com.app.server.repository.appbasicsetup.usermanagement;
import com.app.server.repository.core.SearchInterfaceImpl;
import com.app.shared.appbasicsetup.usermanagement.LoginSession;
import org.springframework.stereotype.Repository;
import com.app.config.annotation.Complexity;
import com.app.config.annotation.SourceCodeAuthorClass;
import com.athena.server.pluggable.utils.helper.ResourceFactoryManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import com.spartan.server.interfaces.LoginSessionInterface;
import java.lang.Override;
import java.sql.Timestamp;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Repository for LoginSession Transaction table", complexity = Complexity.MEDIUM)
public class LoginSessionRepositoryImpl extends SearchInterfaceImpl implements LoginSessionRepository<LoginSession> {

    @Autowired
    private ResourceFactoryManagerHelper emfResource;

    @Override
    @Transactional
    public void updateUserSession(LoginSessionInterface loginSession, String SessionData) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createQuery("update LoginSession l set l.sessionData=:sessionData where l.appSessionId=:AppSessionId");
        query.setParameter("AppSessionId", loginSession.getAppSessionId());
        query.setParameter("SessionData", SessionData);
        int updateVal = query.executeUpdate();
    }

    @Override
    @Transactional
    public void saveSession(String userId, String appSessionId, String appServerSessinId, Timestamp loginTime, Timestamp logOutTime, String ClientIPAddress, Long ClientIPAddressInt, int ClientNetworkAddress, String ClientBrowser) throws Exception {
        com.app.shared.appbasicsetup.usermanagement.LoginSession loginSession = new com.app.shared.appbasicsetup.usermanagement.LoginSession();
        loginSession.setAppServerSessionId(appServerSessinId);
        loginSession.setAppSessionId(appSessionId);
        loginSession.setClientBrowser(ClientBrowser);
        loginSession.setClientIPAddress(ClientIPAddress);
        loginSession.setClientIPAddressInt(ClientIPAddressInt);
        loginSession.setClientNetworkAddress(ClientNetworkAddress);
        loginSession.setLoginTime(loginTime);
        loginSession.setLogoutTime(logOutTime);
        loginSession.setUserId(userId);
        loginSession.setSystemInformation(com.athena.server.pluggable.interfaces.CommonEntityInterface.RECORD_TYPE.ADD);
        javax.persistence.EntityManager emanager = emfResource.getResource();
        emanager.persist(loginSession);
    }

    @Override
    @Transactional
    public void updateSession(LoginSessionInterface loginSession) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createQuery("update LoginSession l set l.loginTime=:loginTime, l.logoutTime=:logoutTime, l.systemInfo.activeStatus=:activeStatus where l.appSessionId=:AppSessionId");
        query.setParameter("loginTime", loginSession.getLoginTime());
        query.setParameter("logoutTime", loginSession.getLogoutTime());
        query.setParameter("activeStatus", loginSession.getActiveStatus());
        query.setParameter("AppSessionId", loginSession.getAppSessionId());
        int updateVal = query.executeUpdate();
    }

    @Override
    @Transactional
    public LoginSessionInterface findById(String AppSessionId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createQuery("select l from LoginSession l where l.appSessionId=:AppSessionId and l.systemInfo.activeStatus=1");
        query.setParameter("AppSessionId", AppSessionId);
        java.util.List<LoginSession> loginSession = query.getResultList();
        if (loginSession != null) {
            if (loginSession.size() > 0) {
                return (LoginSessionInterface) loginSession.get(0);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void updateLastAccessTime(String userId, String appSessionId, Timestamp timestamp) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createQuery("update LoginSession l set l.lastAccessTime=:lastAccessTime where l.appSessionId=:appSessionId and l.userId=:userId");
        query.setParameter("lastAccessTime", timestamp);
        query.setParameter("userId", userId);
        query.setParameter("appSessionId", appSessionId);
        int updateVal = query.executeUpdate();
    }
}

package com.app.server.service.appbasicsetup.userrolemanagement;
import com.app.server.service.EntityTestCriteria;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.app.config.WebConfigExtended;
import org.springframework.test.context.ContextConfiguration;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.TestExecutionListeners;
import com.app.server.repository.appbasicsetup.userrolemanagement.RolesRepository;
import com.app.shared.appbasicsetup.userrolemanagement.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
import com.athena.server.pluggable.utils.helper.EntityValidatorHelper;
import com.app.server.service.RandomValueGenerator;
import java.util.HashMap;
import java.util.List;
import com.spartan.healthmeter.entity.scheduler.ArtMethodCallStack;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.springframework.mock.web.MockServletContext;
import com.spartan.pluggable.logger.api.LogManagerFactory;
import com.athena.server.pluggable.utils.AppLoggerConstant;
import com.spartan.pluggable.logger.api.LogManager;
import com.spartan.pluggable.logger.event.RequestHeaderBean;
import com.spartan.pluggable.logger.api.RuntimeLogUserInfoBean;
import com.athena.server.pluggable.interfaces.CommonEntityInterface.RECORD_TYPE;
import org.junit.Test;
import com.app.shared.appbasicsetup.userrolemanagement.RoleMenuBridge;
import com.app.shared.appbasicsetup.userrolemanagement.AppMenus;
import com.app.server.repository.appbasicsetup.userrolemanagement.AppMenusRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfigExtended.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestExecutionListeners({ org.springframework.test.context.support.DependencyInjectionTestExecutionListener.class, org.springframework.test.context.support.DirtiesContextTestExecutionListener.class, org.springframework.test.context.transaction.TransactionalTestExecutionListener.class })
public class RolesTestCase extends EntityTestCriteria {

    @Autowired
    private RolesRepository<Roles> rolesRepository;

    @Autowired
    private RuntimeLogInfoHelper runtimeLogInfoHelper;

    @Autowired
    private EntityValidatorHelper<Object> entityValidator;

    private RandomValueGenerator valueGenerator = new RandomValueGenerator();

    private static HashMap<String, Object> map = new HashMap<String, Object>();

    private static List<EntityTestCriteria> entityContraint;

    @Autowired
    private ArtMethodCallStack methodCallStack;

    protected MockHttpSession session;

    protected MockHttpServletRequest request;

    protected MockHttpServletResponse response;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        MockServletContext mockServletContext = new MockServletContext("file:src/main/webapp");
        try {
            String _path = mockServletContext.getRealPath("/WEB-INF/conf/");
            LogManagerFactory.createLogManager(_path, AppLoggerConstant.LOGGER_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void startSession() {
        session = new MockHttpSession();
    }

    protected void endSession() {
        session.clearAttributes();
        session.invalidate();
        session = null;
    }

    protected void startRequest() {
        request = new MockHttpServletRequest();
        request.setSession(session);
        org.springframework.web.context.request.RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));
    }

    protected void endRequest() {
        ((org.springframework.web.context.request.ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.getRequestAttributes()).requestCompleted();
        org.springframework.web.context.request.RequestContextHolder.resetRequestAttributes();
        request = null;
    }

    @Before
    public void before() {
        startSession();
        startRequest();
        setBeans();
    }

    @After
    public void after() {
        endSession();
        endRequest();
    }

    private void setBeans() {
        runtimeLogInfoHelper.createRuntimeLogUserInfo("customer", "AAAAA", request.getRemoteHost());
        org.junit.Assert.assertNotNull(runtimeLogInfoHelper);
        methodCallStack.setRequestId(java.util.UUID.randomUUID().toString().toUpperCase());
        entityContraint = addingListOfFieldForNegativeTesting();
        runtimeLogInfoHelper.setRequestHeaderBean(new RequestHeaderBean(new RuntimeLogUserInfoBean("AAAA", "AAAA", request.getRemoteHost(), 0, 0, 0), "", methodCallStack.getRequestId()));
    }

    private Roles createRoles(Boolean isSave) throws Exception {
        Roles roles = new Roles();
        roles.setRoleDescription("7992fbMnVpLJ9fdTfWPArk0BZPcZpIviaNGW9hLbT3m9dU0tHH");
        roles.setRoleIcon("ffZM0fmT7UNdMSkp4k8wjXzhyEAGHFC2TKthZTuUBOlREXnBwO");
        roles.setRoleName("nzONvRbtR38FEmSJJoNgwFe78i4IBXkevqxGYgLZ2kzSr8gStt");
        roles.setRoleHelp("1CLyPCC0WTU2Luqn97kHfm9HihI2phJIxSabejx8In62Y6sxaE");
        java.util.List<RoleMenuBridge> listOfRoleMenuBridge = new java.util.ArrayList<RoleMenuBridge>();
        RoleMenuBridge rolemenubridge = new RoleMenuBridge();
        AppMenus appmenus = new AppMenus();
        appmenus.setMenuAction("AIghf7VosDASnYJDwnfJ8FZV2b2LUKcpikLu8yYUguPUnzv63w");
        appmenus.setMenuDisplay(true);
        appmenus.setRefObjectId("JSur8Al4e95gd7GRr3I0YeQWbZfe1CTK0qXgTD9SfSDwTGIGpY");
        appmenus.setUiType("cu0");
        appmenus.setMenuAccessRights(4);
        appmenus.setMenuTreeId("vgf4DyVNR2XBEfki0IfaiaQ4etCyG9imWPor2nQxCF9fP1R1OS");
        appmenus.setMenuLabel("GehUKaUtV57bWvYYWoNz3vIJe3LlT9m3q6KyNX2uXFiae8GNLq");
        appmenus.setMenuHead(true);
        appmenus.setAppType(1);
        appmenus.setMenuIcon("yxWdafODd40ktl263Xb9j6za230B0a8owcDRrZlzHSlF0PhFWP");
        appmenus.setAutoSave(true);
        appmenus.setAppId("IJvST5yClDD2ChSy6eEVxaUB2YfEfzjWyM9bpk7AG4medS5iYQ");
        appmenus.setMenuCommands("jhJduew07iUXKriWIiUO1SL8mbk4mWIir9QX2gPV69tfWqS8Cd");
        AppMenus AppMenusTest = new AppMenus();
        if (isSave) {
            AppMenusTest = appmenusRepository.save(appmenus);
            map.put("AppMenusPrimaryKey", appmenus._getPrimarykey());
        }
        rolemenubridge.setMenuId((java.lang.String) AppMenusTest._getPrimarykey());
        rolemenubridge.setIsExecute(true);
        rolemenubridge.setIsWrite(true);
        rolemenubridge.setIsRead(true);
        rolemenubridge.setRoles(roles);
        listOfRoleMenuBridge.add(rolemenubridge);
        roles.addAllRoleMenuBridge(listOfRoleMenuBridge);
        roles.setEntityValidator(entityValidator);
        return roles;
    }

    @Test
    public void test1Save() {
        try {
            Roles roles = createRoles(true);
            roles.setEntityAudit(1, "xyz", RECORD_TYPE.ADD);
            roles.isValid();
            rolesRepository.save(roles);
            map.put("RolesPrimaryKey", roles._getPrimarykey());
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Autowired
    private AppMenusRepository<AppMenus> appmenusRepository;

    @Test
    public void test2Update() {
        try {
            org.junit.Assert.assertNotNull(map.get("RolesPrimaryKey"));
            Roles roles = rolesRepository.findById((java.lang.String) map.get("RolesPrimaryKey"));
            roles.setRoleDescription("hD98WRMla8DI0Cl98lpzZbIT7o6fQeCwwGBU7P8YTt592Fj2qG");
            roles.setVersionId(1);
            roles.setRoleIcon("XFgwTcBU2rvQ4SyjhwuZdzcmuXxlPcyxuVYaloolK3x9qi8sfz");
            roles.setRoleName("eDGruCCdvBggpMVNejzoIqKbLBIdM42bPlRen0hMzUE9GpGDRJ");
            roles.setRoleHelp("7I69DPbT0NJNCah3B6yEiSP04b5tNINqg7ZG3wMPzWUDZQBigj");
            roles.setEntityAudit(1, "xyz", RECORD_TYPE.UPDATE);
            rolesRepository.update(roles);
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test3FindById() {
        try {
            org.junit.Assert.assertNotNull(map.get("RolesPrimaryKey"));
            rolesRepository.findById((java.lang.String) map.get("RolesPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test6Delete() {
        try {
            org.junit.Assert.assertNotNull(map.get("RolesPrimaryKey"));
            rolesRepository.delete((java.lang.String) map.get("RolesPrimaryKey")); /* Deleting refrenced data */
            appmenusRepository.delete((java.lang.String) map.get("AppMenusPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    private void validateRoles(EntityTestCriteria contraints, Roles roles) throws Exception {
        if (contraints.getRuleType() == MIN_MAX) {
            roles.isValid();
        } else if (contraints.getRuleType() == NOT_NULL) {
            roles.isValid();
        } else if (contraints.getRuleType() == REGEX) {
            roles.isValid();
        } else if (contraints.getRuleType() == UNIQUE) {
            rolesRepository.save(roles);
        }
    }

    private List<EntityTestCriteria> addingListOfFieldForNegativeTesting() {
        List<EntityTestCriteria> entityContraints = new java.util.ArrayList<EntityTestCriteria>();
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 1, "RoleName", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 2, "roleName", "gHb5dTn79CIzeWBq1CzGZHVv6T3FJAX1OnIhOtGd517u29i14uF27XpL2uQpmd4SQV5FWgYhsY4xZTgrXwjxMrGmwu1CYt8Lb3KvpQNZvQJMYT9YMGyr6xXEqN51v8sLAkMrfxCjC5xkIvjqFHPIOw9K9K8n1bEobFgdjEAtZOIG7uQn45uyesqXOFZPUqN8dDmn9UFQLSQO0gA3Ls2UwUV7wOzsPMcTKrjpL34x8wlvwOoqIbBsJO9ZeogdfkiV3"));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 3, "RoleDescription", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 4, "roleDescription", "YOUoOnjoFH5ME4PreQWJ6luiBzIGi8655vci2n4j6P8cPPtUwb4gtvQxWdYMH6RLJJrXjSayWrXOe0rx7nSAL6NnhIX1UTAOLAQJEmLEmxaIFsNcozwSZG4HimzrLCGMqTTNRqsMTrOLu0drRhNGWje8mRzNE8kyFdM2MKKYbQXR5IuKbtTgKKlgTbprhksh4NtJ5vAXdyv263GeNWCLWEcbDpykajlxmzSOqb70xaTbh0mcGNJHjbk35FRcXKzUz"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 5, "roleIcon", "eX51qpF0DsKKqDUdu0EHwfA4nKF2RhVSgn282c1efIZAEJj5ELoYwv9JlPGSfuSz7"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 6, "roleHelp", "Q8t8kYi4xlUcR5PTRGyAtdFiGZ0rAPHPSOA8CxKPim0N11CO1d7DClGAKcUvNakwM77hlSjKvmVUUMFpbzwt4PTUKNzs4FK7ifAGWPZFdqP0OTabRuMlmCAPu8RIoS86UMzcjkzkYklmbV8xqyPSPfC4ay3ztf5zd6crOWyvCatsZCFBJPqfj3sYY4dyaCY2sramjsZOacmfP2gd82wv8c9dFqio9jmnzKhmOmX2ez0FTKfGV9xnxCBUimgDloOhO"));
        return entityContraints;
    }

    @Test
    public void test5NegativeTesting() throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, Exception {
        int failureCount = 0;
        for (EntityTestCriteria contraints : this.entityContraint) {
            try {
                Roles roles = createRoles(false);
                java.lang.reflect.Field field = null;
                if (!contraints.getFieldName().equalsIgnoreCase("CombineUniqueKey")) {
                    field = roles.getClass().getDeclaredField(contraints.getFieldName());
                }
                switch(((contraints.getTestId()))) {
                    case 0:
                        break;
                    case 1:
                        field.setAccessible(true);
                        field.set(roles, null);
                        validateRoles(contraints, roles);
                        failureCount++;
                        break;
                    case 2:
                        roles.setRoleName(contraints.getNegativeValue().toString());
                        validateRoles(contraints, roles);
                        failureCount++;
                        break;
                    case 3:
                        field.setAccessible(true);
                        field.set(roles, null);
                        validateRoles(contraints, roles);
                        failureCount++;
                        break;
                    case 4:
                        roles.setRoleDescription(contraints.getNegativeValue().toString());
                        validateRoles(contraints, roles);
                        failureCount++;
                        break;
                    case 5:
                        roles.setRoleIcon(contraints.getNegativeValue().toString());
                        validateRoles(contraints, roles);
                        failureCount++;
                        break;
                    case 6:
                        roles.setRoleHelp(contraints.getNegativeValue().toString());
                        validateRoles(contraints, roles);
                        failureCount++;
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (failureCount > 0) {
            org.junit.Assert.fail();
        }
    }
}

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
import com.app.server.repository.appbasicsetup.userrolemanagement.AppMenusRepository;
import com.app.shared.appbasicsetup.userrolemanagement.AppMenus;
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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfigExtended.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestExecutionListeners({ org.springframework.test.context.support.DependencyInjectionTestExecutionListener.class, org.springframework.test.context.support.DirtiesContextTestExecutionListener.class, org.springframework.test.context.transaction.TransactionalTestExecutionListener.class })
public class AppMenusTestCase extends EntityTestCriteria {

    @Autowired
    private AppMenusRepository<AppMenus> appmenusRepository;

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

    private AppMenus createAppMenus(Boolean isSave) throws Exception {
        AppMenus appmenus = new AppMenus();
        appmenus.setMenuAction("9yCaTArpzJwy2v7xKCcAaj61zEmq12lRyXfffpCis7ozE6kxJF");
        appmenus.setMenuDisplay(true);
        appmenus.setRefObjectId("3y3oz7DxWEjLZsJFQgLEIvMOAHpz83HLlIke1mnBwyIbZ3cX52");
        appmenus.setUiType("5pg");
        appmenus.setMenuAccessRights(10);
        appmenus.setMenuTreeId("QgWEwhv1YjbQj6qCQ6KAWCiJuowYUFL2HXR0GG6saC1xJsaHtk");
        appmenus.setMenuLabel("ZQAQ3YrqFEwFCLvM0ERTJy1YSjwLzJxWRveMey6YT50Bmnn61F");
        appmenus.setMenuHead(true);
        appmenus.setAppType(1);
        appmenus.setMenuIcon("E9enG4sADYXQKBiDuEj6bRPJvG9ut3p2mFqWUcq7SwG8YRs8Ll");
        appmenus.setAutoSave(true);
        appmenus.setAppId("jnB4Y27bdWSA5VZTNmljWSjAidivsYIRozIEjImQqFNpWEUqMU");
        appmenus.setMenuCommands("VijAVV3cxUfdu06BzjFJX5KG6X4prhBv5TbBEtrKU1UbMMzrLv");
        appmenus.setEntityValidator(entityValidator);
        return appmenus;
    }

    @Test
    public void test1Save() {
        try {
            AppMenus appmenus = createAppMenus(true);
            appmenus.setEntityAudit(1, "xyz", RECORD_TYPE.ADD);
            appmenus.isValid();
            appmenusRepository.save(appmenus);
            map.put("AppMenusPrimaryKey", appmenus._getPrimarykey());
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test2Update() {
        try {
            org.junit.Assert.assertNotNull(map.get("AppMenusPrimaryKey"));
            AppMenus appmenus = appmenusRepository.findById((java.lang.String) map.get("AppMenusPrimaryKey"));
            appmenus.setMenuAction("irxKxxDZ9zaIqwSfBQ6yiv1GZCXwatOw75IIv3BL0meH9SBXoM");
            appmenus.setVersionId(1);
            appmenus.setRefObjectId("Hly3CZHtpXsvTtyPidG1BSYRRiarKmVNJ83yRqOUQA5Vi4Wd4v");
            appmenus.setUiType("8Tp");
            appmenus.setMenuAccessRights(9);
            appmenus.setMenuTreeId("vyM8XxynNd5MA3dFmfTxz9EPptYDZfe7zxdZ1kqfOAhHtaJPba");
            appmenus.setMenuLabel("yXmqwGDSKTDL0GslbcZPxfhqNUXLUsZo9oXQeR3Eg4zHdZ9kPD");
            appmenus.setAppType(2);
            appmenus.setMenuIcon("L4OyjGZ9Wc0f38V27WXyIMnVZp0PLr3GWNTs1aHeIaPA0RgbLz");
            appmenus.setAppId("gMLOpyiuaEwZkS5i7102PG8muhHjcqOv4kmGOXIHdabfkUHi4M");
            appmenus.setMenuCommands("NZ505mCSH4nMn75f4RfbvmqArfKHwfOjotpmZl9SOBfNgTKdxE");
            appmenus.setEntityAudit(1, "xyz", RECORD_TYPE.UPDATE);
            appmenusRepository.update(appmenus);
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test3FindById() {
        try {
            org.junit.Assert.assertNotNull(map.get("AppMenusPrimaryKey"));
            appmenusRepository.findById((java.lang.String) map.get("AppMenusPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test6Delete() {
        try {
            org.junit.Assert.assertNotNull(map.get("AppMenusPrimaryKey"));
            appmenusRepository.delete((java.lang.String) map.get("AppMenusPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    private void validateAppMenus(EntityTestCriteria contraints, AppMenus appmenus) throws Exception {
        if (contraints.getRuleType() == MIN_MAX) {
            appmenus.isValid();
        } else if (contraints.getRuleType() == NOT_NULL) {
            appmenus.isValid();
        } else if (contraints.getRuleType() == REGEX) {
            appmenus.isValid();
        } else if (contraints.getRuleType() == UNIQUE) {
            appmenusRepository.save(appmenus);
        }
    }

    private List<EntityTestCriteria> addingListOfFieldForNegativeTesting() {
        List<EntityTestCriteria> entityContraints = new java.util.ArrayList<EntityTestCriteria>();
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 1, "menuTreeId", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 2, "menuTreeId", "6BuoKrwwO54i61Hdyb0s5AqZzuXGy0Ov71zGIrFRB7RxCBh9kLYN13GwZf4eYKEAMvPsD2fNeqanFd2MhGV0nZj0l0vu3unQlxQOaqwCpjVeBi2yoBkjmeY4VYGqX7wQMUmU1fTjubA02K96ofHqVxKV1nRIDeyjkV3yVDLQQxOX7eAFDOZd4z1M4P5Iiq4JVc7wBbxs4CVW4A4EksOAS5AdvRigFVm9zBfQqoXmU9laMnc6NKqbNlyjgejMbFlBI"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 3, "menuIcon", "Lcbx6WNnnDdv48YXoiqDjzmf4Wm24euS5gk8Hl0LYFBoftVKWvaeS5oy7bANXHM2BsnGLU3nZzZ2xu9dHem2nL2swAEb9x6iL9Z2g8bX400teD27W0McKJsn6DErH9zMpiLJsByafsE4yb9k30RWH1AspxgqTza2mvdc2UljDXy6Sf8OQfvfjJzD7VYhFa4YnkyiXohE5nH6NiDOHOMMp1oaqFrH5rSAyNc9RmzXN3LNwYDL5gLeelGuVDvkzExLf"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 4, "menuAction", "r3sS7hhFDTg7DqL1E6gCn4J7HxvbuM3LrlyGLQ2KdJ54vTv53gUVHhgaVLdEyAuMGuNXW14SKIVCc7LNuJBMOC3CqIo07SAiwOjXfjzz8Y38djIiMHC4QrKvGyrk635iMr5zIGJsHkwxRlKNR62bLjvMWHTf1RUrV3tlNE0CsS1PCLUCJCCvuChsuxpNy7nJTyu2QkzVfU8e9GdZfhUsOZsFkOWHkfieMQHrOB2Hu04hKpFCb0hq1I5Q5mJNEKxXy"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 5, "menuCommands", "fKIC2jiK6APoSWWlFETecevtpxZDyQAp34KLKIHLUdQQFHgohcgYXlEQv05DyIyky"));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 6, "menuDisplay", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 7, "menuDisplay", true));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 8, "menuHead", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 9, "menuHead", true));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 10, "menuLabel", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 11, "menuLabel", "vrHZJfLkYMh90kxyc06SzwSuJllCn4vj7lPj5k7AYyY9jqv4QHo1opJMdPkIkuhi12EZOORs1tP80tGYLR7T8iqBBiuRaWWkEe6qGon7K1SinMj8WugfBHKuwNkriCkA47sHXdMZKTsYU91jMiL2NQ7M4xP6wroeuCVuXJmXqQvyd0kqgfNdAwV9Aws47PqdfYHUCSfbdKB33lrDw4txGe20NEVh742EIetOSPdEB5UZYJ76rJ5rmWAJgvQO7uKrI"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 12, "uiType", "Gg9A"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 13, "refObjectId", "ku8xgvtq5rfBFmNaiSok2aTq3faQeArF9rEeQ3VSd904Lx9iJvlziToQpjz87wEI3lbn7XdUb7FOZQav7ynSonUENMeZ9x7JcitFcJGZTgOnCS0QtjAkBOaxXNL2126FfamCCAXVKA7KFcXR5fPUpJv1Lmo0ArRSxLw7VpDDrxUNojINjZmHALvIun8sm2wo8D1zHYtcYJLx7h9ceDpBuNbny2quAlDvCH3Zbs7CEiwvRbllKzrD4NWtdmY9WpD7l"));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 14, "menuAccessRights", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 15, "menuAccessRights", 15));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 16, "appType", 3));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 17, "appId", "20AmKvydK26aq9kgIcqt6ncfwBYPggGe1a4E423RV94dKqEZ6ZPWXODeM2qPwwPQ4PiSTfhBIesjm56N5iBh6D0q2DopW2a3zOYBeRmtAbSY1v0CttySGHIHKtNIdhvlsAUv16fO2fohdTu0AWxVSLtmE4lkWqFnYB8IjfoIhi6WBi9cZyyezuMwc2vw631XucYp7qgpJSfO6X6bbJF7J140ZTgQTRTPbvtdzA9CKnJmIjLvPekvIcxna5AIHPUAp"));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 18, "autoSave", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 19, "autoSave", true));
        return entityContraints;
    }

    @Test
    public void test5NegativeTesting() throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, Exception {
        int failureCount = 0;
        for (EntityTestCriteria contraints : this.entityContraint) {
            try {
                AppMenus appmenus = createAppMenus(false);
                java.lang.reflect.Field field = null;
                if (!contraints.getFieldName().equalsIgnoreCase("CombineUniqueKey")) {
                    field = appmenus.getClass().getDeclaredField(contraints.getFieldName());
                }
                switch(((contraints.getTestId()))) {
                    case 0:
                        break;
                    case 1:
                        field.setAccessible(true);
                        field.set(appmenus, null);
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 2:
                        appmenus.setMenuTreeId(contraints.getNegativeValue().toString());
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 3:
                        appmenus.setMenuIcon(contraints.getNegativeValue().toString());
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 4:
                        appmenus.setMenuAction(contraints.getNegativeValue().toString());
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 5:
                        appmenus.setMenuCommands(contraints.getNegativeValue().toString());
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 6:
                        field.setAccessible(true);
                        field.set(appmenus, null);
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 7:
                        break;
                    case 8:
                        field.setAccessible(true);
                        field.set(appmenus, null);
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 9:
                        break;
                    case 10:
                        field.setAccessible(true);
                        field.set(appmenus, null);
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 11:
                        appmenus.setMenuLabel(contraints.getNegativeValue().toString());
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 12:
                        appmenus.setUiType(contraints.getNegativeValue().toString());
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 13:
                        appmenus.setRefObjectId(contraints.getNegativeValue().toString());
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 14:
                        field.setAccessible(true);
                        field.set(appmenus, null);
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 15:
                        appmenus.setMenuAccessRights(Integer.parseInt(contraints.getNegativeValue().toString()));
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 16:
                        appmenus.setAppType(Integer.parseInt(contraints.getNegativeValue().toString()));
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 17:
                        appmenus.setAppId(contraints.getNegativeValue().toString());
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 18:
                        field.setAccessible(true);
                        field.set(appmenus, null);
                        validateAppMenus(contraints, appmenus);
                        failureCount++;
                        break;
                    case 19:
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

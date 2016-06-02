package com.app.server.service.appbasicsetup.usermanagement;
import com.app.server.service.EntityTestCriteria;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.app.config.WebConfigExtended;
import org.springframework.test.context.ContextConfiguration;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.TestExecutionListeners;
import com.app.server.repository.appbasicsetup.usermanagement.UserAccessLevelRepository;
import com.app.shared.appbasicsetup.usermanagement.UserAccessLevel;
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
public class UserAccessLevelTestCase extends EntityTestCriteria {

    @Autowired
    private UserAccessLevelRepository<UserAccessLevel> useraccesslevelRepository;

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

    private UserAccessLevel createUserAccessLevel(Boolean isSave) throws Exception {
        UserAccessLevel useraccesslevel = new UserAccessLevel();
        useraccesslevel.setLevelName("ePynPKEZwlNfWbxfyHRSVcvWmZh9xqkBfw72K1vng2n3R2Angf");
        useraccesslevel.setLevelDescription("cwkwHbNB7bia5fNGKEfbll3Uv4Hkixzk8fMxggJQb5NhTPG6RP");
        useraccesslevel.setUserAccessLevel(valueGenerator.getRandomInteger(99999, 0));
        useraccesslevel.setLevelIcon("U3mviyP3yQo3tJO59MmeuaNAgC5OxanJhyFdLFHTgppNzO5zw1");
        useraccesslevel.setLevelHelp("2QcirKuVR3BvExI0mLPZX5LlD7uRd2QjcDwavlaIW9WC77EARX");
        useraccesslevel.setEntityValidator(entityValidator);
        return useraccesslevel;
    }

    @Test
    public void test1Save() {
        try {
            UserAccessLevel useraccesslevel = createUserAccessLevel(true);
            useraccesslevel.setEntityAudit(1, "xyz", RECORD_TYPE.ADD);
            useraccesslevel.isValid();
            useraccesslevelRepository.save(useraccesslevel);
            map.put("UserAccessLevelPrimaryKey", useraccesslevel._getPrimarykey());
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test2Update() {
        try {
            org.junit.Assert.assertNotNull(map.get("UserAccessLevelPrimaryKey"));
            UserAccessLevel useraccesslevel = useraccesslevelRepository.findById((java.lang.String) map.get("UserAccessLevelPrimaryKey"));
            useraccesslevel.setLevelName("3Gl5jV4bQSlzhRWp5EtJmdCp2wMWOpvIq5nQEc3azVGfQxFB7I");
            useraccesslevel.setLevelDescription("ToZJCrxjLaKOfqS3nlrsc5AD6ScuOTANLdl17miT6C0lOPNr9B");
            useraccesslevel.setUserAccessLevel(84579);
            useraccesslevel.setVersionId(1);
            useraccesslevel.setLevelIcon("9mRSDJjnFN0Z9b9YuoSMcw9DSBARjUn99Je4ohfp7y0FCgh0kU");
            useraccesslevel.setLevelHelp("b9PjePET6uk5EDVmltnDSJ7AqWp7gahCTxbdLb47ftVFDKTZb2");
            useraccesslevel.setEntityAudit(1, "xyz", RECORD_TYPE.UPDATE);
            useraccesslevelRepository.update(useraccesslevel);
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test3FindById() {
        try {
            org.junit.Assert.assertNotNull(map.get("UserAccessLevelPrimaryKey"));
            useraccesslevelRepository.findById((java.lang.String) map.get("UserAccessLevelPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test6Delete() {
        try {
            org.junit.Assert.assertNotNull(map.get("UserAccessLevelPrimaryKey"));
            useraccesslevelRepository.delete((java.lang.String) map.get("UserAccessLevelPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    private void validateUserAccessLevel(EntityTestCriteria contraints, UserAccessLevel useraccesslevel) throws Exception {
        if (contraints.getRuleType() == MIN_MAX) {
            useraccesslevel.isValid();
        } else if (contraints.getRuleType() == NOT_NULL) {
            useraccesslevel.isValid();
        } else if (contraints.getRuleType() == REGEX) {
            useraccesslevel.isValid();
        } else if (contraints.getRuleType() == UNIQUE) {
            useraccesslevelRepository.save(useraccesslevel);
        }
    }

    private List<EntityTestCriteria> addingListOfFieldForNegativeTesting() {
        List<EntityTestCriteria> entityContraints = new java.util.ArrayList<EntityTestCriteria>();
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 1, "userAccessLevel", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 2, "userAccessLevel", 121859));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 3, "levelName", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 4, "levelName", "CnQYAOgw3Wdkn1ninFeQxmQSYwAJ3TG73pXsmItonbS3tjGFlPdNb3TdIEsbE5WPTUQoYbcTTxAN8TpAPO6VZGKqtkHbrzGv68khsYwSxS7ofmn9T5eGMeUNNiPZBY6N2KvYjT90PqTTiOtsJCbzKUWATRXEs3PxTKkmm1UnuWHxrOQPH0EgquhuqFDWWo70wKlaV57gbWdolDth9YfHjBCDgIiRbIHcCORxXbRRm8UapzPy7vzGP4t9zCQrBmBnV"));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 5, "levelDescription", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 6, "levelDescription", "syJCKiIWHknff3Q3PMxAiez63PY84G1mktx8p1pBZblLQyzLgznzyOGtDD1Xvd6D8Y681TNXMP4IGx5RVJWr4MkC6WcAIRKG55e36pybN0t7GIkaksPEINE49MY6gjCkQWY5wOcHAAICN0bI5g7ApJuCpx0wn8E7cDuvv9HhLi069t8NX1wiCJYsI6KkWBPS6OcKSR9EccPUJatK6nTrMVaUcVHJYJi5OB6cZdfFW3lKmN784EgfTRJVa9DznjMqe"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 7, "levelHelp", "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"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 8, "levelIcon", "V9UDvHzyP8QoZfvtftuCmDUoiLDcJsBbI9icBYTFJ4TYFhCLIag230sqd0rv7z5H49ms5wJcpxFPup3Br2pdvTQqhylwYOKgADMhXJJtx37wcJG4JCmSqOjCqkrsmZ179dr0PfbBDcha1Xkw8kqgty2Dta3pfS8ald61neKJ3AGMdMr4F4QCw2VUJoFC2lBj9QeVEewBKefhLFzgBaE5rMkZDjfFhWi77DOQ6NX4SQr67IlBzajgAmteG0FfcLKUo"));
        entityContraints.add(new EntityTestCriteria(UNIQUE, 9, "CombineUniqueKey", ""));
        return entityContraints;
    }

    @Test
    public void test5NegativeTesting() throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, Exception {
        int failureCount = 0;
        UserAccessLevel useraccesslevelUnique = useraccesslevelRepository.findById((java.lang.String) map.get("UserAccessLevelPrimaryKey"));
        for (EntityTestCriteria contraints : this.entityContraint) {
            try {
                UserAccessLevel useraccesslevel = createUserAccessLevel(false);
                java.lang.reflect.Field field = null;
                if (!contraints.getFieldName().equalsIgnoreCase("CombineUniqueKey")) {
                    field = useraccesslevel.getClass().getDeclaredField(contraints.getFieldName());
                }
                switch(((contraints.getTestId()))) {
                    case 0:
                        break;
                    case 1:
                        field.setAccessible(true);
                        field.set(useraccesslevel, null);
                        validateUserAccessLevel(contraints, useraccesslevel);
                        failureCount++;
                        break;
                    case 2:
                        useraccesslevel.setUserAccessLevel(Integer.parseInt(contraints.getNegativeValue().toString()));
                        validateUserAccessLevel(contraints, useraccesslevel);
                        failureCount++;
                        break;
                    case 3:
                        field.setAccessible(true);
                        field.set(useraccesslevel, null);
                        validateUserAccessLevel(contraints, useraccesslevel);
                        failureCount++;
                        break;
                    case 4:
                        useraccesslevel.setLevelName(contraints.getNegativeValue().toString());
                        validateUserAccessLevel(contraints, useraccesslevel);
                        failureCount++;
                        break;
                    case 5:
                        field.setAccessible(true);
                        field.set(useraccesslevel, null);
                        validateUserAccessLevel(contraints, useraccesslevel);
                        failureCount++;
                        break;
                    case 6:
                        useraccesslevel.setLevelDescription(contraints.getNegativeValue().toString());
                        validateUserAccessLevel(contraints, useraccesslevel);
                        failureCount++;
                        break;
                    case 7:
                        useraccesslevel.setLevelHelp(contraints.getNegativeValue().toString());
                        validateUserAccessLevel(contraints, useraccesslevel);
                        failureCount++;
                        break;
                    case 8:
                        useraccesslevel.setLevelIcon(contraints.getNegativeValue().toString());
                        validateUserAccessLevel(contraints, useraccesslevel);
                        failureCount++;
                        break;
                    case 9:
                        useraccesslevel.setUserAccessLevel(useraccesslevelUnique.getUserAccessLevel());
                        validateUserAccessLevel(contraints, useraccesslevel);
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

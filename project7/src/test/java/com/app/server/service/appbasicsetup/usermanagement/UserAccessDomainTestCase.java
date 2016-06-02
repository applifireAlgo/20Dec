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
import com.app.server.repository.appbasicsetup.usermanagement.UserAccessDomainRepository;
import com.app.shared.appbasicsetup.usermanagement.UserAccessDomain;
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
public class UserAccessDomainTestCase extends EntityTestCriteria {

    @Autowired
    private UserAccessDomainRepository<UserAccessDomain> useraccessdomainRepository;

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

    private UserAccessDomain createUserAccessDomain(Boolean isSave) throws Exception {
        UserAccessDomain useraccessdomain = new UserAccessDomain();
        useraccessdomain.setDomainIcon("CN6BnqqfIMa0XXE0RRIVsaPXXxcZX5d1VoZyZJSAuIcK6GdfTu");
        useraccessdomain.setUserAccessDomain(valueGenerator.getRandomInteger(99999, 0));
        useraccessdomain.setDomainDescription("HWEMPzUgiTxwlp6QzPzBf9i60xYqMFj1K68AQfRtzMBggelIVe");
        useraccessdomain.setDomainHelp("8VCJux9lAgN68efcrd2vAnu51biA6fFqHXk59rJz8CWbCFYl3Z");
        useraccessdomain.setDomainName("nwAzfTGPGw9xTAHftZtTcADPDivoSEYyUENnfqOASixnHvWxBz");
        useraccessdomain.setEntityValidator(entityValidator);
        return useraccessdomain;
    }

    @Test
    public void test1Save() {
        try {
            UserAccessDomain useraccessdomain = createUserAccessDomain(true);
            useraccessdomain.setEntityAudit(1, "xyz", RECORD_TYPE.ADD);
            useraccessdomain.isValid();
            useraccessdomainRepository.save(useraccessdomain);
            map.put("UserAccessDomainPrimaryKey", useraccessdomain._getPrimarykey());
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test2Update() {
        try {
            org.junit.Assert.assertNotNull(map.get("UserAccessDomainPrimaryKey"));
            UserAccessDomain useraccessdomain = useraccessdomainRepository.findById((java.lang.String) map.get("UserAccessDomainPrimaryKey"));
            useraccessdomain.setDomainIcon("hTuzwN0zlco5f4elwSxboUgeIaxizJfFLwFTGAy5Jz0v3jfYxK");
            useraccessdomain.setVersionId(1);
            useraccessdomain.setUserAccessDomain(97284);
            useraccessdomain.setDomainDescription("lXd9NjHfFPwuRtiRNsmXHMBsocFwFbXDIEXBX5IFs1WILEv4py");
            useraccessdomain.setDomainHelp("zytA4YUvYnRi5w4jh0z7669uxlChmu1EUm3MOsihrOBLHQppDH");
            useraccessdomain.setDomainName("3nTfpN2X1cjkmLHClKIzcoZH4bjR7SXwnBaB2ps2QD2N5MduhM");
            useraccessdomain.setEntityAudit(1, "xyz", RECORD_TYPE.UPDATE);
            useraccessdomainRepository.update(useraccessdomain);
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test3FindById() {
        try {
            org.junit.Assert.assertNotNull(map.get("UserAccessDomainPrimaryKey"));
            useraccessdomainRepository.findById((java.lang.String) map.get("UserAccessDomainPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test6Delete() {
        try {
            org.junit.Assert.assertNotNull(map.get("UserAccessDomainPrimaryKey"));
            useraccessdomainRepository.delete((java.lang.String) map.get("UserAccessDomainPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    private void validateUserAccessDomain(EntityTestCriteria contraints, UserAccessDomain useraccessdomain) throws Exception {
        if (contraints.getRuleType() == MIN_MAX) {
            useraccessdomain.isValid();
        } else if (contraints.getRuleType() == NOT_NULL) {
            useraccessdomain.isValid();
        } else if (contraints.getRuleType() == REGEX) {
            useraccessdomain.isValid();
        } else if (contraints.getRuleType() == UNIQUE) {
            useraccessdomainRepository.save(useraccessdomain);
        }
    }

    private List<EntityTestCriteria> addingListOfFieldForNegativeTesting() {
        List<EntityTestCriteria> entityContraints = new java.util.ArrayList<EntityTestCriteria>();
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 1, "userAccessDomain", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 2, "userAccessDomain", 119052));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 3, "domainName", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 4, "domainName", "QLlpWp1wF6wHwb62E1QIm3VgXaCYMAVHyLeF3EvOpIkBVN4VpMRSz4ZtYEwdddZhM4bp8HBEzU2F12GD3muB2LY1NlMg3nlaK1K3WtQ8BHH3NSJ3EGMJ488c62aqJs06ePRm1Fx6IAjAibHgo74HsrZwdFE3FYm1AtgRm0JQFQSF9habxQuirONcLQb7KDFManZmZglr6h8EbqUqXGMvgtTIs3ebfhciSWNeUOvUSG7oh2EPSNqcsNM0f1xrDtrR4"));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 5, "domainDescription", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 6, "domainDescription", "lN882us3SPjvSnJ28A6DUnHOz8RrhitMgpgRy8FQBnUyE38k80shqH7AmQ1SnAUk59jB2JRnblcMKoV9cfzrsAcpTaxZzCdFFQIjf84tJmyACHwJZxP9MqTxP4blv0UB8bsoFUl2VLTqQ8R1SPC00y8tjhn3qEvF5rTKrlFOAGJVtFtjWAuvuCpT78PygztMfM1D27bJHmJ1P0iXtikuI2Hi29A1js8iQmig1pHh6ujnO5eMa6iUupKUlqAd5bYWZ"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 7, "domainHelp", "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"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 8, "domainIcon", "BKpHsYaidHt9kBQSDGgOY8goUOoSsOfTQUB90CkhV2wrZ38U6DvzCwWwDi6EOzjbL62w43JLsB5uWjHNhfFGzdQsZFnX6dKGVSJFNJCcK962JZX0lHqokWpD2terxevdbJwQsG21qjMLNBJifaBh0Pm7ZPxzVsV3Iba91uOB734ooVufl9J9aX2NtDk2yjAXZztUCNGrjd7uMFF5JZkTpe44GYuQEbVL8qSQTuNPlovDmB79kL92GkoYzPVZVupNv"));
        entityContraints.add(new EntityTestCriteria(UNIQUE, 9, "CombineUniqueKey", ""));
        return entityContraints;
    }

    @Test
    public void test5NegativeTesting() throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, Exception {
        int failureCount = 0;
        UserAccessDomain useraccessdomainUnique = useraccessdomainRepository.findById((java.lang.String) map.get("UserAccessDomainPrimaryKey"));
        for (EntityTestCriteria contraints : this.entityContraint) {
            try {
                UserAccessDomain useraccessdomain = createUserAccessDomain(false);
                java.lang.reflect.Field field = null;
                if (!contraints.getFieldName().equalsIgnoreCase("CombineUniqueKey")) {
                    field = useraccessdomain.getClass().getDeclaredField(contraints.getFieldName());
                }
                switch(((contraints.getTestId()))) {
                    case 0:
                        break;
                    case 1:
                        field.setAccessible(true);
                        field.set(useraccessdomain, null);
                        validateUserAccessDomain(contraints, useraccessdomain);
                        failureCount++;
                        break;
                    case 2:
                        useraccessdomain.setUserAccessDomain(Integer.parseInt(contraints.getNegativeValue().toString()));
                        validateUserAccessDomain(contraints, useraccessdomain);
                        failureCount++;
                        break;
                    case 3:
                        field.setAccessible(true);
                        field.set(useraccessdomain, null);
                        validateUserAccessDomain(contraints, useraccessdomain);
                        failureCount++;
                        break;
                    case 4:
                        useraccessdomain.setDomainName(contraints.getNegativeValue().toString());
                        validateUserAccessDomain(contraints, useraccessdomain);
                        failureCount++;
                        break;
                    case 5:
                        field.setAccessible(true);
                        field.set(useraccessdomain, null);
                        validateUserAccessDomain(contraints, useraccessdomain);
                        failureCount++;
                        break;
                    case 6:
                        useraccessdomain.setDomainDescription(contraints.getNegativeValue().toString());
                        validateUserAccessDomain(contraints, useraccessdomain);
                        failureCount++;
                        break;
                    case 7:
                        useraccessdomain.setDomainHelp(contraints.getNegativeValue().toString());
                        validateUserAccessDomain(contraints, useraccessdomain);
                        failureCount++;
                        break;
                    case 8:
                        useraccessdomain.setDomainIcon(contraints.getNegativeValue().toString());
                        validateUserAccessDomain(contraints, useraccessdomain);
                        failureCount++;
                        break;
                    case 9:
                        useraccessdomain.setUserAccessDomain(useraccessdomainUnique.getUserAccessDomain());
                        validateUserAccessDomain(contraints, useraccessdomain);
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

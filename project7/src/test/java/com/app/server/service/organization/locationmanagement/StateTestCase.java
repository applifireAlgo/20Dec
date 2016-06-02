package com.app.server.service.organization.locationmanagement;
import com.app.server.service.EntityTestCriteria;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.app.config.WebConfigExtended;
import org.springframework.test.context.ContextConfiguration;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.TestExecutionListeners;
import com.app.server.repository.organization.locationmanagement.StateRepository;
import com.app.shared.organization.locationmanagement.State;
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
import com.app.shared.organization.locationmanagement.Country;
import com.app.server.repository.organization.locationmanagement.CountryRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfigExtended.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestExecutionListeners({ org.springframework.test.context.support.DependencyInjectionTestExecutionListener.class, org.springframework.test.context.support.DirtiesContextTestExecutionListener.class, org.springframework.test.context.transaction.TransactionalTestExecutionListener.class })
public class StateTestCase extends EntityTestCriteria {

    @Autowired
    private StateRepository<State> stateRepository;

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

    private State createState(Boolean isSave) throws Exception {
        Country country = new Country();
        country.setCurrencyCode("zm5");
        country.setCurrencySymbol("DICgRGGGHYyul418416r7aOlMjT2GyJ3");
        country.setCapital("LwTEgJMkygQRewgLvjhn7XEWRevIkZOi");
        country.setIsoNumeric(974);
        country.setCountryCode2("XOd");
        country.setCapitalLatitude(5);
        country.setCapitalLongitude(4);
        country.setCountryName("KZ77Xgr9K1xp4HOLnVlZZMYoFoQSP08YXTm9WcWZt66GRGNM0H");
        country.setCountryCode1("V9F");
        country.setCountryFlag("tHJHMKsNpo69IffJ93So3vXLvgDMTi3X2ve7qou0ndTMU4GbAc");
        country.setCurrencyName("6kObfPBaExxX2nYGpFOnIjlncAJyCLSbqPeArTeNx3L1IGny3N");
        Country CountryTest = new Country();
        if (isSave) {
            CountryTest = countryRepository.save(country);
            map.put("CountryPrimaryKey", country._getPrimarykey());
        }
        State state = new State();
        state.setStateCapital("XVuNDCmQ5SgqQ7RhMMPj4liZJLUmqsJvx4EbkbtLFTejcgKzlO");
        state.setCountryId((java.lang.String) CountryTest._getPrimarykey());
        state.setStateName("ZIX3oSwZ9RTtkUQris2bTHxRvWcDOhRymHO7BotyGYW0XT8DIT");
        state.setStateCode(2);
        state.setStateCapitalLatitude(4);
        state.setStateFlag("0r6Ok5R5zuFlJoRV1Fhreau0O101ZDlzPVto9NFnV8MXxRTJzI");
        state.setStateCodeChar2("3F99Jb1qGe6ieRkqpoBSr3uW2hjskaBx");
        state.setStateDescription("hnshJCgq5ULk3PmZ7KsIHaDcfDm1trrSQz4M5OOscvoPxOutY6");
        state.setStateCapitalLongitude(9);
        state.setStateCodeChar3("svUp1TBxHWy9rqm7icyup8p5pZUrUy7a");
        state.setEntityValidator(entityValidator);
        return state;
    }

    @Test
    public void test1Save() {
        try {
            State state = createState(true);
            state.setEntityAudit(1, "xyz", RECORD_TYPE.ADD);
            state.isValid();
            stateRepository.save(state);
            map.put("StatePrimaryKey", state._getPrimarykey());
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Autowired
    private CountryRepository<Country> countryRepository;

    @Test
    public void test2Update() {
        try {
            org.junit.Assert.assertNotNull(map.get("StatePrimaryKey"));
            State state = stateRepository.findById((java.lang.String) map.get("StatePrimaryKey"));
            state.setStateCapital("7CkfCAv0o5YAYGsgyLyj7vxbtQoBcDJK2jEaixI8lRfVBmEJMI");
            state.setStateName("yezaKBpXHRxpZrsgCVQ1gFbe3fkRzgPSctm9hxZq2nFNMhfIhX");
            state.setStateCode(1);
            state.setStateCapitalLatitude(9);
            state.setStateFlag("X5JPCEUQfk7GrrG125YJsg1ybVfAHNde6fKuyQC6ZP91mDNlxv");
            state.setStateCodeChar2("6JWrBLUXrcuvhbhyXbGIVo1NVjnwnQ42");
            state.setStateDescription("LFNNClqN7f1FK7QhybR4R2UJkZPHh34zU4CFhRUT7ZTLalSBaq");
            state.setVersionId(1);
            state.setStateCapitalLongitude(6);
            state.setStateCodeChar3("DPkrwKP9UIP6uMk81PaJgcMT2n98wnvX");
            state.setEntityAudit(1, "xyz", RECORD_TYPE.UPDATE);
            stateRepository.update(state);
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test3findBycountryId() {
        try {
            java.util.List<State> listofcountryId = stateRepository.findByCountryId((java.lang.String) map.get("CountryPrimaryKey"));
            if (listofcountryId.size() == 0) {
                org.junit.Assert.fail("Query did not return any records.");
            }
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test3FindById() {
        try {
            org.junit.Assert.assertNotNull(map.get("StatePrimaryKey"));
            stateRepository.findById((java.lang.String) map.get("StatePrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test6Delete() {
        try {
            org.junit.Assert.assertNotNull(map.get("StatePrimaryKey"));
            stateRepository.delete((java.lang.String) map.get("StatePrimaryKey")); /* Deleting refrenced data */
            countryRepository.delete((java.lang.String) map.get("CountryPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    private void validateState(EntityTestCriteria contraints, State state) throws Exception {
        if (contraints.getRuleType() == MIN_MAX) {
            state.isValid();
        } else if (contraints.getRuleType() == NOT_NULL) {
            state.isValid();
        } else if (contraints.getRuleType() == REGEX) {
            state.isValid();
        } else if (contraints.getRuleType() == UNIQUE) {
            stateRepository.save(state);
        }
    }

    private List<EntityTestCriteria> addingListOfFieldForNegativeTesting() {
        List<EntityTestCriteria> entityContraints = new java.util.ArrayList<EntityTestCriteria>();
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 1, "stateName", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 2, "stateName", "Wfec54PkkXv0srSNLLwYRFsNPxHPFP0Ax63H3CN3ONBG4FNAGRFXJMCTMnoNr716dPrHU1UBqc1HfFXSk41tTs48NhywGIhVOq9wQd9BcU3goQ8RQegBy1LhT1X3pfgEkjhGcP8j7P9qH23uIB7LUUaggsirPlB6wnieAcgKq8kHbkKplm8I88geTR6dwJdQwUWj7Y3kH0katRHY8rsjcOUnyTfi6HPjOaMBA083QMcEapjQnC7Ls1J3zLmOgdUnT"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 3, "stateCode", 4));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 4, "stateCodeChar2", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 5, "stateCodeChar2", "SOhBt5fgwaiVFg9KYKJfhMhzW6crgNjyV"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 6, "stateCodeChar3", "zmCPX3jjH1h4B63Nb7i0ArtJnhbM3Vxwo"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 7, "stateDescription", "tqRchcWPl7VakVxgaw7wMfzEuhvlfksDRQGPOnBx9csA9lZfGDtlNhZD6wlIHj6wtQcWvKSSWuK15zLgD9ryWIZm5yPov7ZRxj8i9AQUd544ZLpJrQQDlkbP0ReIMhu4iE34RekaXj0o9yH6Mw0VjIywqUDWuf3VtrJxPwPH8lo0BQ2nF3OQm4ybQkCXZDkGHWrS2a2RbuTxD1ASAAzzcmOwhl2zvGHUvEYmKF6m5tLiFkRaELVgdDQ1rgQVgE6Ob"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 8, "stateFlag", "q3AtXiofFgCzyWyQnHCbiAIPcoD9PMgMUKZWmevEZSzGtNYTr2LKPChuzbO4ph5rLI0X9mYu6ht4nBvlzWtZGsg5v6upir1300VrdN2Izfh3b49zaJx04hFTJ9AYyZPnV"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 9, "stateCapital", "qxDKfJ8Ah7ZvqzhIi37DQyGuPfpozbOgPW7Yby0AKB2wxHHTdNgPD5lIXyQIkQQIdfROTsI4G01nx6HDhMlvWVG1rQIWUOaGp2I4DNGmIcg73w62OJJG5sB6oQxSn5B8r"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 10, "stateCapitalLatitude", 21));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 11, "stateCapitalLongitude", 16));
        return entityContraints;
    }

    @Test
    public void test5NegativeTesting() throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, Exception {
        int failureCount = 0;
        for (EntityTestCriteria contraints : this.entityContraint) {
            try {
                State state = createState(false);
                java.lang.reflect.Field field = null;
                if (!contraints.getFieldName().equalsIgnoreCase("CombineUniqueKey")) {
                    field = state.getClass().getDeclaredField(contraints.getFieldName());
                }
                switch(((contraints.getTestId()))) {
                    case 0:
                        break;
                    case 1:
                        field.setAccessible(true);
                        field.set(state, null);
                        validateState(contraints, state);
                        failureCount++;
                        break;
                    case 2:
                        state.setStateName(contraints.getNegativeValue().toString());
                        validateState(contraints, state);
                        failureCount++;
                        break;
                    case 3:
                        state.setStateCode(Integer.parseInt(contraints.getNegativeValue().toString()));
                        validateState(contraints, state);
                        failureCount++;
                        break;
                    case 4:
                        field.setAccessible(true);
                        field.set(state, null);
                        validateState(contraints, state);
                        failureCount++;
                        break;
                    case 5:
                        state.setStateCodeChar2(contraints.getNegativeValue().toString());
                        validateState(contraints, state);
                        failureCount++;
                        break;
                    case 6:
                        state.setStateCodeChar3(contraints.getNegativeValue().toString());
                        validateState(contraints, state);
                        failureCount++;
                        break;
                    case 7:
                        state.setStateDescription(contraints.getNegativeValue().toString());
                        validateState(contraints, state);
                        failureCount++;
                        break;
                    case 8:
                        state.setStateFlag(contraints.getNegativeValue().toString());
                        validateState(contraints, state);
                        failureCount++;
                        break;
                    case 9:
                        state.setStateCapital(contraints.getNegativeValue().toString());
                        validateState(contraints, state);
                        failureCount++;
                        break;
                    case 10:
                        state.setStateCapitalLatitude(Integer.parseInt(contraints.getNegativeValue().toString()));
                        validateState(contraints, state);
                        failureCount++;
                        break;
                    case 11:
                        state.setStateCapitalLongitude(Integer.parseInt(contraints.getNegativeValue().toString()));
                        validateState(contraints, state);
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

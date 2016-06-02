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
import com.app.server.repository.appbasicsetup.usermanagement.LoginRepository;
import com.app.shared.appbasicsetup.usermanagement.Login;
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
import com.app.shared.appbasicsetup.usermanagement.User;
import com.app.server.repository.appbasicsetup.usermanagement.UserRepository;
import com.app.shared.appbasicsetup.usermanagement.UserAccessDomain;
import com.app.server.repository.appbasicsetup.usermanagement.UserAccessDomainRepository;
import com.app.shared.appbasicsetup.usermanagement.UserAccessLevel;
import com.app.server.repository.appbasicsetup.usermanagement.UserAccessLevelRepository;
import com.app.shared.appbasicsetup.usermanagement.PassRecovery;
import com.app.shared.appbasicsetup.usermanagement.Question;
import com.app.server.repository.appbasicsetup.usermanagement.QuestionRepository;
import com.app.shared.appbasicsetup.usermanagement.UserData;
import com.app.shared.organization.contactmanagement.CoreContacts;
import com.app.server.repository.organization.contactmanagement.CoreContactsRepository;
import com.app.shared.organization.locationmanagement.Timezone;
import com.app.server.repository.organization.locationmanagement.TimezoneRepository;
import com.app.shared.organization.contactmanagement.Gender;
import com.app.server.repository.organization.contactmanagement.GenderRepository;
import com.app.shared.organization.contactmanagement.Title;
import com.app.server.repository.organization.contactmanagement.TitleRepository;
import com.app.shared.organization.locationmanagement.Language;
import com.app.server.repository.organization.locationmanagement.LanguageRepository;
import com.app.shared.organization.locationmanagement.Address;
import com.app.server.repository.organization.locationmanagement.AddressRepository;
import com.app.shared.organization.locationmanagement.Country;
import com.app.server.repository.organization.locationmanagement.CountryRepository;
import com.app.shared.organization.locationmanagement.City;
import com.app.server.repository.organization.locationmanagement.CityRepository;
import com.app.shared.organization.locationmanagement.State;
import com.app.server.repository.organization.locationmanagement.StateRepository;
import com.app.shared.organization.locationmanagement.AddressType;
import com.app.server.repository.organization.locationmanagement.AddressTypeRepository;
import com.app.shared.organization.contactmanagement.CommunicationData;
import com.app.shared.organization.contactmanagement.CommunicationType;
import com.app.server.repository.organization.contactmanagement.CommunicationTypeRepository;
import com.app.shared.organization.contactmanagement.CommunicationGroup;
import com.app.server.repository.organization.contactmanagement.CommunicationGroupRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfigExtended.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestExecutionListeners({ org.springframework.test.context.support.DependencyInjectionTestExecutionListener.class, org.springframework.test.context.support.DirtiesContextTestExecutionListener.class, org.springframework.test.context.transaction.TransactionalTestExecutionListener.class })
public class LoginTestCase extends EntityTestCriteria {

    @Autowired
    private LoginRepository<Login> loginRepository;

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

    private Login createLogin(Boolean isSave) throws Exception {
        User user = new User();
        UserAccessDomain useraccessdomain = new UserAccessDomain();
        useraccessdomain.setDomainIcon("v6qDKS4bOgtYgo1uRK6IakCCaM2298FAvZ9dGMCDkM2RZqPJGN");
        useraccessdomain.setUserAccessDomain(valueGenerator.getRandomInteger(99999, 0));
        useraccessdomain.setDomainDescription("846XjUyBJIHebEfK6nESlbwiCVUmsyV01w2V8X26ZbjlLTEVj5");
        useraccessdomain.setDomainHelp("uEGYcPt63onr0DD0TXmVDqE2Vn7dhqZUmRonIK77vELXoFTaZp");
        useraccessdomain.setDomainName("EIdC8vo1poGacNorMewSNNkqflkQh4mmfCifkAik8YwSQQKXon");
        UserAccessDomain UserAccessDomainTest = new UserAccessDomain();
        if (isSave) {
            UserAccessDomainTest = useraccessdomainRepository.save(useraccessdomain);
            map.put("UserAccessDomainPrimaryKey", useraccessdomain._getPrimarykey());
        }
        UserAccessLevel useraccesslevel = new UserAccessLevel();
        useraccesslevel.setLevelName("GyRW0x2JpuM00jw77vhZiekXG1mgZnjTo0HJxqrtlY90JLaIJU");
        useraccesslevel.setLevelDescription("X3sftcKQl3DCyMOuEv1XKFXAgnJkM80aSCqDTdqyXlQjGjr5v3");
        useraccesslevel.setUserAccessLevel(valueGenerator.getRandomInteger(99999, 0));
        useraccesslevel.setLevelIcon("HckYP2xdc7CDC5nik8rdBJJEFH7ABHWVqmBerCEyj82iJx6sOO");
        useraccesslevel.setLevelHelp("ckEzIOrNG0isg1HuPeZT6QYikq1IFnpG6fALRHKRSa23I9JNKL");
        UserAccessLevel UserAccessLevelTest = new UserAccessLevel();
        if (isSave) {
            UserAccessLevelTest = useraccesslevelRepository.save(useraccesslevel);
            map.put("UserAccessLevelPrimaryKey", useraccesslevel._getPrimarykey());
        }
        user.setUserAccessDomainId((java.lang.String) UserAccessDomainTest._getPrimarykey()); /* ******Adding refrenced table data */
        user.setIsLocked(1);
        user.setPasswordExpiryDate(new java.sql.Timestamp(1464879980963l));
        user.setIsDeleted(1);
        user.setSessionTimeout(2817);
        user.setMultiFactorAuthEnabled(1);
        user.setGenTempOneTimePassword(1);
        user.setChangePasswordNextLogin(1);
        user.setPasswordAlgo("u3H0eR69yLFthOWCfO2sJWikwFPrHPjRGfXW138dDtFSWMht57");
        user.setLastPasswordChangeDate(new java.sql.Timestamp(1464879980963l));
        user.setAllowMultipleLogin(1);
        user.setUserAccessLevelId((java.lang.String) UserAccessLevelTest._getPrimarykey()); /* ******Adding refrenced table data */
        user.setUserAccessCode(27576);
        java.util.List<PassRecovery> listOfPassRecovery = new java.util.ArrayList<PassRecovery>();
        PassRecovery passrecovery = new PassRecovery();
        passrecovery.setAnswer("Xs7tvDmasd433pXKd85ffDAV07oljFlEorX9llJE8hK0R9rU2I");
        Question question = new Question();
        question.setQuestion("D04VYPBGeHDJyiZxuUJImzY1IdbjDtc1WdfDEUxXmDprS4ruug");
        question.setQuestionDetails("GvNAvlFgbm");
        question.setLevelid(10);
        question.setQuestionIcon("Ste7l2zXo18I0zCNF9YXkl0yimN75eIg3PVVzWIKuFEujFzUwb");
        Question QuestionTest = new Question();
        if (isSave) {
            QuestionTest = questionRepository.save(question);
            map.put("QuestionPrimaryKey", question._getPrimarykey());
        }
        passrecovery.setAnswer("p6Z0xMKyCEOumm0OZqC0vnkQ4ICURaBTCaZD5KFgcYIfJ8nVVs");
        passrecovery.setQuestionId((java.lang.String) QuestionTest._getPrimarykey()); /* ******Adding refrenced table data */
        passrecovery.setUser(user);
        listOfPassRecovery.add(passrecovery);
        user.addAllPassRecovery(listOfPassRecovery);
        UserData userdata = new UserData();
        userdata.setOneTimePasswordGenDate(new java.sql.Timestamp(1464879981210l));
        userdata.setOneTimePasswordGenDate(new java.sql.Timestamp(1464879981228l));
        userdata.setUser(user);
        userdata.setOneTimePasswordExpiry(6);
        userdata.setLast5Passwords("MV3xsx0ZjXZcBvaZEvOVrfR0iJ9BhrmhD8oojchaZRRLG8nswi");
        userdata.setPassword("PDtDBzgTQGfrxt8do8hIRF1lWxhlOTmSC9Z0SA02NZDBP1CO2R");
        userdata.setOneTimePassword("rTYUWFOpVd5g50CAIo4XzZWWLel2Qeyq");
        user.setUserData(userdata);
        CoreContacts corecontacts = new CoreContacts();
        Timezone timezone = new Timezone();
        timezone.setUtcdifference(1);
        timezone.setCountry("KxNdnSm4WJaAsYU07V212uvQGT1Xq6OiyH0gJk3BnycKDgjpyp");
        timezone.setTimeZoneLabel("p5ETlXD7t9ciqEfg3nHGhmiYvY3RWjBgKFAY9wHWFgbe8c5HwY");
        timezone.setCities("9NAMUVF4t6qbgjhk1BbAvDJMNoCfPBjONz0tkAXEl1KjB1C0la");
        timezone.setGmtLabel("nqMjkQYexZUDieguGQMurz0WHOjfoCIRo5d6aMlS6hYEwSaAcK");
        Gender gender = new Gender();
        gender.setGender("ouscyFfsHF5PgxnoNi3666R4Rr6Iw4ZBpgy2VBX7ElVV9E1PgE");
        Gender GenderTest = new Gender();
        if (isSave) {
            GenderTest = genderRepository.save(gender);
            map.put("GenderPrimaryKey", gender._getPrimarykey());
        }
        Title title = new Title();
        title.setTitles("BmXaeBeyZixUguxYMSS8TVu0EyIryl7GDCbm4H9QYFQgFw6gpj");
        Title TitleTest = new Title();
        if (isSave) {
            TitleTest = titleRepository.save(title);
            map.put("TitlePrimaryKey", title._getPrimarykey());
        }
        Language language = new Language();
        language.setLanguageIcon("bQe8CUmGLdrRskUQGU2KtiDQUgsmPhoGrc7X6JM9nza9hTm6XB");
        language.setAlpha2("DY");
        language.setLanguageType("QoZiQhC59Z60iHoaMJnUvgVmJY2Rxz31");
        language.setLanguageDescription("Gy2wm0SEL034fLrrXR223IfY6bkj6GZv1xZeUjv3OLvvwCjDVd");
        language.setAlpha4("HChb");
        language.setAlpha3("aSZ");
        language.setAlpha4parentid(3);
        language.setLanguage("H67ISn5a04mb2mYpFfn4HXRfGKAcXDZ2amJC4W844Leln6oQ2c");
        Language LanguageTest = new Language();
        if (isSave) {
            LanguageTest = languageRepository.save(language);
            map.put("LanguagePrimaryKey", language._getPrimarykey());
        }
        timezone.setTimeZoneId(null);
        corecontacts.setTimezone(isSave ? timezoneRepository.save(timezone) : timezone);
        if (isSave) {
            map.put("TimezonePrimaryKey", timezone._getPrimarykey());
        }
        corecontacts.setPhoneNumber("JI7twcWIbTvg8SPgkxj8");
        corecontacts.setGenderId((java.lang.String) GenderTest._getPrimarykey()); /* ******Adding refrenced table data */
        corecontacts.setMiddleName("T9ewYNX6RMIcmvNNK00NqjFAhrvm1RlpDli90ql3lCarLUYGBg");
        corecontacts.setApproximateDOB(new java.sql.Timestamp(1464879981424l));
        corecontacts.setEmailId("5iG6WyCwDsEYssfQz4BtcuWj3QA6GV8kXTyv4vo89bk6zVudTX");
        corecontacts.setNativeLastName("dKYJjMkQL5KDyLnrCfpscA8pJIEjPn14LEtM7ZrW8rik4MtFoS");
        corecontacts.setDateofbirth(new java.sql.Timestamp(1464879981424l));
        corecontacts.setNativeMiddleName("XGzpXzxYgOVSQsTxHiaaHHvxoBKWa5sgAbwh6hk1Qvbv2ArLcN");
        corecontacts.setTitleId((java.lang.String) TitleTest._getPrimarykey()); /* ******Adding refrenced table data */
        corecontacts.setAge(122);
        corecontacts.setLastName("aOSKUGx6Fwis1bHnCUv7RhaKgn6oLJIhCQ5aMbqkJ0RMWbChNp");
        corecontacts.setNativeFirstName("RWAdauaiFudc1Kv0enYXE87K9RM0nIlA2Bt0plkJvOCxgMoMtG");
        corecontacts.setNativeLanguageCode((java.lang.String) LanguageTest._getPrimarykey()); /* ******Adding refrenced table data */
        corecontacts.setNativeTitle("axU0bIWrumQeyyfgE4SzZnGjpGyGU5Gu7HkRH5y8p1s1h05NpS");
        corecontacts.setFirstName("DlwAg7F9lPttuAnwxCiVeWhy84Yd19VS9b3813KMcL1tQ20wMW");
        java.util.List<Address> listOfAddress = new java.util.ArrayList<Address>();
        Address address = new Address();
        Country country = new Country();
        country.setCurrencyCode("FYZ");
        country.setCurrencySymbol("TnPprsmTqpbnOHrnOB7lBuAUkEOLIGCe");
        country.setCapital("1lyXDAlXfEyr3eoVS1SeawLrd03TZIU6");
        country.setIsoNumeric(633);
        country.setCountryCode2("EBQ");
        country.setCapitalLatitude(4);
        country.setCapitalLongitude(3);
        country.setCountryName("RMgP0FK8qgJy6DYlCzamfhWl94G9IwGxmzrPZwdRpWrla8Ykht");
        country.setCountryCode1("rqt");
        country.setCountryFlag("9xUF8MW1CBJkxkfWfxuM4aqiBPDOae0NmRUaumiGQK5kHEJFIw");
        country.setCurrencyName("WNP3kAKyD4tEEpiufXu9dD5NFMMQK56UDSESFkLKH7z5mSZZSu");
        Country CountryTest = new Country();
        if (isSave) {
            CountryTest = countryRepository.save(country);
            map.put("CountryPrimaryKey", country._getPrimarykey());
        }
        City city = new City();
        city.setCityCode(3);
        city.setCityCodeChar2("2C8XgfStu3li0Sj50ntVaWQAXTtCKkx5");
        city.setCityLongitude(10);
        State state = new State();
        state.setStateCapital("0b7xAejNkFFRlrCxzH3yrt3XiiRskxnd17NJZECC3CJ5iwOqnp");
        state.setStateCapital("zAGZc3fLAtgFwUuuScXT2tnRT5XCngkeRVgPkAb1kb2Y7QBW4U");
        state.setCountryId((java.lang.String) CountryTest._getPrimarykey()); /* ******Adding refrenced table data */
        state.setStateName("yrrbhtVb23WIZRa3pONckhfJVKfFic1KM5izPDvEWNxVpVDRTk");
        state.setStateCode(2);
        state.setStateCapitalLatitude(1);
        state.setStateFlag("8w5O0rWbHViMXqj3ygdOLt1iQdfhaLCib3sIko4h3MsFGaLZXg");
        state.setStateCodeChar2("i9iC3QY1D5mRj1ahfKLAyYomniHhdz4N");
        state.setStateDescription("bPilJXhFZoZeTnG1FWeNliJfxOydKLLOd5n8ldq0Nm2xREKZ5r");
        state.setStateCapitalLongitude(4);
        state.setStateCodeChar3("mCdxMBl4f3oT4W4CcSAehvwCpGtC2zgR");
        State StateTest = new State();
        if (isSave) {
            StateTest = stateRepository.save(state);
            map.put("StatePrimaryKey", state._getPrimarykey());
        }
        city.setCityCode(2);
        city.setCityCodeChar2("7gmTZTBQtfHUc3cr8La70fgINrRRam8j");
        city.setCityLongitude(4);
        city.setCountryId((java.lang.String) CountryTest._getPrimarykey()); /* ******Adding refrenced table data */
        city.setCityLatitude(6);
        city.setCityName("hx0YloTr9zhI3JMNO8uHGXJqj1jJnhaBk3kvbJk70ZyaMJkceu");
        city.setCityFlag("rO3388IdX3iDRnk8iULDl9RseOvNIjmXm8o3NxVGFmTHhq1MDg");
        city.setCityDescription("EPREgaXo6BdF8J5gVLNnkHfCmOOVBRSfLLy97zSoC0idZ0JkdU");
        city.setStateId((java.lang.String) StateTest._getPrimarykey()); /* ******Adding refrenced table data */
        City CityTest = new City();
        if (isSave) {
            CityTest = cityRepository.save(city);
            map.put("CityPrimaryKey", city._getPrimarykey());
        }
        AddressType addresstype = new AddressType();
        addresstype.setAddressTypeIcon("mrJ0PUH8kNlPNuerQJG1FKnBEsegdoIxaqV7BrZi2EXkaZzNle");
        addresstype.setAddressType("PNI4y0ZNJToOCw2Yivl7ArLar9UHtcBOLRMgxXijtFq8tb89g8");
        addresstype.setAddressTypeDesc("93YfYP6ZNtK5RMIzoHmqIUEkqRrZfMJoE7Z0sjDP6Eumo0P1iA");
        AddressType AddressTypeTest = new AddressType();
        if (isSave) {
            AddressTypeTest = addresstypeRepository.save(addresstype);
            map.put("AddressTypePrimaryKey", addresstype._getPrimarykey());
        }
        address.setCountryId((java.lang.String) CountryTest._getPrimarykey()); /* ******Adding refrenced table data */
        address.setLatitude("nuzyyLbk4P2fl9F3H6gTwhocjfToCCbTSHpeAAuOsmB86Y5sZw");
        address.setZipcode("8YOZSm");
        address.setAddress2("kdbp1qXiXCfhXX2gXyZgejuNp6FqSbxh2l8EUoqx1o3zuKFaKg");
        address.setLongitude("xkvYdUyW42S9rNaZEcXoAtL2x1pphEOFqeLoDRLXeZ9JSiBjb6");
        address.setCityId((java.lang.String) CityTest._getPrimarykey()); /* ******Adding refrenced table data */
        address.setAddress1("fZoQWc6LGvPjb9nX6qWMC608shm39dOZIHNZ68CyRH3Zwb79na");
        address.setAddress3("9x9Vz7oRFMm3EDl2TZvchrHoofql8hyqnKDzyS1cyQGb39zuSa");
        address.setAddressLabel("eGCtAnxHHcH");
        address.setAddressTypeId((java.lang.String) AddressTypeTest._getPrimarykey()); /* ******Adding refrenced table data */
        address.setStateId((java.lang.String) StateTest._getPrimarykey()); /* ******Adding refrenced table data */
        listOfAddress.add(address);
        corecontacts.addAllAddress(listOfAddress);
        java.util.List<CommunicationData> listOfCommunicationData = new java.util.ArrayList<CommunicationData>();
        CommunicationData communicationdata = new CommunicationData();
        CommunicationType communicationtype = new CommunicationType();
        communicationtype.setCommTypeDescription("qVpMhFl5gm9aTL7IBzgNbTpnpThgOr7YWviM643tzr7ACDqz9t");
        communicationtype.setCommTypeName("jLZKileTWgwW8MaNb6ZZns7PsL6dlrKfybM1mO8ITiSuRI1tno");
        CommunicationGroup communicationgroup = new CommunicationGroup();
        communicationgroup.setCommGroupName("Ef1V5NX8Xbt6uLsaiAgcsOgicFJoezlPeOtdwEbQ652rFIR4Wk");
        communicationgroup.setCommGroupDescription("0jN8rIgFlTkLWj02vrpT3G8MUkmzm6KnrSGVG30z0TGVDWHSNA");
        CommunicationGroup CommunicationGroupTest = new CommunicationGroup();
        if (isSave) {
            CommunicationGroupTest = communicationgroupRepository.save(communicationgroup);
            map.put("CommunicationGroupPrimaryKey", communicationgroup._getPrimarykey());
        }
        communicationtype.setCommTypeDescription("b60PC7HVv1i7z8hq49d9mYVTVFqdQyBLL7ElNzA3NqBwjg2PKU");
        communicationtype.setCommTypeName("8dRNHf8GoGtEshdZGa1Erj8oXZAUEE5qHlnTFygfGqaiX0X9Tp");
        communicationtype.setCommGroupId((java.lang.String) CommunicationGroupTest._getPrimarykey()); /* ******Adding refrenced table data */
        CommunicationType CommunicationTypeTest = new CommunicationType();
        if (isSave) {
            CommunicationTypeTest = communicationtypeRepository.save(communicationtype);
            map.put("CommunicationTypePrimaryKey", communicationtype._getPrimarykey());
        }
        communicationdata.setCommType((java.lang.String) CommunicationTypeTest._getPrimarykey()); /* ******Adding refrenced table data */
        communicationdata.setCommGroupId((java.lang.String) CommunicationGroupTest._getPrimarykey());
        communicationdata.setCommData("7exyobhrLa");
        listOfCommunicationData.add(communicationdata);
        corecontacts.addAllCommunicationData(listOfCommunicationData);
        Login login = new Login();
        login.setServerAuthText("tywfUV3tLYcXn7yI");
        user.setUserId(null);
        login.setUser(user);
        login.setServerAuthImage("eVWEwOrKBHNZsmAZgkLTz8huEDhCgy5W");
        corecontacts.setContactId(null);
        login.setCoreContacts(corecontacts);
        login.setLoginId("1utJcl9nLy303SIldpjsq8tPKJ48stYe7qs6a6j7hgcnS78W87");
        login.setFailedLoginAttempts(5);
        login.setIsAuthenticated(true);
        login.setEntityValidator(entityValidator);
        return login;
    }

    @Test
    public void test1Save() {
        try {
            Login login = createLogin(true);
            login.setEntityAudit(1, "xyz", RECORD_TYPE.ADD);
            login.isValid();
            loginRepository.save(login);
            map.put("LoginPrimaryKey", login._getPrimarykey());
            map.put("UserPrimaryKey", login.getUser()._getPrimarykey());
            map.put("CoreContactsPrimaryKey", login.getCoreContacts()._getPrimarykey());
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Autowired
    private UserRepository<User> userRepository;

    @Autowired
    private UserAccessDomainRepository<UserAccessDomain> useraccessdomainRepository;

    @Autowired
    private UserAccessLevelRepository<UserAccessLevel> useraccesslevelRepository;

    @Autowired
    private QuestionRepository<Question> questionRepository;

    @Autowired
    private CoreContactsRepository<CoreContacts> corecontactsRepository;

    @Autowired
    private TimezoneRepository<Timezone> timezoneRepository;

    @Autowired
    private GenderRepository<Gender> genderRepository;

    @Autowired
    private TitleRepository<Title> titleRepository;

    @Autowired
    private LanguageRepository<Language> languageRepository;

    @Autowired
    private AddressRepository<Address> addressRepository;

    @Autowired
    private CountryRepository<Country> countryRepository;

    @Autowired
    private CityRepository<City> cityRepository;

    @Autowired
    private StateRepository<State> stateRepository;

    @Autowired
    private AddressTypeRepository<AddressType> addresstypeRepository;

    @Autowired
    private CommunicationTypeRepository<CommunicationType> communicationtypeRepository;

    @Autowired
    private CommunicationGroupRepository<CommunicationGroup> communicationgroupRepository;

    @Test
    public void test2Update() {
        try {
            org.junit.Assert.assertNotNull(map.get("LoginPrimaryKey"));
            Login login = loginRepository.findById((java.lang.String) map.get("LoginPrimaryKey"));
            login.setServerAuthText("HRHbx8cVp5nXujsw");
            login.setServerAuthImage("dUcbyz1wVBmGRcLYoIHtj4lkRmzhzkdM");
            login.setVersionId(1);
            login.setLoginId("C7O8KtVykcrB5sriwBmgqcG0cj91yJ2hnzMDAdkzK2cUPSa1fB");
            login.setFailedLoginAttempts(6);
            login.setEntityAudit(1, "xyz", RECORD_TYPE.UPDATE);
            loginRepository.update(login);
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test3FindById() {
        try {
            org.junit.Assert.assertNotNull(map.get("LoginPrimaryKey"));
            loginRepository.findById((java.lang.String) map.get("LoginPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testNQFindMappedUser() {
        try {
            loginRepository.FindMappedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNQFindUnMappedUser() {
        try {
            loginRepository.FindUnMappedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test6Delete() {
        try {
            org.junit.Assert.assertNotNull(map.get("LoginPrimaryKey"));
            loginRepository.delete((java.lang.String) map.get("LoginPrimaryKey")); /* Deleting refrenced data */
            communicationtypeRepository.delete((java.lang.String) map.get("CommunicationTypePrimaryKey")); /* Deleting refrenced data */
            communicationgroupRepository.delete((java.lang.String) map.get("CommunicationGroupPrimaryKey")); /* Deleting refrenced data */
            addresstypeRepository.delete((java.lang.String) map.get("AddressTypePrimaryKey")); /* Deleting refrenced data */
            cityRepository.delete((java.lang.String) map.get("CityPrimaryKey")); /* Deleting refrenced data */
            stateRepository.delete((java.lang.String) map.get("StatePrimaryKey")); /* Deleting refrenced data */
            countryRepository.delete((java.lang.String) map.get("CountryPrimaryKey")); /* Deleting refrenced data */
            languageRepository.delete((java.lang.String) map.get("LanguagePrimaryKey")); /* Deleting refrenced data */
            titleRepository.delete((java.lang.String) map.get("TitlePrimaryKey")); /* Deleting refrenced data */
            genderRepository.delete((java.lang.String) map.get("GenderPrimaryKey")); /* Deleting refrenced data */
            timezoneRepository.delete((java.lang.String) map.get("TimezonePrimaryKey")); /* Deleting refrenced data */
            questionRepository.delete((java.lang.String) map.get("QuestionPrimaryKey")); /* Deleting refrenced data */
            useraccesslevelRepository.delete((java.lang.String) map.get("UserAccessLevelPrimaryKey")); /* Deleting refrenced data */
            useraccessdomainRepository.delete((java.lang.String) map.get("UserAccessDomainPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    private void validateLogin(EntityTestCriteria contraints, Login login) throws Exception {
        if (contraints.getRuleType() == MIN_MAX) {
            login.isValid();
        } else if (contraints.getRuleType() == NOT_NULL) {
            login.isValid();
        } else if (contraints.getRuleType() == REGEX) {
            login.isValid();
        } else if (contraints.getRuleType() == UNIQUE) {
            loginRepository.save(login);
        }
    }

    private List<EntityTestCriteria> addingListOfFieldForNegativeTesting() {
        List<EntityTestCriteria> entityContraints = new java.util.ArrayList<EntityTestCriteria>();
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 1, "loginId", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 2, "loginId", "j3MoF1OCKIv3vTik0LlyceQ0fDOqriKPiMrK9WR9HHof5Hp8mSf2TiOFPV0WGnid8D6QOBrAQGOsw01ermi3BQrCCMX6MRLqNSaU6H1a9EBMfuaT0hLmgXqYjoHQv5jvmzfXT9iPjXobfedRI7WD8exJervyCE9rOjPF3YphKMoUeJ7VQjt8nFYsA4U7fzEbUeL86hsjq"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 3, "serverAuthImage", "Xc3GC65RzrkXR60C900qBv51LjvYINQyJ"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 4, "serverAuthText", "1m3eno5jdPmjINRdT"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 5, "failedLoginAttempts", 21));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 6, "isAuthenticated", true));
        return entityContraints;
    }

    @Test
    public void test5NegativeTesting() throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, Exception {
        int failureCount = 0;
        for (EntityTestCriteria contraints : this.entityContraint) {
            try {
                Login login = createLogin(false);
                java.lang.reflect.Field field = null;
                if (!contraints.getFieldName().equalsIgnoreCase("CombineUniqueKey")) {
                    field = login.getClass().getDeclaredField(contraints.getFieldName());
                }
                switch(((contraints.getTestId()))) {
                    case 0:
                        break;
                    case 1:
                        field.setAccessible(true);
                        field.set(login, null);
                        validateLogin(contraints, login);
                        failureCount++;
                        break;
                    case 2:
                        login.setLoginId(contraints.getNegativeValue().toString());
                        validateLogin(contraints, login);
                        failureCount++;
                        break;
                    case 3:
                        login.setServerAuthImage(contraints.getNegativeValue().toString());
                        validateLogin(contraints, login);
                        failureCount++;
                        break;
                    case 4:
                        login.setServerAuthText(contraints.getNegativeValue().toString());
                        validateLogin(contraints, login);
                        failureCount++;
                        break;
                    case 5:
                        login.setFailedLoginAttempts(Integer.parseInt(contraints.getNegativeValue().toString()));
                        validateLogin(contraints, login);
                        failureCount++;
                        break;
                    case 6:
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

package com.app.server.service.organization.contactmanagement;
import com.app.server.service.EntityTestCriteria;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.app.config.WebConfigExtended;
import org.springframework.test.context.ContextConfiguration;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.TestExecutionListeners;
import com.app.server.repository.organization.contactmanagement.CoreContactsRepository;
import com.app.shared.organization.contactmanagement.CoreContacts;
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
public class CoreContactsTestCase extends EntityTestCriteria {

    @Autowired
    private CoreContactsRepository<CoreContacts> corecontactsRepository;

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

    private CoreContacts createCoreContacts(Boolean isSave) throws Exception {
        Timezone timezone = new Timezone();
        timezone.setUtcdifference(10);
        timezone.setCountry("pL0ESV5PkE5pcpQnwJO4Q3JmTE9V1iNgTfc3h3LYilVSdsMONZ");
        timezone.setTimeZoneLabel("JlURMlG9HQaz4Hwja6xJFE3QoQNAs4632MHK9ifyFFN6X8m57m");
        timezone.setCities("825qwexx6PorUfLXXT5r0FiYT48ERwigTodfjUQoz9HeNpBSoH");
        timezone.setGmtLabel("mEzBNXaxNoZvDQkhTmka84L9oylY9uHneiJ9X0aDzqvHxntXrs");
        Gender gender = new Gender();
        gender.setGender("a41rrkwnxa9pwW2pU8RnHrNlwH7NvKMSpe56ArerGFjWrxJLOc");
        Gender GenderTest = new Gender();
        if (isSave) {
            GenderTest = genderRepository.save(gender);
            map.put("GenderPrimaryKey", gender._getPrimarykey());
        }
        Title title = new Title();
        title.setTitles("ZvMAEoeyKJKxA96qoHhKfmAp939h96wFEhvPc4uG4BdwfvTgWx");
        Title TitleTest = new Title();
        if (isSave) {
            TitleTest = titleRepository.save(title);
            map.put("TitlePrimaryKey", title._getPrimarykey());
        }
        Language language = new Language();
        language.setLanguageIcon("87DDE9DwkH6N99nzUqmt4LWMHZ2Yja4EBT2Wr6TilHKopuZtIk");
        language.setAlpha2("dZ");
        language.setLanguageType("ibJNEmcf6sl5oY0PMz4kXc28fzbetSRg");
        language.setLanguageDescription("KH6IxPji9G4yHKxlbg8CWRqcDT0Zb2GRZByJe9XkNK5s4J4bDr");
        language.setAlpha4("XQRn");
        language.setAlpha3("a7x");
        language.setAlpha4parentid(3);
        language.setLanguage("xHVXJghyRbEjKA7KvflI2NpjHo3plmeLMhSN03vkZIojb9kTQO");
        Language LanguageTest = new Language();
        if (isSave) {
            LanguageTest = languageRepository.save(language);
            map.put("LanguagePrimaryKey", language._getPrimarykey());
        }
        CoreContacts corecontacts = new CoreContacts();
        timezone.setTimeZoneId(null);
        corecontacts.setTimezone(isSave ? timezoneRepository.save(timezone) : timezone);
        if (isSave) {
            map.put("TimezonePrimaryKey", timezone._getPrimarykey());
        }
        corecontacts.setPhoneNumber("0xRk0Yj3KiDDNONTvzRN");
        corecontacts.setGenderId((java.lang.String) GenderTest._getPrimarykey()); /* ******Adding refrenced table data */
        corecontacts.setMiddleName("1xk2wv3CWqB5hO5l8nxB8J9lLKdxM7QxzBrSW9ILMNfSXd6RJB");
        corecontacts.setApproximateDOB(new java.sql.Timestamp(1464879969300l));
        corecontacts.setEmailId("4cqevi9csLM5xewB9mXklP8u9OZnVZaCenQL8MrBPhpdh7c6jW");
        corecontacts.setNativeLastName("wdbm7o7UEKYsO0xZTMK53gN41NOymcg7km9jVKaa6iWtxYdiO1");
        corecontacts.setDateofbirth(new java.sql.Timestamp(1464879969300l));
        corecontacts.setNativeMiddleName("rO9fOtLuNRiURkKply15M9s00ZfR8NkxWFm7d46E9pBe9HK53J");
        corecontacts.setTitleId((java.lang.String) TitleTest._getPrimarykey()); /* ******Adding refrenced table data */
        corecontacts.setAge(104);
        corecontacts.setLastName("YubN00AgOdcA80aPfpdwCrXQQhCOx71FAzyq8GEZ4YWhHW6L3H");
        corecontacts.setNativeFirstName("NQ9DPnKb1kAF7iM7VOKIRvyyiZgbypRagMHGQtQ09k1OjwDs6Z");
        corecontacts.setNativeLanguageCode((java.lang.String) LanguageTest._getPrimarykey()); /* ******Adding refrenced table data */
        corecontacts.setNativeTitle("85jZWxTUhgguqfFsHds6sJijv2qRODWBAZtqTDgYVDRQ7YgZWj");
        corecontacts.setFirstName("FtQncBvfzq1kwJnPysgsMGvqseK4J4vkY9gEF3GaS3ssWHTk2w");
        java.util.List<Address> listOfAddress = new java.util.ArrayList<Address>();
        Address address = new Address();
        Country country = new Country();
        country.setCurrencyCode("4SY");
        country.setCurrencySymbol("cjtf9CYFiD1YBNSsk0BnvdcOwZgUCzQB");
        country.setCapital("zd1jBc15feKLpjxqPbF9sFvXRd5i04HB");
        country.setIsoNumeric(640);
        country.setCountryCode2("l66");
        country.setCapitalLatitude(5);
        country.setCapitalLongitude(1);
        country.setCountryName("Uf7FYAnkA0IIoXjHIx82JVJysWjR9HSXk3dib1zTwfVeldH5D4");
        country.setCountryCode1("u3M");
        country.setCountryFlag("X5TYBMLU0aiuiXpwNCBaw6dHdLbmO86i4f5fmiTboqPLcM9wyK");
        country.setCurrencyName("14xIVjOXY5RAm0u9OBrLcabMFutTACN8cMeodxI8k9Wd6zduSq");
        Country CountryTest = new Country();
        if (isSave) {
            CountryTest = countryRepository.save(country);
            map.put("CountryPrimaryKey", country._getPrimarykey());
        }
        City city = new City();
        city.setCityCode(3);
        city.setCityCodeChar2("ZXB2MKh9qbrxUm9y7RUbzDfPzpH6M1zQ");
        city.setCityLongitude(6);
        State state = new State();
        state.setStateCapital("spdjivigVHIBih9RzhY3dktZDWFwzL8OEtpmyuZHrmP1Pqbv3Q");
        state.setStateCapital("wmleNxf7APoZcwKjtEp2eyUCBuI1gVqMDo1lUfnkFaScxmhUuD");
        state.setCountryId((java.lang.String) CountryTest._getPrimarykey()); /* ******Adding refrenced table data */
        state.setStateName("sXyilJIK1sYqDFU9hiXGZ3CzUOjHjr7hhNl1Zi5sEXP5NTUHR5");
        state.setStateCode(1);
        state.setStateCapitalLatitude(6);
        state.setStateFlag("00iBl7e7ue0m8rkDvKE4mqkZVveVw3JejIEZf4uyQLkNUJwYQI");
        state.setStateCodeChar2("k7JGYKmDE9LvVPi1wuJWsfbFdqvZGVOD");
        state.setStateDescription("SCxyo22A1XeLfEpa9rIaJcdh9YUd8YfsHn8Ci2l3TxSxT2AeVy");
        state.setStateCapitalLongitude(7);
        state.setStateCodeChar3("pjq2hInztdUI3xxR13gwiUUm8dMrDM7H");
        State StateTest = new State();
        if (isSave) {
            StateTest = stateRepository.save(state);
            map.put("StatePrimaryKey", state._getPrimarykey());
        }
        city.setCityCode(2);
        city.setCityCodeChar2("sUWklfWS7KqfYqfhmJMeEuTF8hQ4UIpf");
        city.setCityLongitude(1);
        city.setCountryId((java.lang.String) CountryTest._getPrimarykey()); /* ******Adding refrenced table data */
        city.setCityLatitude(4);
        city.setCityName("i1CvdM1pXtKONaZ8UCSFSdvf51g7HItM24tZbC6iZsHIel5Pku");
        city.setCityFlag("EXLxeQZxP3Ipe0LHPgJTzyZbXzKoHIqRmVUxNujLldfCUIkCVE");
        city.setCityDescription("o4RGgSPUb4vgr8TIjwSCilVLAO886QfC0NVgIdHUQEDcnPkMXz");
        city.setStateId((java.lang.String) StateTest._getPrimarykey()); /* ******Adding refrenced table data */
        City CityTest = new City();
        if (isSave) {
            CityTest = cityRepository.save(city);
            map.put("CityPrimaryKey", city._getPrimarykey());
        }
        AddressType addresstype = new AddressType();
        addresstype.setAddressTypeIcon("aVnb5tNIhzp6S6GdtoKG4Wl8jF0pKfGiOTMAuiHox0KtNfFrme");
        addresstype.setAddressType("C6clmmqNIcbvMyxHEOFZjg4l4lVmF6KOoOGAxkUsXtMVBwKqj4");
        addresstype.setAddressTypeDesc("aoPJPO8cCMERrjBVDfzoUh0yxcS0qfqo6cRq1GW5b1zSUmUYOq");
        AddressType AddressTypeTest = new AddressType();
        if (isSave) {
            AddressTypeTest = addresstypeRepository.save(addresstype);
            map.put("AddressTypePrimaryKey", addresstype._getPrimarykey());
        }
        address.setCountryId((java.lang.String) CountryTest._getPrimarykey()); /* ******Adding refrenced table data */
        address.setLatitude("Irl9gbVZ97bIzWazNY8GIdIgl9eCGMQB0jQ4AdlDbCXFtMpRSP");
        address.setZipcode("rRV4uo");
        address.setAddress2("coatR5t8UcrgUGnUu5CESqOMZMs2IyehxUR3NHV786L3D0O1cR");
        address.setLongitude("G3rCXS70lIRnvqMEwpNK2omFH32xgShL4WHAoHDEjmJzsXVgkx");
        address.setCityId((java.lang.String) CityTest._getPrimarykey()); /* ******Adding refrenced table data */
        address.setAddress1("iN1c1Rw9VezwggLdhXrstADdBKWMkVb9T5YByqeZVjroKCOIrb");
        address.setAddress3("amPSEByyNzZBdp71c3wiHwsYhaqy6x4cflkwCyPx4unHF6h5gv");
        address.setAddressLabel("0gcHLy3gH2Z");
        address.setAddressTypeId((java.lang.String) AddressTypeTest._getPrimarykey()); /* ******Adding refrenced table data */
        address.setStateId((java.lang.String) StateTest._getPrimarykey()); /* ******Adding refrenced table data */
        listOfAddress.add(address);
        corecontacts.addAllAddress(listOfAddress);
        java.util.List<CommunicationData> listOfCommunicationData = new java.util.ArrayList<CommunicationData>();
        CommunicationData communicationdata = new CommunicationData();
        CommunicationType communicationtype = new CommunicationType();
        communicationtype.setCommTypeDescription("EfbbRtCqxiPdGF5uMHYFSR44ZZoRMXlpEuGNcvh847m9FHPG8v");
        communicationtype.setCommTypeName("XjY0pIFmvu1YIQdpilPQaWn6IuyEsACNzpw8nFYFtYS7kXT71a");
        CommunicationGroup communicationgroup = new CommunicationGroup();
        communicationgroup.setCommGroupName("jMcbB6BQHlHtCmgV2olOKWiOTUG3wPUm4BPzK6pLPmIIXkwFm6");
        communicationgroup.setCommGroupDescription("AI0G3VIcHdvD3Qi7FfKwmXXcIw8WBiOiXEbxtu4w7u2HisUsRW");
        CommunicationGroup CommunicationGroupTest = new CommunicationGroup();
        if (isSave) {
            CommunicationGroupTest = communicationgroupRepository.save(communicationgroup);
            map.put("CommunicationGroupPrimaryKey", communicationgroup._getPrimarykey());
        }
        communicationtype.setCommTypeDescription("qxiKLbYM6pmDizL2zuOZUWu7nv5IL0OQ8e9elaeURPIeeDpxJI");
        communicationtype.setCommTypeName("goyMUNKjz2TlAs5DDiR87Uzyxh3ws3C0QIJIcglTcEcPTwJvdC");
        communicationtype.setCommGroupId((java.lang.String) CommunicationGroupTest._getPrimarykey()); /* ******Adding refrenced table data */
        CommunicationType CommunicationTypeTest = new CommunicationType();
        if (isSave) {
            CommunicationTypeTest = communicationtypeRepository.save(communicationtype);
            map.put("CommunicationTypePrimaryKey", communicationtype._getPrimarykey());
        }
        communicationdata.setCommType((java.lang.String) CommunicationTypeTest._getPrimarykey()); /* ******Adding refrenced table data */
        communicationdata.setCommGroupId((java.lang.String) CommunicationGroupTest._getPrimarykey());
        communicationdata.setCommData("aANWFOlvxw");
        listOfCommunicationData.add(communicationdata);
        corecontacts.addAllCommunicationData(listOfCommunicationData);
        corecontacts.setEntityValidator(entityValidator);
        return corecontacts;
    }

    @Test
    public void test1Save() {
        try {
            CoreContacts corecontacts = createCoreContacts(true);
            corecontacts.setEntityAudit(1, "xyz", RECORD_TYPE.ADD);
            corecontacts.isValid();
            corecontactsRepository.save(corecontacts);
            map.put("CoreContactsPrimaryKey", corecontacts._getPrimarykey());
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

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
            org.junit.Assert.assertNotNull(map.get("CoreContactsPrimaryKey"));
            CoreContacts corecontacts = corecontactsRepository.findById((java.lang.String) map.get("CoreContactsPrimaryKey"));
            corecontacts.setPhoneNumber("9EEaHKvexJTtN1WKS0Rv");
            corecontacts.setMiddleName("DJKpCZDqUgnPqUX0HTsPgVhN1E6yWFZ3uxnMPwOG6x1i29Apst");
            corecontacts.setApproximateDOB(new java.sql.Timestamp(1464879969808l));
            corecontacts.setEmailId("WlPEBFPj1li17U1dFZiK4rGBUeuXJzeJGSJYwvDTIy0BIZf4PI");
            corecontacts.setNativeLastName("iP6cbgou4Va1BJs8KLmSHHnUUPRZCp2MidxnVZ45Mrwct2YkGh");
            corecontacts.setDateofbirth(new java.sql.Timestamp(1464879969838l));
            corecontacts.setNativeMiddleName("NdlFr5ogPyqr2GNu2nzKgdFlLkl22VN35iOleBpgrWrr93Ripn");
            corecontacts.setAge(109);
            corecontacts.setLastName("1wr5lSwStoYvJHaDW4ujvCF6kosuTxdhw4QFMMyGFHz6kiExXq");
            corecontacts.setNativeFirstName("RdXf8VVcSyWRMndPpV7EYO8sn3GpMZIjMTzX0LYmDvw0YoUEfM");
            corecontacts.setNativeTitle("eicIre5sO8K7YyhU5oFS86GnB8PdIILqvCJI8uU2oeFAVZdxcM");
            corecontacts.setVersionId(1);
            corecontacts.setFirstName("3pmX2HbPjIwgbELcFaYFPHMPlxYxd0gVBdJ5jkGR9zhvjoky02");
            corecontacts.setEntityAudit(1, "xyz", RECORD_TYPE.UPDATE);
            corecontactsRepository.update(corecontacts);
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test3findBygenderId() {
        try {
            java.util.List<CoreContacts> listofgenderId = corecontactsRepository.findByGenderId((java.lang.String) map.get("GenderPrimaryKey"));
            if (listofgenderId.size() == 0) {
                org.junit.Assert.fail("Query did not return any records.");
            }
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test3findBytitleId() {
        try {
            java.util.List<CoreContacts> listoftitleId = corecontactsRepository.findByTitleId((java.lang.String) map.get("TitlePrimaryKey"));
            if (listoftitleId.size() == 0) {
                org.junit.Assert.fail("Query did not return any records.");
            }
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test3findBynativeLanguageCode() {
        try {
            java.util.List<CoreContacts> listofnativeLanguageCode = corecontactsRepository.findByNativeLanguageCode((java.lang.String) map.get("LanguagePrimaryKey"));
            if (listofnativeLanguageCode.size() == 0) {
                org.junit.Assert.fail("Query did not return any records.");
            }
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test3FindById() {
        try {
            org.junit.Assert.assertNotNull(map.get("CoreContactsPrimaryKey"));
            corecontactsRepository.findById((java.lang.String) map.get("CoreContactsPrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test6Delete() {
        try {
            org.junit.Assert.assertNotNull(map.get("CoreContactsPrimaryKey"));
            corecontactsRepository.delete((java.lang.String) map.get("CoreContactsPrimaryKey")); /* Deleting refrenced data */
            communicationtypeRepository.delete((java.lang.String) map.get("CommunicationTypePrimaryKey")); /* Deleting refrenced data */
            communicationgroupRepository.delete((java.lang.String) map.get("CommunicationGroupPrimaryKey")); /* Deleting refrenced data */
            addresstypeRepository.delete((java.lang.String) map.get("AddressTypePrimaryKey")); /* Deleting refrenced data */
            cityRepository.delete((java.lang.String) map.get("CityPrimaryKey")); /* Deleting refrenced data */
            stateRepository.delete((java.lang.String) map.get("StatePrimaryKey")); /* Deleting refrenced data */
            countryRepository.delete((java.lang.String) map.get("CountryPrimaryKey")); /* Deleting refrenced data */
            languageRepository.delete((java.lang.String) map.get("LanguagePrimaryKey")); /* Deleting refrenced data */
            titleRepository.delete((java.lang.String) map.get("TitlePrimaryKey")); /* Deleting refrenced data */
            genderRepository.delete((java.lang.String) map.get("GenderPrimaryKey")); /* Deleting refrenced data */
            timezoneRepository.delete((java.lang.String) map.get("TimezonePrimaryKey"));
        } catch (Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    private void validateCoreContacts(EntityTestCriteria contraints, CoreContacts corecontacts) throws Exception {
        if (contraints.getRuleType() == MIN_MAX) {
            corecontacts.isValid();
        } else if (contraints.getRuleType() == NOT_NULL) {
            corecontacts.isValid();
        } else if (contraints.getRuleType() == REGEX) {
            corecontacts.isValid();
        } else if (contraints.getRuleType() == UNIQUE) {
            corecontactsRepository.save(corecontacts);
        }
    }

    private List<EntityTestCriteria> addingListOfFieldForNegativeTesting() {
        List<EntityTestCriteria> entityContraints = new java.util.ArrayList<EntityTestCriteria>();
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 1, "firstName", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 2, "firstName", "0WfNRtIwu5qjBRh4NXAPhT77vUlSug4O5em9f8SlOkLRjkTF1kaByZEBlCRxmXYiMEb6st1DUoskwjxlkXJvMaLvg0EcCPQdhrci9efj7KEJH3XQIqn7Na7Yks8ng1a9PVXJUUl3uaLlz6wlC1kHZyyjkxzg51ZVcNR6TqgE57l7qXaO7zmKdEtkLnPyq9uoD3YPJy4ntk1GsnwTgby3DPcwfvj3625WBqD0wlzSpPNHhSby55qSzsBhDTVgoaNTU"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 3, "middleName", "cjyssWl2uMPFvUXcVe5XFct62NRXa9U9iJSm3jPk0lHxii4cGgUZUKCcrFYmZ1oHbkqiKjRrlLOxBrKBfyEkrvMAtlaqdPzxCS6MzH1WVF8NqYyrjFUpMofRSNUf3Jg9GEHnWXVuFbw8zYIYo10mzzK0mTStcxsaU6RFJAY0NS148E8kLwWpUu0Hm626ivxXD4WOSefRwcCeZVCnqYpYmEBcByR09QHYKycc87FXOVFewGuPoxu9opr1fnj2dRZKi"));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 4, "lastName", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 5, "lastName", "GV30FGBeTcuhtcZf2DLYa0vK29tOYi8enF6mYCAcuLbar7Oonep5bAUF3tErTcmrX4613ICLbQoJvnRqqXGVBD8FTFnvzKpaOYXT6jMGEYkyXzpiJ1cXroM1zgCK00WMvvzrckJGr7wb3mEzkdHnbMB1nOHz1ezf5jAlYPtwiWDrqMGAdhRSAVO8lGLD9EzCqSEGnATLvBwKQcj4d1Dm5Bi0enla4W3L3fGW06sCJCVJO9tOgwA9JBryEFUPh6522"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 6, "nativeTitle", "EPBPHiikCSETtd7YCJnB89ozkmG36WiJldr9aOMotmGBwKCfvi4DLLz5YJ1VOr3CH"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 7, "nativeFirstName", "R8TItlXCsrrna8vFdWMmt0oQ1o0AZRa8Uk0rpQLsWR55Er1U2LcrVmADQWf1jFV8NbFwDDoAdiboiExczfRhzU5BzPFxn2ptuRgXyJ31GXfkNLXFxKXrSNmsXyAxxVkRnoo3dOakmHdOI1XDFYUdvolgs3R04Jaw2SgX6T3bpYGN08fWZhP09miweLlAxVGnGqnRY2BORkEDeP8mPMimk0MfHu92x8vmna6lKeldaaATIk83VT6tzt3fvH3uOIVbP"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 8, "nativeMiddleName", "dVu04ti5j3GKLzMRN8PLKSirOYczMNZvPPZJhA70VCQBvtV4SVuyZfpZBoCDagWZjxOgg6uRJMiO6iK8Tig1feRZ6Ric5Gm5nA256dqbKbkSo7Yl8D4hoP8qFZW31mmcobfhwfZIAIEMduA40XnItlsgHLRzBPTKnIZBHrJwQsWyZd0vILKvSIna4V0fpT9vsy6DARppKegtJI8FqbH5Y1sWF8FJsiUipTt2U5p0MH4GBZOuQqsG06wsFaM4YunjG"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 9, "nativeLastName", "dVNw9b0nft1hqbOiQYxn1H0MdTHwJRKtW9jkZzlZDsES5OF4uB3b1xmOkkML4elMcimJS3IfMZetHz5eGudcIj73fhrerCTNeKk3EL6p1oLJg65UaahTuUoJnHHAyqzGgsqxLWgFlt3osmntJVxLjIXCGj8Y7SwpJy25fIpD6cqLM20evszOB3wap1ePXcYjKJa4WXujfhytKuGrPhqAmrpd7rjwiZ01d2ieKFyDBRDbTkzLxPqWmfa5mTTsN8PRZ"));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 10, "age", 142));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 11, "emailId", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 12, "emailId", "bc1Y5j0Zil1BM4ecejHE4g4HzA1DtzUCxK4w8VGkt8bEHjhB4GaUyJ4Phji5sUYPmakc82QhNek8qWGcF2z0ZOAHUZzS1kviG08Oy64zGRdIN3kMKa5SK9QASypYBnN3vMn8WGMc3XVu8tnm714M7ep7upeyLwBF3YgPsD1frNPfYcRP2pprMkzszsXmvON4w6I5tFfjuzb77e8kxwgDOenuBW4wFnQc1cV29SnWiDbeZ8qVLRw8kLNqHyLYaaSyZ"));
        entityContraints.add(new EntityTestCriteria(NOT_NULL, 13, "phoneNumber", null));
        entityContraints.add(new EntityTestCriteria(MIN_MAX, 14, "phoneNumber", "6d652IFSCkbb6VRFF6ykK"));
        return entityContraints;
    }

    @Test
    public void test5NegativeTesting() throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, Exception {
        int failureCount = 0;
        for (EntityTestCriteria contraints : this.entityContraint) {
            try {
                CoreContacts corecontacts = createCoreContacts(false);
                java.lang.reflect.Field field = null;
                if (!contraints.getFieldName().equalsIgnoreCase("CombineUniqueKey")) {
                    field = corecontacts.getClass().getDeclaredField(contraints.getFieldName());
                }
                switch(((contraints.getTestId()))) {
                    case 0:
                        break;
                    case 1:
                        field.setAccessible(true);
                        field.set(corecontacts, null);
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 2:
                        corecontacts.setFirstName(contraints.getNegativeValue().toString());
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 3:
                        corecontacts.setMiddleName(contraints.getNegativeValue().toString());
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 4:
                        field.setAccessible(true);
                        field.set(corecontacts, null);
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 5:
                        corecontacts.setLastName(contraints.getNegativeValue().toString());
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 6:
                        corecontacts.setNativeTitle(contraints.getNegativeValue().toString());
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 7:
                        corecontacts.setNativeFirstName(contraints.getNegativeValue().toString());
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 8:
                        corecontacts.setNativeMiddleName(contraints.getNegativeValue().toString());
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 9:
                        corecontacts.setNativeLastName(contraints.getNegativeValue().toString());
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 10:
                        corecontacts.setAge(Integer.parseInt(contraints.getNegativeValue().toString()));
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 11:
                        field.setAccessible(true);
                        field.set(corecontacts, null);
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 12:
                        corecontacts.setEmailId(contraints.getNegativeValue().toString());
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 13:
                        field.setAccessible(true);
                        field.set(corecontacts, null);
                        validateCoreContacts(contraints, corecontacts);
                        failureCount++;
                        break;
                    case 14:
                        corecontacts.setPhoneNumber(contraints.getNegativeValue().toString());
                        validateCoreContacts(contraints, corecontacts);
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

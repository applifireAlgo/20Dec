package com.app.server.repository.organization.contactmanagement;
import com.app.server.repository.core.SearchInterfaceImpl;
import com.app.shared.organization.contactmanagement.CoreContacts;
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
import com.app.shared.organization.locationmanagement.Address;
import com.app.shared.organization.contactmanagement.CommunicationData;

@Repository
@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Repository for CoreContacts Transaction table", complexity = Complexity.MEDIUM)
public class CoreContactsRepositoryImpl extends SearchInterfaceImpl implements CoreContactsRepository<CoreContacts> {

    @Autowired
    private ResourceFactoryManagerHelper emfResource;

    private LogManager Log = LogManagerFactory.getInstance(AppLoggerConstant.LOGGER_ID);

    @Autowired
    private RuntimeLogInfoHelper runtimeLogInfoHelper;

    @Override
    @Transactional
    public List<CoreContacts> findAll() throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        java.util.List<com.app.shared.organization.contactmanagement.CoreContacts> query = emanager.createQuery("select u from CoreContacts u where u.systemInfo.activeStatus=1").getResultList();
        Log.out.println("ORGCM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsRepositoryImpl", "findAll", "Total Records Fetched = " + query.size());
        return query;
    }

    @Override
    @Transactional
    public CoreContacts save(CoreContacts entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        java.util.List<com.app.shared.organization.locationmanagement.Address> address = new java.util.ArrayList<com.app.shared.organization.locationmanagement.Address>();
        for (java.util.Iterator iterator = entity.getAddress().iterator(); iterator.hasNext(); ) {
            com.app.shared.organization.locationmanagement.Address childEntity = (com.app.shared.organization.locationmanagement.Address) iterator.next();
            if (childEntity.getPrimaryKey() != null) {
                com.app.shared.organization.locationmanagement.Address ans = emanager.find(Address.class, childEntity.getPrimaryKey());
                address.add(ans);
            } else {
                address.add(childEntity);
            }
        }
        java.util.List<com.app.shared.organization.contactmanagement.CommunicationData> communicationdata = new java.util.ArrayList<com.app.shared.organization.contactmanagement.CommunicationData>();
        for (java.util.Iterator iterator = entity.getCommunicationData().iterator(); iterator.hasNext(); ) {
            com.app.shared.organization.contactmanagement.CommunicationData childEntity = (com.app.shared.organization.contactmanagement.CommunicationData) iterator.next();
            if (childEntity.getPrimaryKey() != null) {
                com.app.shared.organization.contactmanagement.CommunicationData ans = emanager.find(CommunicationData.class, childEntity.getPrimaryKey());
                communicationdata.add(ans);
            } else {
                communicationdata.add(childEntity);
            }
        }
        entity.setAddress(address);
        entity.setCommunicationData(communicationdata);
        emanager.persist(entity);
        Log.out.println("ORGCM322990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsRepositoryImpl", "save", entity);
        return entity;
    }

    @Override
    @Transactional
    public List<CoreContacts> save(List<CoreContacts> entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (int i = 0; i < entity.size(); i++) {
            com.app.shared.organization.contactmanagement.CoreContacts obj = entity.get(i);
            emanager.persist(obj);
        }
        Log.out.println("ORGCM322990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsRepositoryImpl", "saveAll", "Total Records saved = " + entity.size());
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        com.app.shared.organization.contactmanagement.CoreContacts s = emanager.find(com.app.shared.organization.contactmanagement.CoreContacts.class, id);
        emanager.remove(s);
        Log.out.println("ORGCM328990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsRepositoryImpl", "delete", "Record Deleted");
    }

    @Override
    @Transactional
    public void deleteAddress(List<Address> address) {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (com.app.shared.organization.locationmanagement.Address _address : address) {
            com.app.shared.organization.locationmanagement.Address s = emanager.find(com.app.shared.organization.locationmanagement.Address.class, _address.getAddressId());
            emanager.remove(s);
        }
    }

    @Override
    @Transactional
    public void deleteCommunicationData(List<CommunicationData> communicationdata) {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (com.app.shared.organization.contactmanagement.CommunicationData _communicationdata : communicationdata) {
            com.app.shared.organization.contactmanagement.CommunicationData s = emanager.find(com.app.shared.organization.contactmanagement.CommunicationData.class, _communicationdata.getCommDataId());
            emanager.remove(s);
        }
    }

    @Override
    @Transactional
    public void update(CoreContacts entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        emanager.merge(entity);
        Log.out.println("ORGCM321990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsRepositoryImpl", "update", entity);
    }

    @Override
    @Transactional
    public void update(List<CoreContacts> entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (int i = 0; i < entity.size(); i++) {
            com.app.shared.organization.contactmanagement.CoreContacts obj = entity.get(i);
            emanager.merge(obj);
        }
        Log.out.println("ORGCM321990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsRepositoryImpl", "updateAll", "Total Records updated = " + entity.size());
    }

    @Transactional
    public List<CoreContacts> findByTitleId(String titleId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("CoreContacts.findByTitleId");
        query.setParameter("titleId", titleId);
        java.util.List<com.app.shared.organization.contactmanagement.CoreContacts> listOfCoreContacts = query.getResultList();
        Log.out.println("ORGCM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsRepositoryImpl", "findByTitleId", "Total Records Fetched = " + listOfCoreContacts.size());
        return listOfCoreContacts;
    }

    @Transactional
    public List<CoreContacts> findByNativeLanguageCode(String nativeLanguageCode) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("CoreContacts.findByNativeLanguageCode");
        query.setParameter("nativeLanguageCode", nativeLanguageCode);
        java.util.List<com.app.shared.organization.contactmanagement.CoreContacts> listOfCoreContacts = query.getResultList();
        Log.out.println("ORGCM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsRepositoryImpl", "findByNativeLanguageCode", "Total Records Fetched = " + listOfCoreContacts.size());
        return listOfCoreContacts;
    }

    @Transactional
    public List<CoreContacts> findByGenderId(String genderId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("CoreContacts.findByGenderId");
        query.setParameter("genderId", genderId);
        java.util.List<com.app.shared.organization.contactmanagement.CoreContacts> listOfCoreContacts = query.getResultList();
        Log.out.println("ORGCM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsRepositoryImpl", "findByGenderId", "Total Records Fetched = " + listOfCoreContacts.size());
        return listOfCoreContacts;
    }

    @Transactional
    public List<CoreContacts> findByTimeZoneId(String timeZoneId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("CoreContacts.findByTimeZoneId");
        query.setParameter("timeZoneId", timeZoneId);
        java.util.List<com.app.shared.organization.contactmanagement.CoreContacts> listOfCoreContacts = query.getResultList();
        Log.out.println("ORGCM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsRepositoryImpl", "findByTimeZoneId", "Total Records Fetched = " + listOfCoreContacts.size());
        return listOfCoreContacts;
    }

    @Transactional
    public CoreContacts findById(String contactId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("CoreContacts.findById");
        query.setParameter("contactId", contactId);
        com.app.shared.organization.contactmanagement.CoreContacts listOfCoreContacts = (com.app.shared.organization.contactmanagement.CoreContacts) query.getSingleResult();
        Log.out.println("ORGCM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsRepositoryImpl", "findById", "Total Records Fetched = " + listOfCoreContacts);
        return listOfCoreContacts;
    }
}

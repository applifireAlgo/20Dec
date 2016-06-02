package com.app.server.repository.organization.locationmanagement;
import com.app.server.repository.core.SearchInterfaceImpl;
import com.app.shared.organization.locationmanagement.City;
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

@Repository
@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Repository for City Master table Entity", complexity = Complexity.LOW)
public class CityRepositoryImpl extends SearchInterfaceImpl implements CityRepository<City> {

    @Autowired
    private ResourceFactoryManagerHelper emfResource;

    private LogManager Log = LogManagerFactory.getInstance(AppLoggerConstant.LOGGER_ID);

    @Autowired
    private RuntimeLogInfoHelper runtimeLogInfoHelper;

    @Override
    @Transactional
    public List<City> findAll() throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        java.util.List<com.app.shared.organization.locationmanagement.City> query = emanager.createQuery("select u from City u where u.systemInfo.activeStatus=1").getResultList();
        Log.out.println("ORGLM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CityRepositoryImpl", "findAll", "Total Records Fetched = " + query.size());
        return query;
    }

    @Override
    @Transactional
    public City save(City entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        emanager.persist(entity);
        Log.out.println("ORGLM322990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CityRepositoryImpl", "save", entity);
        return entity;
    }

    @Override
    @Transactional
    public List<City> save(List<City> entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (int i = 0; i < entity.size(); i++) {
            com.app.shared.organization.locationmanagement.City obj = entity.get(i);
            emanager.persist(obj);
        }
        Log.out.println("ORGLM322990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CityRepositoryImpl", "saveAll", "Total Records saved = " + entity.size());
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        com.app.shared.organization.locationmanagement.City s = emanager.find(com.app.shared.organization.locationmanagement.City.class, id);
        emanager.remove(s);
        Log.out.println("ORGLM328990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CityRepositoryImpl", "delete", "Record Deleted");
    }

    @Override
    @Transactional
    public void update(City entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        emanager.merge(entity);
        Log.out.println("ORGLM321990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CityRepositoryImpl", "update", entity);
    }

    @Override
    @Transactional
    public void update(List<City> entity) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        for (int i = 0; i < entity.size(); i++) {
            com.app.shared.organization.locationmanagement.City obj = entity.get(i);
            emanager.merge(obj);
        }
        Log.out.println("ORGLM321990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CityRepositoryImpl", "updateAll", "Total Records updated = " + entity.size());
    }

    @Transactional
    public List<City> findByCountryId(String countryId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("City.findByCountryId");
        query.setParameter("countryId", countryId);
        java.util.List<com.app.shared.organization.locationmanagement.City> listOfCity = query.getResultList();
        Log.out.println("ORGLM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CityRepositoryImpl", "findByCountryId", "Total Records Fetched = " + listOfCity.size());
        return listOfCity;
    }

    @Transactional
    public List<City> findByStateId(String stateId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("City.findByStateId");
        query.setParameter("stateId", stateId);
        java.util.List<com.app.shared.organization.locationmanagement.City> listOfCity = query.getResultList();
        Log.out.println("ORGLM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CityRepositoryImpl", "findByStateId", "Total Records Fetched = " + listOfCity.size());
        return listOfCity;
    }

    @Transactional
    public City findById(String cityId) throws Exception {
        javax.persistence.EntityManager emanager = emfResource.getResource();
        javax.persistence.Query query = emanager.createNamedQuery("City.findById");
        query.setParameter("cityId", cityId);
        com.app.shared.organization.locationmanagement.City listOfCity = (com.app.shared.organization.locationmanagement.City) query.getSingleResult();
        Log.out.println("ORGLM324990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CityRepositoryImpl", "findById", "Total Records Fetched = " + listOfCity);
        return listOfCity;
    }
}

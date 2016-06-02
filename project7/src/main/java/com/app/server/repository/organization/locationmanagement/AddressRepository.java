package com.app.server.repository.organization.locationmanagement;
import com.app.server.repository.core.SearchInterface;
import com.app.config.annotation.Complexity;
import com.app.config.annotation.SourceCodeAuthorClass;
import java.util.List;

@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Repository for Address Transaction table", complexity = Complexity.MEDIUM)
public interface AddressRepository<T> extends SearchInterface {

    public List<T> findAll() throws Exception;

    public T save(T entity) throws Exception;

    public List<T> save(List<T> entity) throws Exception;

    public void delete(String id) throws Exception;

    public void update(T entity) throws Exception;

    public void update(List<T> entity) throws Exception;

    public List<T> findByAddressTypeId(String addressTypeId) throws Exception;

    public List<T> findByCountryId(String countryId) throws Exception;

    public List<T> findByStateId(String stateId) throws Exception;

    public List<T> findByCityId(String cityId) throws Exception;

    public T findById(String addressId) throws Exception;
}

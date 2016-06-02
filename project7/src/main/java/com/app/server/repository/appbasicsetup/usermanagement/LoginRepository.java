package com.app.server.repository.appbasicsetup.usermanagement;
import com.app.server.repository.core.SearchInterface;
import com.app.config.annotation.Complexity;
import com.app.config.annotation.SourceCodeAuthorClass;
import java.util.List;

@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Repository for Login Transaction table", complexity = Complexity.MEDIUM)
public interface LoginRepository<T> extends SearchInterface {

    public List<T> findAll() throws Exception;

    public T save(T entity) throws Exception;

    public List<T> save(List<T> entity) throws Exception;

    public void delete(String id) throws Exception;

    public void update(T entity) throws Exception;

    public void update(List<T> entity) throws Exception;

    public List<T> findByUserId(String userId) throws Exception;

    public List<T> findByContactId(String contactId) throws Exception;

    public T findById(String loginPk) throws Exception;

    public List<T> FindMappedUser() throws Exception;

    public List<T> FindUnMappedUser() throws Exception;
}

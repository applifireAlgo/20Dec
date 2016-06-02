package com.app.server.repository.appbasicsetup.usermanagement;
import com.app.server.repository.core.SearchInterface;
import com.spartan.server.interfaces.UserRepositoryInterface;
import com.app.config.annotation.Complexity;
import com.app.config.annotation.SourceCodeAuthorClass;
import java.util.List;
import com.app.shared.appbasicsetup.usermanagement.PassRecovery;

@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Repository for User Transaction table", complexity = Complexity.MEDIUM)
public interface UserRepository<T> extends SearchInterface, UserRepositoryInterface {

    public List<T> findAll() throws Exception;

    public T save(T entity) throws Exception;

    public List<T> save(List<T> entity) throws Exception;

    public void delete(String id) throws Exception;

    public void deletePassRecovery(List<PassRecovery> passrecovery);

    public void update(T entity) throws Exception;

    public List<T> findByUserAccessLevelId(String userAccessLevelId) throws Exception;

    public List<T> findByUserAccessDomainId(String userAccessDomainId) throws Exception;

    public T findById(String userId) throws Exception;
}

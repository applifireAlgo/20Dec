package com.app.server.repository.appbasicsetup.usermanagement;
import com.app.server.repository.core.SearchInterface;
import com.spartan.server.password.interfaces.PasswordPolicyRepositoryInterface;
import com.app.config.annotation.Complexity;
import com.app.config.annotation.SourceCodeAuthorClass;
import com.spartan.server.password.interfaces.PasswordPolicyInterface;
import java.util.List;

@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Repository for PasswordPolicy Master table Entity", complexity = Complexity.LOW)
public interface PasswordPolicyRepository<T> extends SearchInterface, PasswordPolicyRepositoryInterface {

    public List<PasswordPolicyInterface> findAll() throws Exception;

    public T save(T entity) throws Exception;

    public List<T> save(List<T> entity) throws Exception;

    public void delete(String id) throws Exception;

    public void update(T entity) throws Exception;

    public void update(List<T> entity) throws Exception;

    public T findById(String policyId) throws Exception;
}

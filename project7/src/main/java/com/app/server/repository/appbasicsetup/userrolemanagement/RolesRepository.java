package com.app.server.repository.appbasicsetup.userrolemanagement;
import com.app.server.repository.core.SearchInterface;
import com.app.config.annotation.Complexity;
import com.app.config.annotation.SourceCodeAuthorClass;
import java.util.List;
import com.app.shared.appbasicsetup.userrolemanagement.RoleMenuBridge;

@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Repository for Roles Transaction table", complexity = Complexity.MEDIUM)
public interface RolesRepository<T> extends SearchInterface {

    public List<T> findAll() throws Exception;

    public T save(T entity) throws Exception;

    public List<T> save(List<T> entity) throws Exception;

    public void delete(String id) throws Exception;

    public void deleteRoleMenuBridge(List<RoleMenuBridge> rolemenubridge);

    public void update(T entity) throws Exception;

    public void update(List<T> entity) throws Exception;

    public T findById(String roleId) throws Exception;
}

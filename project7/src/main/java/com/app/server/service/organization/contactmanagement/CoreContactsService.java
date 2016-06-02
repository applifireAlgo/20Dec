package com.app.server.service.organization.contactmanagement;
import com.app.config.annotation.Complexity;
import com.app.config.annotation.SourceCodeAuthorClass;
import com.athena.server.pluggable.utils.bean.ResponseBean;
import org.springframework.http.HttpEntity;
import com.app.shared.organization.contactmanagement.CoreContacts;
import java.util.List;
import com.athena.server.pluggable.utils.bean.FindByBean;

@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Service for CoreContacts Transaction table", complexity = Complexity.MEDIUM)
public abstract class CoreContactsService {

    public HttpEntity<ResponseBean> findAll() throws Exception {
        return null;
    }

    public HttpEntity<ResponseBean> save(CoreContacts entity) throws Exception {
        return null;
    }

    public HttpEntity<ResponseBean> save(List<CoreContacts> entity, boolean isArray) throws Exception {
        return null;
    }

    public HttpEntity<ResponseBean> delete(String id) throws Exception {
        return null;
    }

    public HttpEntity<ResponseBean> update(CoreContacts entity) throws Exception {
        return null;
    }

    public HttpEntity<ResponseBean> update(List<CoreContacts> entity, boolean isArray) throws Exception {
        return null;
    }

    public HttpEntity<ResponseBean> findByTitleId(FindByBean findByBean) throws Exception {
        return null;
    }

    public HttpEntity<ResponseBean> findByNativeLanguageCode(FindByBean findByBean) throws Exception {
        return null;
    }

    public HttpEntity<ResponseBean> findByGenderId(FindByBean findByBean) throws Exception {
        return null;
    }

    public HttpEntity<ResponseBean> findByTimeZoneId(FindByBean findByBean) throws Exception {
        return null;
    }

    public HttpEntity<ResponseBean> findById(FindByBean findByBean) throws Exception {
        return null;
    }
}

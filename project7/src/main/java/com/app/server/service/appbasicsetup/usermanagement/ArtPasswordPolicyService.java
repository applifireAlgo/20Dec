package com.app.server.service.appbasicsetup.usermanagement;
import com.app.shared.appbasicsetup.usermanagement.ArtPasswordPolicy;
import java.util.List;

import org.springframework.http.HttpEntity;
import com.athena.server.pluggable.utils.bean.FindByBean;
import com.athena.server.pluggable.utils.bean.ResponseBean;

public abstract class ArtPasswordPolicyService {
	public HttpEntity<ResponseBean> findAll() throws Exception {
		return null;
	}

	public HttpEntity<ResponseBean> save(ArtPasswordPolicy entity) throws Exception {
		return null;
	}

	public HttpEntity<ResponseBean> save(List<ArtPasswordPolicy> entity, boolean isArray) throws Exception{
		return null;
	}

	public HttpEntity<ResponseBean> delete(String id) throws  Exception {
		return null;
	}

	public HttpEntity<ResponseBean> update(ArtPasswordPolicy entity) throws Exception {
		return null;
	}

	public HttpEntity<ResponseBean> update(List<ArtPasswordPolicy> entity, boolean isArray) throws  Exception {
		return null;
	}

	public HttpEntity<ResponseBean> findById(FindByBean findByBean) throws  Exception {
		return null;
	}

}

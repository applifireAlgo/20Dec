package com.app.server.repository.appbasicsetup.usermanagement;
import com.app.shared.appbasicsetup.usermanagement.ArtPasswordPolicy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.athena.server.pluggable.utils.helper.ResourceFactoryManagerHelper;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;

@Repository
public class ArtPasswordPolicyRepositoryImpl implements
		ArtPasswordPolicyRepository<ArtPasswordPolicy> {

	@Autowired
	private ResourceFactoryManagerHelper emfResource;

	@Autowired
	private RuntimeLogInfoHelper runtimeLogInfoHelper;

	@Override
	public List<ArtPasswordPolicy> findAll() throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		java.util.List<ArtPasswordPolicy> query = emanager.createQuery(
				"select u from ArtPasswordPolicy u").getResultList();
		return query;
	}

	@Override
	public ArtPasswordPolicy save(ArtPasswordPolicy entity) throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		emanager.persist(entity);
		return entity;
	}

	@Override
	public List<ArtPasswordPolicy> save(List<ArtPasswordPolicy> entity)
			throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		for (int i = 0; i < entity.size(); i++) {
			ArtPasswordPolicy obj = entity.get(i);
			emanager.persist(obj);
		}
		return entity;
	}

	@Override
	public void delete(String id) throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		ArtPasswordPolicy s = emanager.find(ArtPasswordPolicy.class, id);
		emanager.remove(s);
	}

	@Override
	public void update(ArtPasswordPolicy entity) throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		emanager.merge(entity);
	}

	@Override
	public void update(List<ArtPasswordPolicy> entity) throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		for (int i = 0; i < entity.size(); i++) {
			ArtPasswordPolicy obj = entity.get(i);
			emanager.merge(obj);
		}
	}

	@Override
	public ArtPasswordPolicy findById(String id) throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		ArtPasswordPolicy s = emanager.find(ArtPasswordPolicy.class, id);
		return s;

	}

}

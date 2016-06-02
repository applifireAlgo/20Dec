package com.app.server.repository.appbasicsetup.usermanagement;
import com.app.shared.appbasicsetup.usermanagement.ArtPasswordAlgorithm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.athena.server.pluggable.utils.helper.ResourceFactoryManagerHelper;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;

@Repository
public class ArtPasswordAlgorithmRepositoryImpl implements
		ArtPasswordAlgorithmRepository<ArtPasswordAlgorithm> {

	@Autowired
	private ResourceFactoryManagerHelper emfResource;

	@Autowired
	private RuntimeLogInfoHelper runtimeLogInfoHelper;

	@Override
	@Transactional
	public List<ArtPasswordAlgorithm> findAll() throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		java.util.List<ArtPasswordAlgorithm> query = emanager.createQuery(
				"select u from ArtPasswordAlgorithm u").getResultList();
		return query;
	}

	@Override
	@Transactional
	public ArtPasswordAlgorithm save(ArtPasswordAlgorithm entity)
			throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		emanager.persist(entity);
		return entity;
	}

	@Override
	@Transactional
	public List<ArtPasswordAlgorithm> save(List<ArtPasswordAlgorithm> entity)
			throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		for (int i = 0; i < entity.size(); i++) {
			ArtPasswordAlgorithm obj = entity.get(i);
			emanager.persist(obj);
		}
		return entity;
	}

	@Transactional
	@Override
	public void delete(String id) throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		ArtPasswordAlgorithm s = emanager.find(ArtPasswordAlgorithm.class, id);
		emanager.remove(s);
	}

	@Override
	@Transactional
	public void update(ArtPasswordAlgorithm entity) throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		emanager.merge(entity);
	}

	@Override
	@Transactional
	public void update(List<ArtPasswordAlgorithm> entity) throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		for (int i = 0; i < entity.size(); i++) {
			ArtPasswordAlgorithm obj = entity.get(i);
			emanager.merge(obj);
		}
	}

	@Override
	public ArtPasswordAlgorithm findById(String id) throws Exception {
		javax.persistence.EntityManager emanager = emfResource.getResource();
		ArtPasswordAlgorithm s = emanager.find(ArtPasswordAlgorithm.class, id);
		return s;
	}

}

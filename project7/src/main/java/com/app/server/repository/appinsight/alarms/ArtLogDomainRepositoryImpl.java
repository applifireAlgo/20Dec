package com.app.server.repository.appinsight.alarms;
import com.app.shared.appinsight.alarms.ArtLogBoundedContext;

import com.app.shared.appinsight.alarms.ArtLogDomain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.athena.server.pluggable.utils.helper.ResourceFactoryManagerHelper;

@Repository
@Transactional
public class ArtLogDomainRepositoryImpl implements ArtLogDomainRepository {

	@Autowired
	ResourceFactoryManagerHelper entity;
	
	@Override
	public List<ArtLogDomain> findByArtLogBoundedContext(ArtLogBoundedContext artLogBoundedContext) {

		try {
			EntityManager entityManager = entity.getResource();
			String JPQL = "SELECT artlogdomain FROM ArtLogDomain artlogdomain WHERE artlogdomain.artLogBoundedContext =:artLogBoundedContext";
			Query query = entityManager.createQuery(JPQL);
			query.setParameter("artLogBoundedContext", artLogBoundedContext);
			@SuppressWarnings("unchecked")
			List<ArtLogDomain> artLogDomainList = query.getResultList();
			return artLogDomainList;
		} catch (NoResultException nr) {
			nr.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ArtLogDomain findById(String id) {

		try {
			EntityManager entityManager = entity.getResource();
			String JPQL = "SELECT awsLogDomain FROM ArtLogDomain awsLogDomain WHERE awsLogDomain.domainPkId =:domainPkId";
			Query query = entityManager.createQuery(JPQL);
			query.setParameter("domainPkId", id);
			ArtLogDomain awsLogDomain = (ArtLogDomain) query.getSingleResult();
			return awsLogDomain;
		} catch (NoResultException nr) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void update(ArtLogDomain awsLogDomain) {
		try {
			EntityManager entityManager = entity.getResource();
			entityManager.merge(awsLogDomain);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(ArtLogDomain awsLogDomain) {
		try {
			EntityManager entityManager = entity.getResource();
			entityManager.persist(entityManager.merge(awsLogDomain));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

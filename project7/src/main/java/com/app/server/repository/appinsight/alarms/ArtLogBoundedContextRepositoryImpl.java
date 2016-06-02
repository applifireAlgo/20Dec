package com.app.server.repository.appinsight.alarms;
import com.app.shared.appinsight.alarms.ArtLogBoundedContext;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.athena.server.pluggable.utils.helper.ResourceFactoryManagerHelper;

@Repository
@Transactional
@Scope(value = "prototype")
public class ArtLogBoundedContextRepositoryImpl implements ArtLogBoundedContextRepository {

	@Autowired
	ResourceFactoryManagerHelper entity;

	@Override
	@Transactional
	public void save(ArtLogBoundedContext artLogBoundedContext) {
		try {
			EntityManager entityManager = entity.getResource();
			entityManager.merge(artLogBoundedContext);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void update(ArtLogBoundedContext artLogBoundedContext) {
		try {
			EntityManager entityManager = entity.getResource();
			entityManager.merge(artLogBoundedContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public ArtLogBoundedContext findByBoundedContextPrefix(String boundedContextPrefix) {
		try {
			EntityManager entityManager = entity.getResource();
			String JPQL = "SELECT artLogBoundedContext FROM ArtLogBoundedContext artLogBoundedContext WHERE ArtLogBoundedContext.boundedContextPrefix =:boundedContextPrefix";
			Query query = entityManager.createQuery(JPQL);
			query.setParameter("boundedContextPrefix", boundedContextPrefix);
			ArtLogBoundedContext artLogBoundedContext = (ArtLogBoundedContext) query.getSingleResult();
			return artLogBoundedContext;
		} catch (NoResultException nr) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArtLogBoundedContext> findAll() {
		try {
			EntityManager entityManager = entity.getResource();
			return entityManager.createQuery("select u from ArtLogBoundedContext u").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

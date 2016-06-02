package com.app.server.repository.appbasicsetup.aaa;
import com.app.shared.appbasicsetup.aaa.ArtUserLastStatus;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.athena.server.pluggable.utils.helper.ResourceFactoryManagerHelper;




@Repository
@Transactional
public class ArtUserStatusRepositoryImpl implements ArtUserStatusRepository{

	
	@Autowired
	private ResourceFactoryManagerHelper resourceFactoryManager;
	
	@Override
	public void save(ArtUserLastStatus userLastStatus)throws Exception
	{
		if (userLastStatus == null) {
			throw new com.spartan.pluggable.exception.security.InvalidDataException();
		}

		try {
			EntityManager entityManager = resourceFactoryManager.getResource();
			entityManager.persist(userLastStatus);
		} catch (Exception e) {
			throw new Exception("Database Query Failed in " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}

	@Override
	public void update(ArtUserLastStatus userLastStatus)throws Exception {
		
		if (userLastStatus == null) {
			throw new com.spartan.pluggable.exception.security.InvalidDataException();
		}

		try {
			EntityManager entityManager = resourceFactoryManager.getResource();
			entityManager.merge(userLastStatus);
		} catch (Exception e) {
			throw new Exception("Database Query Failed in " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		
	}

	@Override
	public void delete(String id) throws Exception {
		if (id == null) {
			throw new com.spartan.pluggable.exception.security.InvalidDataException();
		}

		try {
			EntityManager entityManager = resourceFactoryManager.getResource();
			ArtUserLastStatus userLastStatus = entityManager.find(ArtUserLastStatus.class, id);
			if(userLastStatus!=null)
			{
				entityManager.remove(userLastStatus);
			}
		} catch (Exception e) {
			throw new Exception("Database Query Failed in " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}

	@Override
	public ArtUserLastStatus findById(String id)throws Exception {
		
		if (id == null) {
			throw new com.spartan.pluggable.exception.security.InvalidDataException();
		}

		try {
			EntityManager entityManager = resourceFactoryManager.getResource();
			ArtUserLastStatus userLastStatus = entityManager.find(ArtUserLastStatus.class, id);
			return userLastStatus;
		} catch (Exception e) {
			throw new Exception("Database Query Failed in " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}	
	
	@Override
	public List<ArtUserLastStatus> findByUserId(String userId)throws Exception {
		
		if (userId == null) {
			throw new com.spartan.pluggable.exception.security.InvalidDataException();
		}

		try {
			EntityManager entityManager = resourceFactoryManager.getResource();
			String JPQL = "SELECT userLastStatus FROM ArtUserLastStatus userLastStatus where userLastStatus.userId = :userId ORDER BY userLastStatus.createdDate";
			Query query = entityManager.createQuery(JPQL);
			query.setParameter("userId", userId);
			List<ArtUserLastStatus> result = query.getResultList();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Database Query Failed in " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}	
	
	@Override
	public List<ArtUserLastStatus> findByUserMenuId(String userId, String menuId)throws Exception {
		
		if (userId == null) {
			throw new com.spartan.pluggable.exception.security.InvalidDataException();
		}

		try {
			EntityManager entityManager = resourceFactoryManager.getResource();
			String JPQL = "SELECT userLastStatus FROM ArtUserLastStatus userLastStatus where userLastStatus.userId = :userId and userLastStatus.menuId = :menuId";
			Query query = entityManager.createQuery(JPQL);
			query.setParameter("userId", userId);
			query.setParameter("menuId", menuId);
			List<ArtUserLastStatus> result = query.getResultList();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Database Query Failed in " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}
	}		
	
	
	
}

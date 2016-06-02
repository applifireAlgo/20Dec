package com.app.server.repository.aspect;import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.athena.server.pluggable.utils.helper.ResourceFactoryManagerHelper;
import com.athena.server.pluggable.utils.helper.EntityValidatorHelper;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
import com.athena.server.pluggable.interfaces.CommonEntityInterface;
import com.spartan.pluggable.exception.security.InvalidDataException;
import com.spartan.pluggable.logger.api.RuntimeLogUserInfoBean;

@Aspect
@Component
public abstract class RepositoryAspect {

	@Autowired
	private RuntimeLogInfoHelper runtimeLogInfoHelper;

	@Autowired
	private EntityValidatorHelper<Object> entityValidator;

	@Autowired
	private ResourceFactoryManagerHelper emfResource;


	public Object aroundfindAll(ProceedingJoinPoint joinPoint) throws Throwable {
		return joinPoint;
	}

	protected void repositoryLogic(ProceedingJoinPoint joinPoint) throws Throwable {
		setCustomerIdInEntityManager();
		Object object = null;
		if (joinPoint.getArgs().length > 0) {
			Object methodInputParam = joinPoint.getArgs()[0];
			if (methodInputParam != null && methodInputParam instanceof CommonEntityInterface) {
				CommonEntityInterface entity = (CommonEntityInterface) methodInputParam;
				preSaveUpdateOperation(entity);
			} else if (methodInputParam != null && methodInputParam instanceof List) {
				List listOfEntities = (List) methodInputParam;
				if (listOfEntities.size() > 0) {
					/*
					 * Checking 0th element type. So no need to check type for
					 * each element in the loop.
					 */
					if (listOfEntities.get(0) instanceof CommonEntityInterface) {
						for (Object object1 : listOfEntities) {
							CommonEntityInterface entity = (CommonEntityInterface) object1;
							preSaveUpdateOperation(entity);
						}
					}
				}
			}
		}
	}

	protected Object handleRepositoryCall(ProceedingJoinPoint joinPoint) throws Throwable  {
		Object returnObject = null;
		try {

			returnObject = joinPoint.proceed();
			System.out.println("Repository handle");
		} catch (Exception e) {
		}
		return returnObject;
	}

	protected void preSaveUpdateOperation(CommonEntityInterface entity) throws InvalidDataException {
		entity.setEntityAudit(1, runtimeLogInfoHelper.getRuntimeLogInfo().getUserId());
		/* validates the entity */
		if (!entity.isEntityValidated()) {
			validateEntity(entity);
		}
	}

	private boolean validateEntity(CommonEntityInterface entity) throws InvalidDataException {
		boolean isValidEntity = true;
		/* set entity validator */
		entity.setEntityValidator(this.entityValidator);
		/* validates the entity */
		isValidEntity = entity.isValid();
		return isValidEntity;
	}

	protected RuntimeLogUserInfoBean createLocalRuntimeLogInfo() {
		String ipAddress = "localhost";
		try {
			InetAddress ip = InetAddress.getLocalHost();
			ipAddress = ip.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return new RuntimeLogUserInfoBean("customer", "DEFAULT", ipAddress,0,0,0);
	}

	public void setCustomerIdInEntityManager() {
		if (runtimeLogInfoHelper.getCustomerId() != "" && runtimeLogInfoHelper.getCustomerId() != null) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tenant.id", runtimeLogInfoHelper.getCustomerId());
			emfResource.setResourceAttributes(map);
			runtimeLogInfoHelper.setMultitenantFields(map);
		}
	}

	@Pointcut("execution(* com.athena..repository..*(..))")
	protected void athenaServiceOperation() {
	}

	@Pointcut("execution(* com.spartan..repository..*(..))")
	protected void spartanServiceOperation() {
	}

	@Pointcut("(execution(* com.athena..repository..*(..)) || execution(* com.spartan..repository..*(..))) && !within(com.athena.server.repository.ArtQueryRepository+)")
	protected void applifireRepositoryOperation() {
	}

	@Pointcut("execution(* com.athena.server.repository.ArtQueryRepository.findSqlFromJPQL())")
	protected void applifireJPQLToSQLConverter() {

	}

}

package com.app.server.repository.aspect;import java.util.HashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.athena.server.pluggable.utils.helper.ResourceFactoryManagerHelper;
import com.athena.server.pluggable.utils.helper.EntityValidatorHelper;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
import com.spartan.pluggable.logger.event.RuntimeLogUserInfo;

@Aspect
@Component
public class RepositoryBaseAspect extends RepositoryAspect{

	@Autowired
	private RuntimeLogInfoHelper runtimeLogInfoHelper;

	@Autowired
	private EntityValidatorHelper<Object> entityValidator;

	@Autowired
	private ResourceFactoryManagerHelper emfResource;



	public void setCustomerIdInEntityManager() {
		if (runtimeLogInfoHelper.getCustomerId() != "" && runtimeLogInfoHelper.getCustomerId() != null) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tenant.id", runtimeLogInfoHelper.getCustomerId());
			emfResource.setResourceAttributes(map);
			runtimeLogInfoHelper.setMultitenantFields(map);
		}
	}
	
	@Around(value = "applifireRepositoryOperation()")
    @Order(2)
    public Object aroundApplifire(ProceedingJoinPoint joinPoint) throws Throwable {
        setCustomerIdInEntityManager();
        return handleRepositoryCall(joinPoint);
    }

    @Around(value = "applifireJPQLToSQLConverter()")
    @Order(1)
    public Object aroundApplifireJPQLToSQLConverter(ProceedingJoinPoint joinPoint) throws Throwable {
        RuntimeLogUserInfo localRuntimeLogInfo = createLocalRuntimeLogInfo();
        return handleRepositoryCall(joinPoint);
    }

	@Pointcut("execution(* com.spartan..repository..*(..))) && !within(com.athena.server.dataengine.repository.ArtQueryRepository+) && ! cloudOperation() && !within(com.spartan..alarms..*)")
	protected void applifireRepositoryOperation() {
	}

	@Pointcut("execution(* com.athena.server.dataengine.repository.ArtQueryRepository.findSqlFromJPQL())")
	protected void applifireJPQLToSQLConverter() {

	}

    @Pointcut("execution(* com.athena.cloud..repository..*(..))")
  protected void cloudOperation() {
  }

}

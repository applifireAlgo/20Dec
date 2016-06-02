package com.app.server.service.aspect;
import com.app.server.businessservice.appbasicsetup.aaa.SessionValidation;

import com.app.server.businessservice.appbasicsetup.aaa.CookieValidation;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.repository.MetricRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.athena.server.pluggable.utils.helper.EntityValidatorHelper;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
import com.athena.server.pluggable.interfaces.CommonEntityInterface;
import com.spartan.healthmeter.entity.scheduler.ArtMethodCallStack;
import com.spartan.healthmeter.entity.scheduler.MethodCallDetails;
import com.spartan.healthmeter.msgWriter.config.ExecutionTimer;
import com.spartan.healthmeter.msgWriter.config.HealthConstants;
import com.spartan.healthmeter.msgWriter.core.Healthmeter;
import com.spartan.pluggable.exception.auth.AccessDeniedException;
import com.spartan.pluggable.exception.security.InvalidDataException;
import com.spartan.pluggable.exception.core.BaseSecurityException;
import com.spartan.pluggable.logger.api.RuntimeLogUserInfoBean;
import com.spartan.pluggable.logger.event.RequestHeaderBean;
import com.spartan.server.interfaces.LoginSessionRepositoryInterface;

@Aspect
@Component
public abstract class ServiceAspect {

	@Autowired
	private EntityValidatorHelper<Object> entityValidator;

	@Autowired
	private SessionValidation sessionValidation;

	@Autowired
	private CookieValidation cookieValidation;

	@Autowired
	private RuntimeLogInfoHelper runtimeLogInfoHelper;
	@Autowired
	private LoginSessionRepositoryInterface loginSessionRepo;

	@Autowired
	private CounterService counterService;

	@Autowired
	private GaugeService gaugeservice;
	
	@Autowired
    private MetricRepository repository;


	public Boolean aroundAdviceSaveAndUpdateLogin(ProceedingJoinPoint proceedingJoinPoint) throws SecurityException {
		boolean isValidEntity = true;
		if (proceedingJoinPoint.getArgs().length > 0) {
			Object methodInputParam = proceedingJoinPoint.getArgs()[0];
			if (methodInputParam != null && methodInputParam instanceof CommonEntityInterface) {
				CommonEntityInterface entity = (CommonEntityInterface) methodInputParam;
				try {
					preSaveUpdateOperation(entity);
					entity.setSystemTxnCode(51000);
				} catch (SecurityException e) {
					isValidEntity = false;
				}
			} else if (methodInputParam != null && methodInputParam instanceof List) {
				List listOfEntities = (List) methodInputParam;
				if (listOfEntities.size() > 0) {
					/*
					 * Checking 0th element type. So no need to check type for
					 * each element in the loop.
					 */
					if (listOfEntities.get(0) instanceof CommonEntityInterface) {
						for (Object object : listOfEntities) {
							CommonEntityInterface entity = (CommonEntityInterface) object;
							try {
								preSaveUpdateOperation(entity);
								entity.setSystemTxnCode(51000);
							} catch (SecurityException e) {
								isValidEntity = false;
							}
						}
					}
				}
			}
		}
		return isValidEntity;
	}

	public void serviceLogic(HttpSession session, HttpServletRequest request, HttpServletResponse response, String _requestId, String _sessionId) throws Throwable {
		/*
		 * Needs to get user id from request header and pass it to entityAudit
		 * and RuntimeLogInfo
		 */
		/* create logging info object (Needs to call from login service only */
		runtimeLogInfoHelper.setCustomerId(getCustomerId(request));
		runtimeLogInfoHelper.createRuntimeLogUserInfo(getCustomerId(request), loggedInUserId(request), request.getRemoteHost());
		runtimeLogInfoHelper.setRequestHeaderBean(new RequestHeaderBean(new RuntimeLogUserInfoBean(getCustomerId(request), loggedInUserId(request), request
				.getRemoteHost(), request.getRemotePort(), 0, 0), _sessionId, _requestId));
		/* validate request */
		if (request.getHeader("Job-Execution") == null) {
			if (request.getHeader("isBeforeSession") == null) {
				validateRequest(session, request, response);
				if (!sessionValidation.checkIgnoreURL(request)) {
					/** Sets user's last access time in table */
					java.sql.Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());
					loginSessionRepo.updateLastAccessTime(loggedInUserId(request), session.getAttribute("usidHash").toString(), currentTime);
					runtimeLogInfoHelper.setUserAccessCode(Integer.parseInt(session.getAttribute("userAccessCode").toString()));
				}
			}
		}
	}

	public boolean validateRequest(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws  BaseSecurityException {

		if (!sessionValidation.checkIgnoreURL(request)) {
			sessionValidation.validateSession(session, request, response);
			cookieValidation.validateSessionCookie(session, request);
		}
		return true;
	}

	// public Object aroundAdvice1(ProceedingJoinPoint proceedingJoinPoint)
	// throws Throwable {
	// return null;
	// }
	//
	// public Object aroundAdvice2Save(ProceedingJoinPoint proceedingJoinPoint)
	// throws Throwable {
	// return null;
	// }

	protected void preSaveUpdateOperation(CommonEntityInterface entity) throws SecurityException {
		prepareEntityAuditInfo(entity);
		/* validates the entity */
		validateEntity(entity);

	}

	public Object aroundAdvice2Update(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		return proceedingJoinPoint;
	}

	public Object aroundAdvice2Delete(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		return null;
	}

	private boolean validateEntity(CommonEntityInterface entity) throws SecurityException {
		boolean isValidEntity = true;
		/* set entity validator */
		entity.setEntityValidator(this.entityValidator);
		/* validates the entity */
		isValidEntity = entity.isValid();
		return isValidEntity;
	}

	protected Object aroundAdvicefindOperation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		return true;
	}

	public void afterReturning(JoinPoint join) throws IOException {
	}

	public String loggedInUserId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("usidHash") != null) {
			String loggedInObject = (String) session.getAttribute(session.getAttribute("usidHash").toString());
			try {
				JSONObject json = new JSONObject(loggedInObject);
				JSONObject userJSON = json.getJSONObject("user");
				return userJSON.getString("userId");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return "";
		}
		return null;

	}

	public String getSessionId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("usidHash") != null) {
			return (String) session.getAttribute("usidHash").toString();
		} else {
			return "";
		}

	}

	protected void prepareEntityAuditInfo(CommonEntityInterface entity) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String userId = loggedInUserId(request);
		entity.setEntityAudit(1, userId);
	}

	public String getCustomerId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("customerId") != null) {
			return (String) session.getAttribute("customerId").toString();
		} else {
			return "";
		}
	}

	public String incrementUricounter(String className, String methodName) {
		counterService.increment(className + "." + methodName);
		Metric metric = repository.findOne("gauge." + className + "." + methodName + "");
		if (metric != null) {
			gaugeservice.submit(className + "." + methodName, (Double) metric.getValue() + 1);
		} else {
			gaugeservice.submit(className + "." + methodName, 1);
		}
		return className + "." + methodName;
	}

	protected MethodCallDetails setMedhodCallDetails(ArtMethodCallStack methodCallStack, HttpServletRequest request, ProceedingJoinPoint proceedingJoinPoint) {
		MethodCallDetails methodCallDetails = new MethodCallDetails(methodCallStack.getRequestId(), methodCallStack.getCallSequence(),
				HealthConstants.CLASS_TYPE.SERVICE, request.getRemoteHost(), request.getMethod(), proceedingJoinPoint.getTarget().getClass().toString(),
				proceedingJoinPoint.getSignature().getName(), loggedInUserId(request), getSessionId(request));
		return methodCallDetails;
	}

	protected  MethodCallDetails setMethodPostCallDetails(MethodCallDetails methodCallDetails, String healthConstant, String statusCodeName) {
		methodCallDetails.setPostCallDetails(healthConstant, statusCodeName);
		return methodCallDetails;
	}

	protected void setFinallyMethodCallDetails(ArtMethodCallStack methodCallStack,Healthmeter healthmeter,ExecutionTimer executionTimer, MethodCallDetails methodCallDetails, ProceedingJoinPoint proceedingJoinPoint)
			throws CloneNotSupportedException {
		String entityName = incrementUricounter(proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName(), proceedingJoinPoint.getSignature().getName());
		methodCallStack.addMethodCallDetails(methodCallDetails);
		healthmeter.apphealth.writeHealthLog((ArtMethodCallStack) methodCallStack.clone());
		Integer existingValue = 0;
		Metric metric = repository.findOne("gauge." + "total.Time" + entityName + "");
		if (metric != null) {
			existingValue = metric.getValue().intValue();
		}
		gaugeservice.submit("total.Time" + entityName + "", executionTimer.getSystemTime + existingValue);
	}

	@Pointcut("execution(* com.spartan..service..*(..))")
	protected void spartanServiceOperation() {
	}

	@Pointcut("execution(* com.athena..service..update*(..)) || execution(* com.spartan..service..update*(..))")
	protected void athenaAndSpartanUpdateServiceOperation() {
	}

	@Pointcut("execution(* com.athena..service..*(..))")
	protected void athenaServiceOperation() {
	}

}

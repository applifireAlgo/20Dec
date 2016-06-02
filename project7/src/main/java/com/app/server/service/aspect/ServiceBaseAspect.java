package com.app.server.service.aspect;
import com.app.server.businessservice.appbasicsetup.aaa.SessionValidation;

import com.app.server.businessservice.appbasicsetup.aaa.CookieValidation;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.repository.MetricRepository;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.athena.server.pluggable.utils.bean.ResponseBean;
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
public class ServiceBaseAspect {
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
	private ArtMethodCallStack methodCallStack;

	@Autowired
	private Healthmeter healthmeter;

	public HttpStatus httpStatusCode;

	@Autowired
	private CounterService counterService;

	@Autowired
	private GaugeService gaugeservice;

	@Autowired
	private ExecutionTimer executionTimer;

	@Autowired
	private MetricRepository repository;

	public AtomicLong autoRequestId = new AtomicLong(1);

	@Around("athenaServiceOperation()||spartanServiceOperation()")
	@Order(1)
	public Object aroundAdvice1Default(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		ServletWebRequest servletWebRequest = new ServletWebRequest(request);
		HttpServletResponse response = servletWebRequest.getResponse();
		HttpSession session = request.getSession();
		long nextAutoNum = autoRequestId.getAndIncrement();
		methodCallStack.setRequestId(UUID.randomUUID().toString().toUpperCase());
        methodCallStack.setAppSessionId(getSessionId(request));
		MethodCallDetails methodCallDetails = new MethodCallDetails(methodCallStack.getRequestId(), methodCallStack.getCallSequence(),
				HealthConstants.CLASS_TYPE.SERVICE, request.getRemoteHost(), request.getMethod(), proceedingJoinPoint.getTarget().getClass().toString(),
				proceedingJoinPoint.getSignature().getName(), loggedInUserId(request), getSessionId(request));
		String entityName = incrementUricounter(proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName(), proceedingJoinPoint.getSignature().getName());
		ResponseEntity<ResponseBean> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		try {
			serviceLogic(session, request, response,methodCallStack.getRequestId(),methodCallStack.getAppSessionId());
			Object obj = proceedingJoinPoint.proceed();
			responseEntity = (ResponseEntity<ResponseBean>) obj;
			httpStatusCode = responseEntity.getStatusCode();
			methodCallDetails.setPostCallDetails(HealthConstants.METHOD_NORMAL_EXECUTION, responseEntity.getStatusCode().name());
		}  catch (Exception e) {
	} finally {
			methodCallStack.addMethodCallDetails(methodCallDetails);
			healthmeter.apphealth.writeHealthLog((ArtMethodCallStack) methodCallStack.clone());
			Integer existingValue = 0;
			Metric metric = repository.findOne("gauge." + "total.Time" + entityName + "");
			if (metric != null) {
				existingValue = metric.getValue().intValue();
			}
			gaugeservice.submit("total.Time" + entityName + "", executionTimer.getSystemTime + existingValue);
		}
		return responseEntity;
	}

	public Object aroundAdviceSaveAndUpdateLogin(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		boolean isValidEntity = true;
		if (proceedingJoinPoint.getArgs().length > 0) {
			Object methodInputParam = proceedingJoinPoint.getArgs()[0];
			if (methodInputParam != null && methodInputParam instanceof CommonEntityInterface) {
				CommonEntityInterface entity = (CommonEntityInterface) methodInputParam;
				try {
					preSaveUpdateOperation(entity);
					entity.setSystemTxnCode(51000);
				} catch (InvalidDataException e) {
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
							} catch (InvalidDataException e) {
								isValidEntity = false;
							}
						}
					}
				}
			}
		}
		return isValidEntity;
	}

	public void serviceLogic(HttpSession session, HttpServletRequest request, HttpServletResponse response,String _requestId,String _sessionId) throws Throwable{

	       /*
   * Needs to get user id from request header and pass it to entityAudit
   * and RuntimeLogInfo
   */
  /* create logging info object (Needs to call from login service only */
  runtimeLogInfoHelper.setCustomerId(getCustomerId(request));
  runtimeLogInfoHelper.createRuntimeLogUserInfo("customer", loggedInUserId(request), request.getRemoteHost());
  runtimeLogInfoHelper.setRequestHeaderBean(new RequestHeaderBean(new RuntimeLogUserInfoBean(getCustomerId(request), loggedInUserId(request), request.getRemoteHost(), request.getRemotePort(), 100, 0), _sessionId, _requestId));

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

	public boolean validateRequest(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws BaseSecurityException {

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

	@Around("athenaServiceOperation()||spartanServiceOperation()")
	@Order(2)
	public Object aroundAdvice2SaveDefault(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		boolean isValidEntity = true;
		Object returnObject = new Object();
		aroundAdviceSaveAndUpdateLogin(proceedingJoinPoint);
		if (isValidEntity) {
			returnObject = proceedingJoinPoint.proceed();
			return returnObject;
		}
		return returnObject;
	}

	protected void preSaveUpdateOperation(CommonEntityInterface entity) throws InvalidDataException {
		prepareEntityAuditInfo(entity);
		/* validates the entity */
		validateEntity(entity);

	}

	@Around("athenaAndSpartanUpdateServiceOperation()")
	@Order(2)
	public Object aroundAdvice2Updatedefault(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		boolean isValidEntity = true;
		Object returnObject = new Object();
		aroundAdviceSaveAndUpdateLogin(proceedingJoinPoint);
		if (isValidEntity) {
			returnObject = proceedingJoinPoint.proceed();
			return returnObject;
		}
		return returnObject;
	}

	@Around("athenaServiceOperation()||spartanServiceOperation()")
	@Order(2)
	public Object aroundAdvice2DeleteDefault(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object returnObject = new Object();
		if (proceedingJoinPoint.getArgs().length > 0) {
			returnObject = proceedingJoinPoint.proceed();
			return returnObject;
		}
		return returnObject;
	}

	private boolean validateEntity(CommonEntityInterface entity) throws InvalidDataException {
		boolean isValidEntity = true;
		/* set entity validator */
		entity.setEntityValidator(this.entityValidator);
		/* validates the entity */
		isValidEntity = entity.isValid();
		return isValidEntity;
	}

	@Around("athenaServiceOperation()||spartanServiceOperation()")
	@Order(2)
	public Object aroundAdvicefindOperationDefault(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object returnObject = new Object();
		if (proceedingJoinPoint.getArgs().length > 0) {
			Object methodInputParam = proceedingJoinPoint.getArgs()[0];
			if (methodInputParam != null) {
				returnObject = proceedingJoinPoint.proceed();
			}
		} else {
			returnObject = proceedingJoinPoint.proceed();
		}
		return returnObject;
	}

	@AfterReturning("athenaServiceOperation()||spartanServiceOperation()")
	public void afterReturningDefault(JoinPoint join) throws IOException {
		counterService.increment("counter.HttpStatus." + httpStatusCode.name() + "." + join.getSignature().getDeclaringType().getSimpleName() + "."
				+ join.getSignature().getName() + ".calls");
		counterService.increment("counter.numberof.calls");
	}

	public String incrementUricounter(String className, String methodName) {
		return null;
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

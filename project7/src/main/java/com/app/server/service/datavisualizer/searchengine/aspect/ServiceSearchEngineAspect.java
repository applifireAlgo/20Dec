package com.app.server.service.datavisualizer.searchengine.aspect;
import com.app.server.service.aspect.ServiceAspect;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import com.spartan.healthmeter.entity.scheduler.ArtMethodCallStack;
import com.spartan.healthmeter.msgWriter.core.Healthmeter;
import org.springframework.http.HttpStatus;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import com.spartan.healthmeter.msgWriter.config.ExecutionTimer;
import org.springframework.boot.actuate.metrics.repository.MetricRepository;
import java.util.concurrent.atomic.AtomicLong;
import com.spartan.pluggable.logger.api.LogManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import com.athena.server.pluggable.utils.bean.ResponseBean;
import com.spartan.healthmeter.entity.scheduler.MethodCallDetails;
import com.spartan.healthmeter.msgWriter.config.HealthConstants;
import java.util.UUID;
import com.spartan.pluggable.logger.api.LogManagerFactory;
import com.spartan.pluggable.logger.alarms.AppAlarm;
import org.eclipse.persistence.exceptions.TransactionException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.core.annotation.Order;
import java.io.IOException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import com.athena.server.pluggable.utils.AppLoggerConstant;

@Aspect
@Component
public class ServiceSearchEngineAspect extends ServiceAspect {

    @Autowired
    private RuntimeLogInfoHelper runtimeLogInfoHelper;

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

    private LogManager Log = LogManagerFactory.getInstance(AppLoggerConstant.LOGGER_ID);

    @Around("allOperation()")
    @Order(1)
    public Object aroundAdvice1(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        HttpServletResponse response = servletWebRequest.getResponse();
        HttpSession session = request.getSession();
        long nextAutoNum = autoRequestId.getAndIncrement();
        methodCallStack.setRequestId(UUID.randomUUID().toString().toUpperCase());
        methodCallStack.setAppSessionId(getSessionId(request));
        MethodCallDetails methodCallDetails = setMedhodCallDetails(methodCallStack, request, proceedingJoinPoint);
        ResponseEntity<ResponseBean> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        try {
            serviceLogic(session, request, response, methodCallStack.getRequestId(), methodCallStack.getAppSessionId());
            Object obj = proceedingJoinPoint.proceed();
            responseEntity = (ResponseEntity<ResponseBean>) obj;
            httpStatusCode = responseEntity.getStatusCode();
            methodCallDetails = setMethodPostCallDetails(methodCallDetails, HealthConstants.METHOD_NORMAL_EXECUTION, responseEntity.getStatusCode().name());
        } catch (Exception e) {
            AppAlarm appAlarm = Log.getAlarm("DVISE254900500");
            ResponseBean exceptionbean = new ResponseBean(appAlarm);
            exceptionbean.add("message", appAlarm.getMessage());
            httpStatusCode = HttpStatus.valueOf(appAlarm.getAlarmStatus());
            Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName(), String.format(appAlarm.getMessage(), e.getMessage()));
            methodCallDetails = setMethodPostCallDetails(methodCallDetails, HealthConstants.METHOD_EXCEPTION, httpStatusCode.name());
            return new ResponseEntity<ResponseBean>(exceptionbean, responseEntity.getStatusCode());
        } finally {
            setFinallyMethodCallDetails(methodCallStack, healthmeter, executionTimer, methodCallDetails, proceedingJoinPoint);
        }
        return responseEntity;
    }

    @AfterReturning("allOperation()")
    public void afterReturning(JoinPoint join) throws IOException {
        counterService.increment("counter.HttpStatus." + httpStatusCode.name() + "." + join.getSignature().getDeclaringType().getSimpleName() + "." + join.getSignature().getName() + ".calls");
        counterService.increment("counter.numberof.calls");
    }

    @Pointcut("execution(* com.app.server.service.datavisualizer.searchengine..*(..))")
    protected void allOperation() {
    }
}

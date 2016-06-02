package com.app.shared.appinsight.alarms;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "art_log_events_t")
@Entity
public class ArtLogEvents implements Serializable {
	@Id
	@Column(name = "id")
	@JsonProperty("id")
	@GeneratedValue(generator = "UUIDGenerator")
	private String id;
	
	@Column(name = "eventDate")
	private Timestamp eventDate;
	
	@Column(name = "alarmId")
	private String alarmId;

	@Column(name = "customerId")
	private String customerId;
	
	@Column(name = "userId")
	private String userId;
	
	@Column(name = "userIpAddress")
	private String userIpAddress;
	
	@Column(name = "userPort")
	private int userPort;

	@Column(name = "geoLatitude")
	private double geoLatitude;
	
	@Column(name = "geoLongitude")
	private double geoLongitude;
	  
	@Column(name = "sessionId")
	private String sessionId;
	
	@Column(name = "requestId")
	private String requestId;
	
	@Column(name = "boundedContextId")
	private String boundedContextId;
	
	@Column(name = "domainId")
	private String domainId;
	
	@Column(name = "className")
	private String className;
	
	@Column(name = "methodName")
	private String methodName;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "exception")
	private String exception;
	
	@Column(name = "context")
	private int context;
	
	@Column(name = "severity")
	private int severity;
	
	@Column(name = "layer")
	private int layer;
	
	@Column(name = "eventAction")
	private int eventAction;
	
	@Column(name = "alarmStatus")
	private int alarmStatus;
	

	public ArtLogEvents() {
		super();
	}

	public ArtLogEvents(String id, Timestamp eventDate, String alarmId, String customerId, String userId, String userIpAddress, int userPort, double geoLatitude,
			double geoLongitude, String sessionId, String requestId, String boundedContextId, String domainId, String className, String methodName, String message,
			String exception, int context, int severity, int layer, int eventAction, int alarmStatus) {
		super();
		this.id = id;
		this.eventDate = eventDate;
		this.alarmId = alarmId;
		this.customerId = customerId;
		this.userId = userId;
		this.userIpAddress = userIpAddress;
		this.userPort = userPort;
		this.geoLatitude = geoLatitude;
		this.geoLongitude = geoLongitude;
		this.sessionId = sessionId;
		this.requestId = requestId;
		this.boundedContextId = boundedContextId;
		this.domainId = domainId;
		this.className = className;
		this.methodName = methodName;
		this.message = message;
		this.exception = exception;
		this.context = context;
		this.severity = severity;
		this.layer = layer;
		this.eventAction = eventAction;
		this.alarmStatus = alarmStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getEventDate() {
		return eventDate;
	}

	public void setEventDate(Timestamp eventDate) {
		this.eventDate = eventDate;
	}

	public String getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserIpAddress() {
		return userIpAddress;
	}

	public void setUserIpAddress(String userIpAddress) {
		this.userIpAddress = userIpAddress;
	}

	public int getUserPort() {
		return userPort;
	}

	public void setUserPort(int userPort) {
		this.userPort = userPort;
	}

	public double getGeoLatitude() {
		return geoLatitude;
	}

	public void setGeoLatitude(double geoLatitude) {
		this.geoLatitude = geoLatitude;
	}

	public double getGeoLongitude() {
		return geoLongitude;
	}

	public void setGeoLongitude(double geoLongitude) {
		this.geoLongitude = geoLongitude;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getBoundedContextId() {
		return boundedContextId;
	}

	public void setBoundedContextId(String boundedContextId) {
		this.boundedContextId = boundedContextId;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public int getContext() {
		return context;
	}

	public void setContext(int context) {
		this.context = context;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getEventAction() {
		return eventAction;
	}

	public void setEventAction(int eventAction) {
		this.eventAction = eventAction;
	}

	public int getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(int alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	@Override
	public String toString() {
		return "ArtLogAlarmEvents [id=" + id + ", eventDate=" + eventDate + ", alarmId=" + alarmId + ", customerId=" + customerId + ", userId=" + userId + ", userIpAddress=" + userIpAddress
				+ ", userPort=" + userPort + ", geoLatitude=" + geoLatitude + ", geoLongitude=" + geoLongitude + ", sessionId=" + sessionId + ", requestId=" + requestId + ", boundedContextId="
				+ boundedContextId + ", domainId=" + domainId + ", className=" + className + ", methodName=" + methodName + ", message=" + message + ", exception=" + exception + ", context="
				+ context + ", severity=" + severity + ", layer=" + layer + ", eventAction=" + eventAction + ", alarmStatus=" + alarmStatus + "]";
	}

	public String toJSON() {
		JSONObject jsonObj=new JSONObject();
		try {
			jsonObj.put("id", "id");
			jsonObj.put("eventDate", "eventDate");
			jsonObj.put("alarmId",alarmId);
			jsonObj.put("customerId",customerId);
			jsonObj.put("severity",severity);
			jsonObj.put("userId", "userId");
			jsonObj.put("userIpAddress", "userIpAddress");
			jsonObj.put("userPort", "userPort");
			jsonObj.put("geoLatitude",geoLatitude);
			jsonObj.put("geoLongitude",geoLongitude);
			jsonObj.put("sessionId", "sessionId");
			jsonObj.put("requestId", "requestId");
			jsonObj.put("boundedContextId", "boundedContextId");
			jsonObj.put("domainId", "domainId");
			jsonObj.put("className", "className");
			jsonObj.put("methodName", "methodName");
			jsonObj.put("message",message);
			jsonObj.put("exception",exception);
			jsonObj.put("context",context);
			jsonObj.put("layer",layer);
			jsonObj.put("eventActions",eventAction);
			jsonObj.put("alarmStatus",alarmStatus);
		}catch(JSONException e) {
			e.printStackTrace();
		}
		return jsonObj.toString();
	}
}

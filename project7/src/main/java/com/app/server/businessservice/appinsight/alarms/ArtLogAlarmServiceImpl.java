package com.app.server.businessservice.appinsight.alarms;
import com.app.shared.appinsight.alarms.ArtLogAlarm;

import com.app.shared.appinsight.alarms.ArtLogArchitectureLayer;

import com.app.shared.appinsight.alarms.ArtLogBoundedContext;

import com.app.shared.appinsight.alarms.ArtLogConfig;

import com.app.shared.appinsight.alarms.ArtLogConnector;

import com.app.shared.appinsight.alarms.ArtLogDomain;

import com.app.shared.appinsight.alarms.ArtLogEventAction;

import com.app.shared.appinsight.alarms.ArtLogException;

import com.app.shared.appinsight.alarms.ArtLogSeverity;

import com.app.shared.appinsight.alarms.ArtLogStatus;

import com.app.server.repository.appinsight.alarms.ArtAlarmLoggerRepository;

import com.app.server.repository.appinsight.alarms.ArtLogBoundedContextRepository;

import com.app.server.repository.appinsight.alarms.ArtLogDomainRepository;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;
import com.athena.server.pluggable.utils.bean.ResponseBean;

@RestController
//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestMapping("/logAlarmService")
//@Service
public class ArtLogAlarmServiceImpl extends ArtLogAlarmService {

	@Autowired
	ArtAlarmLoggerRepository artLoggerRepository;

	/*@Autowired
	AwsLogModuleRepository awsLogModuleRepository;*/
	
	@Autowired
	private ArtLogBoundedContextRepository artLogBoundedContextRepository;
	
	@Autowired
	private ArtLogDomainRepository artLogDomainRepository;
	
	@Override
	@RequestMapping(value = "/getListOfAlarms", consumes = "application/json", method = RequestMethod.GET)
	public HttpEntity<ResponseBean> getListOfAlarms() {
		ResponseBean responseBean = new ResponseBean();
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
		List<ArtLogBoundedContext> boundedContexts= artLogBoundedContextRepository.findAll();
		JSONArray contextArray = new JSONArray();
		try {
			for(int i =0; i<boundedContexts.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				String bId = boundedContexts.get(i).getBoundedContextId();
				jsonObject.put("bId",bId);
				jsonObject.put("contextName",new JSONObject(boundedContexts.get(i).getBoundedContextJson()).getString("contextName"));
				jsonObject.put("contextPrefix",boundedContexts.get(i).getBoundedContextPrefix());
				List<ArtLogDomain> domainData = boundedContexts.get(i).getArtLogDomain();
				if(domainData != null && !domainData.equals("")) {
					JSONArray domainArray = new JSONArray();
					for(int j = 0; j < domainData.size(); j++) {
						JSONObject domainObject = new JSONObject();
						domainObject.put("mappingId",domainData.get(j).getdomainPkId());
						domainObject.put("domainPrefix",domainData.get(j).getDomainId());
						domainObject.put("domain",domainData.get(j).getDomainName());
						
						/*List<AwsLogArchitectureLayer> logArchitectureLayers = awsLoggerRepository.findByProjectId(projectId).get(0).getAwsLogArchitectureLayers();
						JSONArray layerArray = new JSONArray();
						for(AwsLogArchitectureLayer appArchitectureLayer : logArchitectureLayers) {
							JSONObject object = new JSONObject();
							object.put("layerId", appArchitectureLayer.getLayerId());
							object.put("layerName", appArchitectureLayer.getLayer());
							layerArray.add(object);
						}
						domainObject.put("architectureLayers", layerArray);*/
						domainArray.add(domainObject);
					}
					jsonObject.put("domains", domainArray);
				}
				contextArray.add(jsonObject);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			responseBean.add("success", true);
			responseBean.add("message", "Error in alarms retrieval");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);
		}
		responseBean.add("success", true);
		responseBean.add("message", "Successfully retrived ");
		responseBean.add("data", contextArray.toString());
		return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);
//		return contextArray.toString();
//		return null;
	}

	@Override
	@RequestMapping(value = "/getLoggerDetails", consumes = "application/json", method = RequestMethod.GET)
	public HttpEntity<ResponseBean> getLogConfigDetails() {
		ResponseBean responseBean = new ResponseBean();
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
        try {
        	List<ArtLogConfig> artLogList = artLoggerRepository.findAll();
        	if (artLogList.size() > 0) {
        		StringBuilder artLoggerDetails = new StringBuilder();

        		/*ArtLogConfig artLogConfig = artLogList.get(0);
        		List<ArtLogConnector> artLogConnectors = artLogConfig.getArtLogConnectors();
        		List<ArtLogSeverity> artLogSeverity = artLogConfig.getArtLogSeverity();
        		List<ArtLogArchitectureLayer> artLogArchitectureLayers = artLogConfig.getArtLogArchitectureLayers();
        		List<ArtLogEventAction> artLogEventActions = artLogConfig.getArtLogEventActions();
        		List<ArtLogException> artLogExceptions = artLogConfig.getArtLogExceptions();
        		List<ArtLogStatus> artLogStatusList = artLogConfig.getArtLogStatus();
        		artLoggerDetails.append("{");

        		getConnectorArrayJson(artLogConnectors, artLoggerDetails);
        		artLoggerDetails.append(",");
        		getSeverityJson(artLogSeverity, artLoggerDetails);
        		artLoggerDetails.append(",");
        		getExceptionJson(artLogExceptions, artLoggerDetails);
        		artLoggerDetails.append(",");
        		getArchitectureLayers(artLogArchitectureLayers, artLoggerDetails);
        		artLoggerDetails.append(",");
        		getEventActionJson(artLogEventActions, artLoggerDetails);
        		artLoggerDetails.append(",");
        		getStatusJson(artLogStatusList, artLoggerDetails);

        		artLoggerDetails.append("}");*/
        		responseBean.add("success", true);
        		responseBean.add("message", "Successfully retrived ");
        		responseBean.add("data", artLoggerDetails.toString());
        		return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);
        		//			return artLoggerDetails.toString();
        	} else {
        		responseBean.add("success", false);
        		responseBean.add("message", "Data cannot be retrived ");
        		//	        responseBean.add("data", artLoggerDetails.toString());
        		return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);
        		//			return null;
        	}
        }catch(Exception e) {
        	e.printStackTrace();
        }
		return null;
		 
	}
	
	private StringBuilder getSeverityJson(List<ArtLogSeverity> artLogSeverity, StringBuilder artLoggerDetails) {
		artLoggerDetails.append("\"alarmSeverity\":[ ");
		if (artLogSeverity != null && !artLogSeverity.isEmpty()) {
			for (ArtLogSeverity logSeverity : artLogSeverity) {
				artLoggerDetails.append(logSeverity.toSeverityDetails() + ",");
			}
			artLoggerDetails.deleteCharAt(artLoggerDetails.length() - 1);
		}
		artLoggerDetails.append("]");
		return artLoggerDetails;
	}
	
	private StringBuilder getConnectorArrayJson(List<ArtLogConnector> artLogConnectors, StringBuilder artLoggerDetails) {
		artLoggerDetails.append("\"connectorArray\":[");
		if (artLogConnectors != null && !artLogConnectors.isEmpty()) {
			for (ArtLogConnector connector : artLogConnectors) {
				artLoggerDetails.append(connector.toConnectorDetails() + ",");
			}
			artLoggerDetails.deleteCharAt(artLoggerDetails.length() - 1);
		}
		artLoggerDetails.append("]");
		return artLoggerDetails;
	}
	
	private StringBuilder getExceptionJson(List<ArtLogException> artLogExceptions, StringBuilder artLoggerDetails) {
		artLoggerDetails.append("\"exceptions\":[ ");
		if (artLogExceptions.size()>0 && artLogExceptions != null && !artLogExceptions.isEmpty()) {
			for (ArtLogException awsLogException : artLogExceptions) {
				artLoggerDetails.append(awsLogException.toJSON() + ",");
			}
			artLoggerDetails.deleteCharAt(artLoggerDetails.length() - 1);
		}
		artLoggerDetails.append("]");
		return artLoggerDetails;
	}

	private StringBuilder getStatusJson(List<ArtLogStatus> artLogStatusList, StringBuilder artLoggerDetails) {
		artLoggerDetails.append("\"alarmStatus\":[ ");
		if (artLogStatusList.size()>0 && artLogStatusList != null && !artLogStatusList.isEmpty()) {
			for (ArtLogStatus logStatus : artLogStatusList) {
				artLoggerDetails.append(logStatus.toJSON() + ",");
			}
			artLoggerDetails.deleteCharAt(artLoggerDetails.length() - 1);
		}
		artLoggerDetails.append("]");
		return artLoggerDetails;
	}
	
	private StringBuilder getEventActionJson(List<ArtLogEventAction> artLogEventActions, StringBuilder artLoggerDetails) {
		artLoggerDetails.append("\"eventActions\":[ ");
		if (artLogEventActions.size()>0 && artLogEventActions != null && !artLogEventActions.isEmpty()) {
			for (ArtLogEventAction eventAction : artLogEventActions) {
				artLoggerDetails.append(eventAction.toJSON() + ",");
			}
			artLoggerDetails.deleteCharAt(artLoggerDetails.length() - 1);
		}
		artLoggerDetails.append("]");
		return artLoggerDetails;		
	}
	
	private StringBuilder getArchitectureLayers(List<ArtLogArchitectureLayer> artLogArchitectureLayers, StringBuilder artLoggerDetails) {
		artLoggerDetails.append("\"architectureLayers\":[ ");
		if(artLogArchitectureLayers.size()>0 && artLogArchitectureLayers != null && !artLogArchitectureLayers.isEmpty()) {
			for(ArtLogArchitectureLayer appArchitectureLayer : artLogArchitectureLayers) {
				artLoggerDetails.append(appArchitectureLayer.toJSON() + ",");
			}
			artLoggerDetails.deleteCharAt(artLoggerDetails.length() - 1);
		}
		artLoggerDetails.append("]");
		return artLoggerDetails;
	}

	@Override
	@RequestMapping(value = "/updateLogAlarm", consumes = "application/json", method = RequestMethod.PUT)
	public HttpEntity<ResponseBean> updateLogAlarm(@RequestBody ArtLogDomain logDomain) {
		ResponseBean responseBean = new ResponseBean();
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
		ArtLogDomain awsLogDomain = null;
		if(logDomain.getdomainPkId() != null){
			awsLogDomain = artLogDomainRepository.findById(logDomain.getdomainPkId());
			logDomain.setArtLogBoundedContext(awsLogDomain.getArtLogBoundedContext());
			logDomain.setDomainId(awsLogDomain.getDomainId());
			logDomain.setDomainName(awsLogDomain.getDomainName());
		}
		artLogDomainRepository.update(logDomain);
		responseBean.add("success", true);
		responseBean.add("message", "Alarms updated successfully");
		return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);	
//		return null;
	}

	@Override
	@RequestMapping(value = "/getGridData", consumes = "application/json", method = RequestMethod.GET)
	public HttpEntity<ResponseBean> getGridData(@RequestBody String domainId) throws SecurityException {
		ResponseBean responseBean = new ResponseBean();
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
		List<ArtLogBoundedContext> boundedContexts= artLogBoundedContextRepository.findAll();
		for (Iterator<ArtLogBoundedContext> iterator = boundedContexts.iterator(); iterator.hasNext();) {
			ArtLogBoundedContext artLogBoundedContext = (ArtLogBoundedContext) iterator.next();
			List<ArtLogDomain> domainData = artLogBoundedContext.getArtLogDomain();
			for (Iterator<ArtLogDomain> iterator2 = domainData.iterator(); iterator2.hasNext();) {
				ArtLogDomain artLogDomain = (ArtLogDomain) iterator2.next();
				if(artLogDomain != null && artLogDomain.getdomainPkId().equals(domainId)) {
					
					List<ArtLogAlarm> artLogAlarms = artLogDomain.getArtLogAlarm();
					if(artLogAlarms.size()>0) {
						responseBean.add("success", true);
						responseBean.add("message", "Successfully retrived ");
						responseBean.add("data", artLogDomain);
						return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);
//						return null;
					}
				}
			}
		}
		responseBean.add("success", true);
		responseBean.add("message", "Error in grid data retrieval");
		return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);	
//		return null;
	}
}

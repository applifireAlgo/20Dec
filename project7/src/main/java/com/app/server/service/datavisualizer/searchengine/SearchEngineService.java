package com.app.server.service.datavisualizer.searchengine;
import com.app.config.appSetup.model.AppConfiguration;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import atg.taglib.json.util.JSONException;
import com.athena.server.pluggable.utils.helper.apicaller.APICaller;
import com.athena.server.pluggable.utils.helper.apicaller.APIResponse;
import com.athena.server.pluggable.utils.bean.ResponseBean;
import com.spartan.server.session.bizService.SessionDataMgtService;
import java.net.URLEncoder;

@RestController
@Scope("prototype")
@RequestMapping(value = "SearchEngineService")
public class SearchEngineService {

	@Autowired
	private AppConfiguration appconfig;
	@Autowired
	private SessionDataMgtService sessionDataAttribute;

	@RequestMapping(value = "/getAvailableLanguages", produces = "Application/json; Charset=UTF-8", method = RequestMethod.GET)
	public HttpEntity<ResponseBean> getAvailableLanguages(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
		ResponseBean responseBean = new ResponseBean();
		org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
		APIResponse apiResponse = null;
		try {
			APICaller caller = new APICaller(appconfig.getSearchEngineConfig().getSearchAppURL() + "secure/SearchEngineController/getAvailableLanguages", "POST");
			apiResponse = caller.getResponse();
			responseBean.add("success", true);
			responseBean.add("message", "Successfully retrived ");
			responseBean.add("data", apiResponse);
		} catch (Exception e) {
			httpStatus = org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
			responseBean.add("success", false);
			responseBean.add("message", "Data not retrived.");
			responseBean.add("data", apiResponse);
		}
		return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);
	}

	@RequestMapping(value = "/getSearchResult", produces = "Application/json; Charset=UTF-8", method = RequestMethod.GET)
	public HttpEntity<ResponseBean> getSearchResult(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
		ResponseBean responseBean = new ResponseBean();
		org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
		APIResponse apiResponse = null;
		try {
			String searchString = request.getParameter("searchString");
			String returnType = request.getParameter("returnType");
			String language = request.getParameter("oprationType");

			Object sessionData = sessionDataAttribute.getSessionData("userAccessQuery");
			QueryParameterPreparer queryParameterPreparer = new QueryParameterPreparer();
			String queryParam = queryParameterPreparer.getDateTimeCriteria(searchString, sessionData);
			queryParam = URLEncoder.encode(queryParam, "UTF-8");
			APICaller caller = new APICaller(appconfig.getSearchEngineConfig().getSearchAppURL() + "secure/SearchEngineController/getSearchResult?searchString=" + queryParam + "&oprationType="
					+ language, "POST");

			apiResponse = caller.getResponse();
			responseBean.add("success", true);
			responseBean.add("message", "Successfully retrived.");
			responseBean.add("data", apiResponse);

		} catch (Exception e) {
			httpStatus = org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
			responseBean.add("success", false);
			responseBean.add("message", "Data not retrived.");
			responseBean.add("data", apiResponse);
		}
		return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);
	}

}

package com.app.server.businessservice.appbasicsetup.aaa;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.athena.server.pluggable.utils.bean.ResponseBean;
import com.spartan.pluggable.auth.LoginCredentials;
import com.spartan.pluggable.auth.TokenCredential;
public interface AuthenticateBusinessService {
	
	public HttpEntity<ResponseBean> authenticate(@RequestBody LoginCredentials loginBean, HttpServletRequest request, HttpServletResponse response, HttpSession session);

	public HttpEntity<ResponseBean> reauthenticate(TokenCredential tokenBean, HttpServletRequest request, HttpServletResponse response, HttpSession session);
	
	public HttpEntity<ResponseBean> checkLoginStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session);

}

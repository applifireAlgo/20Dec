package com.app.server.service.appbasicsetup.aaa;
import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.athena.server.pluggable.utils.bean.ResponseBean;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
import com.athena.server.pluggable.interfaces.CommonEntityInterface;
import com.spartan.pluggable.auth.PluggableAuthConnector;
import com.spartan.pluggable.exception.layers.db.PersistenceException;
import com.spartan.server.interfaces.LoginSessionDataRepository;
import com.spartan.server.interfaces.LoginSessionInterface;
import com.spartan.server.interfaces.LoginSessionRepositoryInterface;
import com.spartan.server.session.bizService.SessionDataMgtService;

@RestController
@RequestMapping(value = "Logout", method = RequestMethod.POST, consumes = { "application/json", "application/xml" })
public class LogoutService {

	@Autowired
	LoginSessionRepositoryInterface loginInterfaceRepository;
	@Autowired
	private SessionDataMgtService sessionDataAttributeService;

	@Autowired
	private LoginSessionDataRepository loginSeesionDataRepository;

	@Autowired
	private RuntimeLogInfoHelper runtimeLogInfoHelper;

	@Autowired
	private PluggableAuthConnector userAuthenticator;

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<ResponseBean> logoutExecute(HttpSession session) {
		org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
		LoginSessionInterface loginSession = null;
		try {
			loginSession = loginInterfaceRepository.findById(session.getAttribute("usidHash").toString());

			if (loginSession != null) {
				loginSession.setLogoutTime(new Timestamp(System.currentTimeMillis()));
				loginSession.setSystemInformation(CommonEntityInterface.RECORD_TYPE.DELETE);
				loginInterfaceRepository.updateSession(loginSession);

				boolean lououtStatus = userAuthenticator.logout();
				if (lououtStatus) {
					sessionDataAttributeService.removeSessionAllAttributes();
					userAuthenticator.terminateSession();
				}

			}

		} catch (PersistenceException e1) {
			System.err.println("Requested Session Id not found");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		ResponseBean responseBean = new ResponseBean();
		responseBean.add("success", "true");
		responseBean.add("message", "User logged out Successfully");
		return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);
	}
}

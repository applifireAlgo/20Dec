package com.app.server.businessservice.appbasicsetup.aaa;
import com.app.config.appSetup.model.AppConfiguration;

import com.app.util.IpAddress;

import com.app.bean.TokenCredentialBean;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import atg.taglib.json.util.JSONObject;

import com.athena.server.pluggable.utils.AppLoggerConstant;
import com.athena.server.pluggable.utils.bean.ResponseBean;
import com.athena.server.pluggable.utils.helper.JSONObjectMapperHelper;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
import com.google.gson.JsonObject;
import com.spartan.pluggable.auth.LoginCredentials;
import com.spartan.pluggable.auth.PluggableAuthConnector;
import com.spartan.pluggable.auth.TokenCredential;
import com.spartan.pluggable.auth.UserBean;
import com.spartan.pluggable.exception.auth.AccessDeniedException;
import com.spartan.pluggable.exception.auth.AccountExpired;
import com.spartan.pluggable.exception.auth.InvalidLoginIdException;
import com.spartan.pluggable.exception.auth.PasswordExpiredException;
import com.spartan.pluggable.exception.auth.SessionTimeOutException;
import com.spartan.pluggable.exception.layers.db.PersistenceException;
import com.spartan.pluggable.logger.alarms.AppAlarm;
import com.spartan.pluggable.logger.api.LogManager;
import com.spartan.pluggable.logger.api.LogManagerFactory;
import com.spartan.server.authenticate.repository.AuthenticateRepository;
import com.spartan.server.interfaces.LoginSessionDataRepository;
import com.spartan.server.interfaces.LoginSessionInterface;
import com.spartan.server.interfaces.LoginSessionRepositoryInterface;
import com.spartan.server.interfaces.SessionDataInterface;
import com.spartan.server.interfaces.UserInterface;
import com.spartan.server.interfaces.UserRepositoryInterface;
import com.spartan.server.session.bizService.SessionDataMgtService;

@Service
public class AuthenticateBusinessServiceImpl implements AuthenticateBusinessService {

	@Autowired
	private RuntimeLogInfoHelper runtimeLogInfoHelper;

	@Autowired
	private PluggableAuthConnector userAuthenticator;

	@Autowired
	private SessionDataGeneration sessionDataGeneration;

	@Autowired
	private CookieValidation cookieValidation;

	@Autowired
	private LoginSessionRepositoryInterface loginInterfaceRepository;

	@Autowired
	AuthenticateRepository authenticateRepository;

	@Autowired
	private SessionDataMgtService sessionDataAttribute;

	@Autowired
	private UserRepositoryInterface userRepo;

	@Autowired
	private AppConfiguration appConfig;

	@Autowired
	private LoginSessionDataRepository loginSessionDataRepository;

	@Autowired
	private UserAccessRightService userAccessRightService;
	private LogManager Log = LogManagerFactory.getInstance(AppLoggerConstant.LOGGER_ID);

	private enum DATA_TYPE {
		NUMBER(1), DATE_TIME(2), STRING(3), BOOLEAN(4), JSON(5);

		private int value;

		private DATA_TYPE(int value) {
			this.value = value;
		}
	}

	@Override
	public HttpEntity<ResponseBean> authenticate(@RequestBody LoginCredentials loginBean, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		ResponseBean responseBean = new ResponseBean();
		org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
		boolean success = true;
		String message = "";
		UserBean userBean = null;

		try {
			if (request.getCookies() != null) {
				for (Cookie cookie : request.getCookies()) {
					if (cookie.getName().equalsIgnoreCase("cookieName")) {
						String cookieValue = cookie.getValue();
					}

				}
			}

			try {
				String customerId = authenticateRepository.getUserDetailsSQL(loginBean.getLoginId());
				if (customerId != null) {
					session.setAttribute("customerId", customerId);
					runtimeLogInfoHelper.setCustomerId(customerId);
				}
			} catch (Exception e) {
				System.err.println("Column for MultitenantFields not found");
			}

			boolean isUserAthenticated = false;
			if (appConfig.getAuthenticationConfig().getAuthPlugin().getAuthType() == 1) {

				if (!userLoggedIn(session)) {
					isUserAthenticated = userAuthenticator.authenticate(loginBean);
					if (isUserAthenticated) {
						userBean = userAuthenticator.getAuthenticatedUser();
					}
				} else {
					isUserAthenticated = userAuthenticator.authenticate(loginBean);
					if (isUserAthenticated)
						userBean = userAuthenticator.getAuthenticatedUser();
				}
				if (userBean != null) {
					UserInterface userData = userRepo.getByUserId(userBean.getProperties().get("userId").toString());
					Integer changePasswordInNextLogin = userData.getChangePasswordNextLogin();
					if (changePasswordInNextLogin != null && changePasswordInNextLogin == 1) {
						responseBean.add("changePassword", true);
						success = false;
						message = "Your account is applicable to change password in next login!<br>For login you must change password";
					}

					/**
					 * ADDING FLAG TO SET SECURITY QUESTION WHEN FLAT IS TRUE
					 * USER GETS THE POP-UP TO SET SECURITY QUESTION FROM USER
					 * PROFILE OPTION
					 */
					boolean setSecutityQuestions = false;
					if (userData.getTotalNumberOfPassRecovery() == 0) {
						setSecutityQuestions = true;
					}
					responseBean.add("setSecutityQuestions", setSecutityQuestions);
				}
			} else if (appConfig.getAuthenticationConfig().getAuthPlugin().getAuthType() == 2) {
				isUserAthenticated = userAuthenticator.authenticate(loginBean);
				if (isUserAthenticated) {
					userBean = userAuthenticator.getAuthenticatedUser();
				}
			}

			if (userBean != null) {
				sessionDataGeneration.setSessionData(session, request, userBean.getProperties());
				saveSessionData(session, request, userBean);

				Cookie cookie = cookieValidation.getSessionCookies(session, request, userBean, session.getAttribute("qKeHash").toString());
				response.addCookie(cookie);
				Cookie timeZoneCookie = cookieValidation.setTimeZoneCookie(session, request, userBean);
				response.addCookie(timeZoneCookie);
				message = "Authentication success";
				addUserAccessDatainSession(session);

			} else {
				throw new InvalidLoginIdException("Invalid Login Id or Password","ABSAA254901401",null);
			}

			/* ADDING AUTHENTICATED USER DETAILS IN RESPONSE */
			JsonObject userInfo = new JsonObject();
			userInfo.addProperty("firstName", userBean.getProperties().get("firstName").toString());
			userInfo.addProperty("middleName", userBean.getProperties().get("middleName").toString());
			userInfo.addProperty("lastName", userBean.getProperties().get("lastName").toString());
			userInfo.addProperty("emailId", userBean.getProperties().get("emailId").toString());
			userInfo.addProperty("phoneNumber", userBean.getProperties().get("phoneNumber").toString());

			userInfo.addProperty("XA_TID", userBean.getProperties().get("timeZoneId").toString());

			loginInterfaceRepository.updateLastAccessTime(userBean.getProperties().get("userId").toString(), session.getAttribute("usidHash").toString(), new java.sql.Timestamp(
					System.currentTimeMillis()));
			AppAlarm appAlarm = Log.getAlarm("ABSAA124900200");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", success);
			responseBean.add("message", message);
			responseBean.add("userinfo", userInfo.toString());
			Log.out.println("ABSAA124900200", runtimeLogInfoHelper.getRequestHeaderBean(), "authenticate", loginBean.getLoginId());
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

		} catch (InvalidLoginIdException e) {
			e.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm(e.getAppAlarmId());
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", String.format(appAlarm.getMessage(), e.getMessage()));
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());

			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "authenticate", loginBean.getLoginId(), "Invald loginId and Password");
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		} catch (AccountExpired e1) {
			e1.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm("ABSAA254901401");
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", appAlarm.getMessage());
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "authenticate", loginBean.getLoginId(), "Your Account is Expired");
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		} catch (PasswordExpiredException e2) {
			e2.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm("ABSUM343953403");
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", appAlarm.getMessage());
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "authenticate", loginBean.getLoginId(), "User details not found");
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		} catch (PersistenceException e2) {
			e2.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm("ABSAA254901401");
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", appAlarm.getMessage());
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "authenticate", loginBean.getLoginId(), "User details not found");
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		} catch (com.spartan.pluggable.exception.auth.AccountLockedException e3) {
			e3.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm(e3.getAppAlarmId());
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", appAlarm.getMessage());
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "authenticate", loginBean.getLoginId(),
					String.format(appAlarm.getMessage(), e3.getMessage()));
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		} catch (Exception e4) {
			e4.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm("ABSAA154900400");
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", " Error in creating entity" + e4.getMessage());
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "authenticate", loginBean.getLoginId(), e4);
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		}
	}

	@Override
	public HttpEntity<ResponseBean> reauthenticate(@RequestBody TokenCredential tokenBean, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ResponseBean responseBean = new ResponseBean();
		org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
		TokenCredentialBean tockenCredential = null;
		boolean success = true;
		String message = "";
		UserBean userBean = null;
		try {
			AppAlarm appAlarm = null;
			Cookie[] cookies = request.getCookies();
			String sessionId = null;
			// Checks the null value for cookies
			if (null != cookies) {
				// Iteration for cookies presence
				for (int i = 0; i < cookies.length; i++) {
					System.out.println("outer cookie--------" + cookies[i].getName() + " : " + cookies[i].getValue());
					if (cookies[i].getName().contains("XA_ID")) {
						sessionId = cookies[i].getValue();
					}
				}
			}
			/** For Multitent reauthentication **/
			List<SessionDataInterface> sessiondatalst = loginSessionDataRepository.findByAppSessionId(sessionId);
			String loginId = "";
			for (int i = 0; i < sessiondatalst.size(); i++) {
				SessionDataInterface sessionData = sessiondatalst.get(i);
				if (sessionData.getSessionKey().equalsIgnoreCase("loginid")) {
					loginId = sessionData.getStringValue();
					break;
				}
			}
			try {
				String customerId = authenticateRepository.getUserDetailsSQL(loginId);
				if (customerId != null) {
					session.setAttribute("customerId", customerId);
					runtimeLogInfoHelper.setCustomerId(customerId);
				}
			} catch (Exception e) {
				System.err.println("Column for MultitenantFields not found");
			}

			boolean isUserAthenticated = false;

			if (sessionId != null && !sessionId.isEmpty()) {
				tockenCredential = new TokenCredentialBean(tokenBean.getTokenCredentials(), sessionId);
			}

			if (appConfig.getAuthenticationConfig().getAuthPlugin().getAuthType() == 1) {

				isUserAthenticated = userAuthenticator.reAuthenticate(tockenCredential);

				if (isUserAthenticated) {
					userBean = userAuthenticator.getAuthenticatedUser();
				}
				if (userBean != null) {
					UserInterface userData = userRepo.getByUserId(userBean.getProperties().get("userId").toString());
					Integer changePasswordInNextLogin = userData.getChangePasswordNextLogin();

					if (changePasswordInNextLogin != null) {
						if (changePasswordInNextLogin == 1) {
							responseBean.add("changePassword", true);
							success = false;
							message = "Your account is applicable to change password in next login!<br>For login you must change password";
						}
					}
				}

			} else if (appConfig.getAuthenticationConfig().getAuthPlugin().getAuthType() == 2) {
				isUserAthenticated = userAuthenticator.reAuthenticate(tockenCredential);
				if (isUserAthenticated) {
					userBean = userAuthenticator.getAuthenticatedUser();
				}
			} 
			if (userBean != null) {
				sessionDataGeneration.setSessionData(session, request, userBean.getProperties());
				saveSessionData(session, request, userBean);

				Cookie cookie = cookieValidation.getSessionCookies(session, request, userBean, session.getAttribute("qKeHash").toString());
				response.addCookie(cookie);
				Cookie timeZoneCookie = cookieValidation.setTimeZoneCookie(session, request, userBean);
				response.addCookie(timeZoneCookie);


				message = "Authentication success";
				addUserAccessDatainSession(session);
				appAlarm = Log.getAlarm("ABSAA124900200");
				responseBean.addAlarm(appAlarm);
				responseBean.add("success", success);
				responseBean.add("message", message);
				Log.out.println("ABSAA124900200", runtimeLogInfoHelper.getRequestHeaderBean(), "reauthenticate", session.getAttribute("loginId").toString());
				return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

			} else {
				throw new InvalidLoginIdException("Invalid Login Id or Password","ABSAA254901401",null);

			}
		} catch (InvalidLoginIdException e) {
			e.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm(e.getAppAlarmId());
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", String.format(appAlarm.getMessage(), e.getMessage()));
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "reauthenticate",
					String.format(appAlarm.getMessage(), e.getMessage()));
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		} catch (AccountExpired e1) {
			e1.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm("ABSAA254901401");
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", appAlarm.getMessage());
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "reauthenticate",
					String.format(appAlarm.getMessage(), e1.getMessage()));
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		} catch (PasswordExpiredException e2) {
			e2.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm("ABSUM343953403");
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", appAlarm.getMessage());
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "reauthenticate", 
					String.format(appAlarm.getMessage(), e2.getMessage()));
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		} catch (PersistenceException e2) {
			e2.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm("ABSAA254904403");
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", appAlarm.getMessage());
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "reauthenticate", 
					String.format(appAlarm.getMessage(), e2.getMessage()));
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		} catch (com.spartan.pluggable.exception.auth.AccountLockedException e3) {
			e3.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm(e3.getAppAlarmId());
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", appAlarm.getMessage());
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "reauthenticate",
					String.format(appAlarm.getMessage(), e3.getMessage()));
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		} catch (Exception e4) {
			e4.printStackTrace();
			AppAlarm appAlarm = Log.getAlarm("ABSAA154900400");
			ResponseBean exceptionbean = new ResponseBean(appAlarm);
			exceptionbean.add("message", " Error in creating entity" + e4.getMessage());
			httpStatus = HttpStatus.valueOf(appAlarm.getAlarmStatus());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "reauthenticate", 
					String.format(appAlarm.getMessage(), e4.getMessage()));
			return new org.springframework.http.ResponseEntity<ResponseBean>(exceptionbean, httpStatus);

		}

	}

	public void reauthenticate(HttpServletRequest request) {
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equalsIgnoreCase("cookieName")) {
				String cookieValue = cookie.getValue();
			}
		}
	}

	private boolean userLoggedIn(HttpSession session) {
		LoginSessionInterface loginSession = null;
		try {
			System.out.println("session.getAttribute(\"usidHash\").toString() " + session.getAttribute("usidHash").toString());
			loginSession = loginInterfaceRepository.findById(session.getAttribute("usidHash").toString());

		} catch (PersistenceException e1) {
			System.out.println("Requested Session Id not found");
		} catch (Exception e1) {
			System.out.println("Requested Session Id not found");
		}
		if (loginSession != null) {
			if (loginSession.getActiveStatus() == 1) {
				return true;
			} else {
				return false;
			}
		}

		else {
			return false;
		}
	}

	@Override
	public HttpEntity<ResponseBean> checkLoginStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		ResponseBean responseBean = new ResponseBean();
		org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;

		try {
			String cookieValue = cookieValidation.getCookieValue(request, "XA_ID");
			if (cookieValue.length() > 0) {
				try {
					LoginSessionInterface loginSessionIntf = loginInterfaceRepository.findById(cookieValue);
					if (loginSessionIntf != null) {

						Timestamp currentTime = new Timestamp(System.currentTimeMillis());

						String usidHash = (String) sessionDataAttribute.getSessionData("usidHash");
						String userJson = (String) sessionDataAttribute.getSessionData(usidHash).toString();
						JSONObject json = new JSONObject(userJson);
						JSONObject user = json.getJSONObject("user");
						int sessionTimeOut = user.getInt("sessionTimeout");

						if ((currentTime.getTime() - loginSessionIntf.getLastAccessTime().getTime()) < (sessionTimeOut * 1000)) {

							responseBean.add("success", true);
							responseBean.add("message", "Already logged in");
						} else {
							responseBean.add("success", false);
							responseBean.add("message", " Error " + new AccessDeniedException("Session expired", "APSAN154101401", new AccessDeniedException()).getMessage());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					responseBean.add("success", false);
					responseBean.add("message", " Error " + e.getMessage());
					httpStatus = org.springframework.http.HttpStatus.BAD_REQUEST;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseBean.add("success", false);
			responseBean.add("message", " Error " + e.getMessage());
			httpStatus = org.springframework.http.HttpStatus.BAD_REQUEST;
		}

		return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);
	}

	private void saveSessionData(HttpSession session, HttpServletRequest request, UserBean userBean) throws Exception {
		long currentTime = System.currentTimeMillis();

		Enumeration<String> sessionNamesEnumeration = session.getAttributeNames();

		List<String> sessionattributeNames = new ArrayList<String>();

		while (sessionNamesEnumeration.hasMoreElements()) {
			sessionattributeNames.add(sessionNamesEnumeration.nextElement());
		}

		LoginSessionInterface loginSession = null;
		try {
			loginSession = loginInterfaceRepository.findById(session.getId());
		} catch (PersistenceException e1) {
			System.err.println("Requested Session Id not found");
		} catch (Exception e1) {
			System.err.println("Requested Session Id not found");
		}

		if (loginSession != null) {
			loginSession.setLoginTime(new Timestamp(currentTime));
			loginSession.setLogoutTime(null);
			try {
				loginInterfaceRepository.updateSession(loginSession);
			} catch (PersistenceException e) {
				e.printStackTrace();
			}
		} else {
			IpAddress ipaddress = new IpAddress(request.getRemoteHost());
			try {

				loginInterfaceRepository.saveSession(userBean.getProperties().get("userId").toString(), session.getAttribute("usidHash").toString(), session.getId(),
						new Timestamp(currentTime), new Timestamp(currentTime), request.getRemoteHost(), ipaddress.getIPAddressAsLong(), 0, request.getHeader("User-Agent"));

				// saving userId
				loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.STRING.value, null,
						userBean.getProperties().get("userId").toString(), null, null, null, session.getAttribute("usidHash").toString(), "userId");

				// saving usidHash
				loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.STRING.value, null,
						session.getAttribute("usidHash").toString(), null, null, null, session.getAttribute("usidHash").toString(), "usidHash");

				// saving usidKeyHash

				loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.JSON.value, null, null,
						session.getAttribute(session.getAttribute("usidHash").toString()).toString(), null, null, session.getAttribute("usidHash").toString(), session
								.getAttribute("usidHash").toString());

				// saving qkeyHash
				loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.STRING.value, null,
						session.getAttribute("qKeHash").toString(), null, null, null, session.getAttribute("usidHash").toString(), "qKeHash");

				// saving loginId
				loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.STRING.value, null,
						session.getAttribute("loginId").toString(), null, null, null, session.getAttribute("usidHash").toString(), "loginID");

				// saving clientIpPort
				loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.STRING.value, null,
						session.getAttribute("clientIP").toString(), null, null, null, session.getAttribute("usidHash").toString(), "clientIP");

				// saving clientPort
				loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.NUMBER.value,
						Integer.parseInt(session.getAttribute("clientPort").toString()), null, null, null, null, session.getAttribute("usidHash").toString(), "clientPort");

				// saving contactId
				loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.STRING.value, null,
						session.getAttribute("contactId").toString(), null, null, null, session.getAttribute("usidHash").toString(), "contactId");

				// saving contactId
				loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.STRING.value, null,
						session.getAttribute("timeZone").toString(), null, null, null, session.getAttribute("usidHash").toString(), "timeZone");

				// saving contactId
				loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.STRING.value, null,
						session.getAttribute("timeZoneId").toString(), null, null, null, session.getAttribute("usidHash").toString(), "timeZoneId");

				if(runtimeLogInfoHelper.getCustomerId()!=null && runtimeLogInfoHelper.getCustomerId()!="")
				{
					// saving contactId
					loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.STRING.value, null,
							session.getAttribute("customerId").toString(), null, null, null, session.getAttribute("usidHash").toString(), "customerId");
				}
			} catch (PersistenceException e) {
				e.printStackTrace();
			}
		}

		session.setAttribute("userAccessCode", userBean.getProperties().get("userAccessCode").toString());
		String json = new JSONObjectMapperHelper().toJsonString(userBean.getProperties().get("userAccessCode").toString());
		loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.JSON.value, null, null, json,
				null, null, session.getAttribute("usidHash").toString(), "userAccessCode");
	}

	public void addUserAccessDatainSession(HttpSession session) throws Exception {
		// TODO Auto-generated method stub
		try {
			String finalString = userAccessRightService.getUserAccessData();
			loginSessionDataRepository.saveSessionData(session.getAttribute("userId").toString(), session.getAttribute("userId").toString(), DATA_TYPE.STRING.value, null,
					finalString, null, null, null, session.getAttribute("usidHash").toString(), "userAccessQuery");
		} catch (Exception ex) {
			System.err.println("Error in addUserAccessDatainSession" + ex.getMessage());
		}
	}

}

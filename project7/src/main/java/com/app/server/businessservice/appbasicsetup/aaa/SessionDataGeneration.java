package com.app.server.businessservice.appbasicsetup.aaa;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.athena.server.pluggable.utils.HashAlgorithms;
import com.spartan.pluggable.exception.auth.AccessDeniedException;
import com.spartan.server.authenticate.repository.AuthenticateRepository;
import com.spartan.server.interfaces.UserAuthentication;

@Component
public class SessionDataGeneration {

	@Autowired
	private AuthenticateRepository authenticateRepository;

	public void setSessionData(HttpSession session, HttpServletRequest request, HashMap<String, Object> attributes) {

		int userSessionTimeout = Integer.parseInt(attributes.get("sessionTimeout").toString());
		String usidKey = "NOKEY";
		usidKey = generateUsidKey(session.getId(), attributes.get("loginId").toString(), attributes.get("credentials").toString(), request);
		session.setMaxInactiveInterval(userSessionTimeout);
		setSessionAttributes(session, "clientIP", request.getRemoteHost());
		setSessionAttributes(session, "clientPort", request.getRemotePort());

		setSessionAttributes(session, "loginId", attributes.get("loginId"));
		setSessionAttributes(session, "userId", attributes.get("userId"));
		setSessionAttributes(session, "timeZone", attributes.get("timeZone"));
		setSessionAttributes(session, "timeZoneId", attributes.get("timeZoneId"));
		setSessionAttributes(session, "sessionTimeout", attributes.get("sessionTimeout"));

		setSessionAttributes(session, "contactId", attributes.get("contactId"));
		setSessionAttributes(session, "userAccessCode", attributes.get("userAccessCode"));

		setSessionAttributes(session, "qKeHash", generateQKeHash(attributes.get("loginId").toString(), attributes.get("credentials").toString(), session.getId(), request, usidKey));

		// setSessionAttributes(session, "usidHash",
		// generateUsidKey(session.getId(),
		// attributes.get("loginId").toString(),
		// attributes.get("credentials").toString(), request));
		String userHash = generateUserHash(session.getId(), attributes.get("loginId").toString(), attributes.get("credentials").toString(), request, usidKey);
		// setSessionAttributes(session,generateUsidKey(session.getId(),
		// attributes.get("loginId").toString(),
		// attributes.get("credentials").toString(), request), userHash);
		saveUserDataToSession(session, userHash, attributes, usidKey);
		setSessionAttributes(session, "userId", attributes.get("userId"));
	}

	private HttpSession setSessionAttributes(HttpSession session, String key, Object value) {
		session.setAttribute(key, value);
		return session;
	}

	private String generatePasswordHash(String userCredentials) {
		/** Store User specific info into session */

		String pswdHash = "";
		try {
			pswdHash = HashAlgorithms.getInstance().computeHash(userCredentials, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pswdHash;
	}

	private String generateUsidKey(String sessionId, String loginId, String userCredentials, HttpServletRequest request) {
		Date date = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append(sessionId).append("|");
		sb.append(loginId).append("|");
		sb.append(request.getRemoteHost()).append("|");
		sb.append(request.getRemotePort()).append("|");
		sb.append(date.getTime()).append("|");
		sb.append(generatePasswordHash(userCredentials)).append("|");
		return sb.toString();// Plain User Session ID
	}

	private String generateUserHash(String sessionId, String loginId, String userCredentials, HttpServletRequest request, String _usidKey) {

		String userHash = "";
		try {
			userHash = HashAlgorithms.getInstance().computeHash(_usidKey, HashAlgorithms.SHA_256);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userHash;
	}

	public String generateQKeHash(String loginId, String userCredentials, String sessionId, HttpServletRequest request, String _usidKey) {
		// Create Unique Application Session ID for the Cookie
		String qKeyHash = "";
		String pswdHash = "";
		// Create the Session IDs (User and Cookie) after the successful
		// validation.
		String qKe = "";

		try {
			pswdHash = HashAlgorithms.getInstance().computeHash(userCredentials, 1);

			qKe = _usidKey;// Plain Cookie Session ID

			try {
				qKeyHash = HashAlgorithms.getInstance().computeHash(qKe, HashAlgorithms.SHA_256);
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new AccessDeniedException("Unable to compute Cookie Session ID QKe using Key 2", "ABSAA154900400", new AccessDeniedException());
			}

		} catch (Exception ignored) {
			ignored.printStackTrace();
		}

		return qKeyHash;
	}

	private void saveUserDataToSession(HttpSession session, String userHash, HashMap<String, Object> attributes, String _usidkey) {
		try {

			UserAuthentication userAuth = authenticateRepository.getUserByLoginId(attributes.get("loginId").toString());
			if (userAuth != null) {
				userAuth.setContainerSessionId(session.getId());
				userAuth.setUserHash(userHash); // Set the User Hash in the
				userAuth.setQKeHash(session.getAttribute("qKeHash").toString());
				setSessionAttributes(session, "usidHash", userHash);
				session.setAttribute(userHash, userAuth.toJsonString());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

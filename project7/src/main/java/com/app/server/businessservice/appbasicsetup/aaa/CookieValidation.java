package com.app.server.businessservice.appbasicsetup.aaa;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.spartan.pluggable.auth.UserBean;
import com.spartan.pluggable.exception.auth.AccessDeniedException;

@Component
public class CookieValidation {
	final private String COOKIE_ID = "XA_ID";

	public Cookie getSessionCookies(HttpSession session, HttpServletRequest request, UserBean user,String qkey) {
		Cookie cookie = null;
		try {

			int actualTimeout = Integer.parseInt(user.getProperties().get("sessionTimeout").toString());

			// String cookieTokenName = "XA" + session.getId();// Create Cookie
			// String cookieTokenName = "XA_ID";// Create Cookie
			session.setAttribute("CookieTokenName", COOKIE_ID);
			// Token Name
			cookie = new Cookie(COOKIE_ID, qkey);
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(actualTimeout * 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cookie;
	}
	
	public Cookie setTimeZoneCookie(HttpSession session, HttpServletRequest request, UserBean user) {
		Cookie cookie = null;
		try {

			int actualTimeout = Integer.parseInt(user.getProperties().get("sessionTimeout").toString());
			cookie = new Cookie("XA_TID",user.getProperties().get("timeZoneId").toString());
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(actualTimeout * 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cookie;
	}

	// /* skip checking cookies only when mainPageController is called */
	// if (user.isCheckCookie()) {
	// if (!checkMainPageControllerPattern(request, this.urlPatterns)) {
	// // Validate QKESession ID
	// validateQKESessionID(session, request);
	// }
	// }

	/**
	 * Validates the session cookies
	 * 
	 * */
	public void validateSessionCookie(final HttpSession mapSession, final HttpServletRequest request) throws AccessDeniedException {
		validateQKESessionID(mapSession, request);
	}

	/**
	 * Validate the XTOKEN value from the 512 Bit message digest
	 * 
	 * @return
	 * @throws AccessDeniedException
	 */
	private boolean validateQKESessionID(final HttpSession mapSession, final HttpServletRequest request) throws AccessDeniedException {
		String qKeSessionId = (String) mapSession.getAttribute("qKeHash");
		if (qKeSessionId == null) {
			System.err.println("Invalid Cookie SessionID. Possible Session Attack from IP Address = " + request.getRemoteHost());
			throw new AccessDeniedException("Invalid Cookie SessionID. Possible Session Attack from IP Address = " + request.getRemoteHost(),"ABSAA154900400",new AccessDeniedException());
		}

		// Compare the Cookie App Session ID with the qKe stored in the Users
		// Session
		if (!qKeSessionId.equalsIgnoreCase(getQKESessionIDFromCookies(mapSession, request))) {
			System.err.println("Session ID Expired. Possible Session Attack from IP Address = " + request.getRemoteHost());
			throw new AccessDeniedException("Session ID Expired. Possible Session Attack from IP Address = " + request.getRemoteHost(),"ABSAA154900400",new AccessDeniedException());
		}

		return true;
	}

	/**
	 * Get XTOKEN from Cookies
	 * 
	 * @return String
	 * @throws AccessDeniedException
	 */
	private String getQKESessionIDFromCookies(final HttpSession mapSession, final HttpServletRequest request) throws AccessDeniedException {
		String cookieValue = null;
		String cookieTokenName = getCookieTokenName(mapSession);
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equalsIgnoreCase(cookieTokenName)) {
				cookieValue = cookie.getValue();
				break;
			}
		}
		if (cookieValue == null) {
			System.err.println("Invalid Request Cookie. Possible Session Attack from IP Address = " + request.getRemoteHost());
			throw new AccessDeniedException("Invalid Request Cookie. Possible Session Attack from IP Address = " + request.getRemoteHost(),"ABSAA154900400",new AccessDeniedException());
		}
		return cookieValue;
	}

	/**
	 * Get the Cookie Token Name which is generated based the following
	 * 
	 * XA + User Application ID
	 * 
	 * @return
	 * @throws AccessDeniedException
	 */
	private final String getCookieTokenName(final HttpSession mapSession) throws AccessDeniedException {
		return (String) mapSession.getAttribute("CookieTokenName");
	}

	public String getCookieValue(HttpServletRequest request, String key) {
		String cookieValue = "";
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equalsIgnoreCase(key)) {
					cookieValue = cookie.getValue();
					break;
				}
			}
		}
		return cookieValue;
	}

	public String getCookieName(HttpServletRequest request) {
		String cookieName = "";
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().startsWith("XA_ID")) {
				cookieName = cookie.getName();
				break;
			}
		}

		if (cookieName.length() > 0) {
			cookieName = cookieName.substring(2);
		}
		return cookieName;
	}

}

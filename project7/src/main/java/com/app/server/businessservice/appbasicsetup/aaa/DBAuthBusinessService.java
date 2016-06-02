package com.app.server.businessservice.appbasicsetup.aaa;
import com.app.bean.UserInfoBeanImpl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spartan.pluggable.auth.LoginCredentials;
import com.spartan.pluggable.auth.PluggableAuthConnector;
import com.spartan.pluggable.auth.TokenCredential;
import com.spartan.pluggable.auth.UserBean;
import com.spartan.pluggable.exception.auth.AccountLockedException;
import com.spartan.pluggable.exception.auth.InvalidLoginIdException;
import com.spartan.pluggable.exception.auth.PasswordExpiredException;
import com.spartan.pluggable.exception.core.AppBaseException;
import com.spartan.pluggable.exception.core.BaseSecurityException;
import com.spartan.pluggable.exception.layers.db.PersistenceException;
import com.spartan.server.authenticate.repository.AuthenticateRepository;
import com.spartan.server.interfaces.LoginSessionInterface;
import com.spartan.server.interfaces.LoginSessionRepositoryInterface;
import com.spartan.server.interfaces.UserAuthentication;
import com.spartan.server.interfaces.UserInterface;
import com.spartan.server.interfaces.UserRepositoryInterface;
import com.athena.server.pluggable.utils.HashAlgorithms;
import com.spartan.server.password.interfaces.PasswordAlgoInterface;
import com.spartan.server.password.interfaces.PasswordAlgoRepositoryIntefrace;
import com.spartan.server.password.interfaces.PasswordPolicyInterface;
import com.spartan.server.password.interfaces.PasswordPolicyRepositoryInterface;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DBAuthBusinessService implements PluggableAuthConnector {

	@Autowired
	AuthenticateRepository authenticateRepository;

	@Autowired
	PasswordAlgoRepositoryIntefrace passwordAlgoRepository;

	@Autowired
	private PasswordPolicyRepositoryInterface passwordPolicyRepository;

	@Autowired
	AuthenticatePassword authenticatePassword;

	@Autowired
	private UserRepositoryInterface userRepo;

	@Autowired
	private LoginSessionRepositoryInterface loginInterfaceRepository;

	@Autowired
	AuthenticateRepository loginInfoRepository;
	private UserInfoBeanImpl userBean;

	@Override
	public boolean authenticate(final LoginCredentials _loginBean) throws BaseSecurityException {
		boolean isAuthenticate = false;
		UserAuthentication userAuthentication = null;

		try {
			userAuthentication = authenticateRepository.getUserByLoginId(_loginBean.getLoginId());
			if (userAuthentication != null) {
				isAuthenticate = checkUserAthentication(userAuthentication, _loginBean.getPassword());
				if (isAuthenticate) {
					userBean = (UserInfoBeanImpl) getUserBean(userAuthentication);
				}
			} else {
				throw new PersistenceException();
			}
		} catch (Exception e) {
			throw new InvalidLoginIdException("Invalid Login Id or Password","ABSAA254901401",null);
		}

		return isAuthenticate;
	}

	@Override
	public boolean reAuthenticate(final TokenCredential _tokenBean) throws BaseSecurityException {
		boolean isReAuthenticate = false;
		UserAuthentication userAuthentication = null;
		try {
			LoginSessionInterface loginSessionInterface = loginInterfaceRepository.findById(_tokenBean.getAppToken());
			userAuthentication = loginInfoRepository.findByUserId(loginSessionInterface.getUserId());
			if (userAuthentication != null) {
				isReAuthenticate = checkUserAthentication(userAuthentication, _tokenBean.getTokenCredentials());
				if (isReAuthenticate) {
					userBean = (UserInfoBeanImpl) getUserBean(userAuthentication);
				}
			}

		} catch (Exception e) {
			throw new InvalidLoginIdException("Invalid Login Id or Password","ABSAA254901401",null);
		}
		return isReAuthenticate;
	}

	@Override
	public UserBean getAuthenticatedUser() {
		return userBean;
	}

	@Override
	public boolean logout() {
		return true;
	}

	@Override
	public boolean terminateSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		session.invalidate();
		return true;
	}

	@Override
	public boolean validateOTP(final TokenCredential _tokenBean) throws BaseSecurityException {
		return false;
	}

	private boolean checkUserAthentication(UserAuthentication userAuthentication, String password) throws AppBaseException {
		String userEncodedPwd = "";
		boolean isUserAthentcate = false;

		if (userAuthentication.isDisabled()) {
			throw new AccountLockedException("Account Locked","ABSAA254902401",null);
		}

		if (userAuthentication.isPasswordExpired()) {
			throw new PasswordExpiredException("Password Expired","ABSUM343953403",null);
		}
		try {
			UserInterface userData = userRepo.getByUserId(userAuthentication.getUserId());
			Integer userAccountStatus = userData.getIsLocked();
			if (userAccountStatus != null && userAccountStatus == 1) {
				throw new AccountLockedException();
			} else {
				Boolean isPasswordValid = true;

				PasswordPolicyInterface passwordPolicy = passwordPolicyRepository.findAll().get(0);

				isPasswordValid = authenticatePassword.isPasswordValid(password, passwordPolicy);
				if (isPasswordValid) {
					try {

						PasswordAlgoInterface passwordAlgo = passwordAlgoRepository.findAll().get(0);
						userEncodedPwd = HashAlgorithms.getInstance().computeHash(password, passwordAlgo.getAlgorithm());

					} catch (Exception e) {
						throw new InvalidLoginIdException("Invalid Login Id or Password","ABSAA254901401",null);
					}

					if (userEncodedPwd.equals(userAuthentication.getCredential())) {
						isUserAthentcate = true;
					} else {
						throw new InvalidLoginIdException("Invalid Login Id or Password","ABSAA254901401",null);
					}
				}

			}
		} catch (Exception e) {
			throw new InvalidLoginIdException("Invalid Login Id or Password","ABSAA254901401",null);
		}
		return isUserAthentcate;

	}

	private UserBean getUserBean(UserAuthentication userAuthntication) {
		UserInfoBeanImpl userBean = new UserInfoBeanImpl();
		userBean.setLoginID(userAuthntication.getLoginId());
		// need to discuss
		// userBean.setAthenticated(true);
		userBean.setLocked(userAuthntication.isDisabled());
		HashMap<String, Object> propertiesMap = new HashMap<String, Object>();
		propertiesMap.put("loginId", userAuthntication.getLoginId());
		propertiesMap.put("userId", userAuthntication.getUserId());

		propertiesMap.put("timeZone", userAuthntication.getCoreContacts().getTimezone().getGmtLabel());
		propertiesMap.put("timeZoneId", userAuthntication.getCoreContacts().getTimezone().getTimeZoneLabel());

		propertiesMap.put("credentials", userAuthntication.getCredential());
		propertiesMap.put("sessionTimeout", userAuthntication.getSessionTimeout());

		propertiesMap.put("contactId", userAuthntication.getCoreContacts().getContactId());
		propertiesMap.put("userAccessCode", userAuthntication.getuserAccessCode());
 
		propertiesMap.put("firstName", (userAuthntication.getCoreContacts().getFirstName() == null ? "-" : userAuthntication.getCoreContacts().getFirstName()));
		propertiesMap.put("middleName", (userAuthntication.getCoreContacts().getMiddleName() == null ? "-" : userAuthntication.getCoreContacts().getMiddleName()));
		propertiesMap.put("lastName", (userAuthntication.getCoreContacts().getLastName() == null ? "-" : userAuthntication.getCoreContacts().getLastName()));
		propertiesMap.put("emailId", userAuthntication.getCoreContacts().getEmailId());
		propertiesMap.put("phoneNumber", userAuthntication.getCoreContacts().getPhoneNumber());

		userBean.setProperties(propertiesMap);

		return userBean;

	}

}

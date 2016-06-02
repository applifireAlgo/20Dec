package com.app.server.businessservice.appbasicsetup.usermanagement;
import com.app.bean.PasswordLogic;

import com.app.util.PasswordGenerator;

import com.app.server.repository.appbasicsetup.usermanagement.UserManagementRepository;

import com.app.server.businessservice.appbasicsetup.usermanagement.EMailSender;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.athena.server.pluggable.utils.AppLoggerConstant;
import com.athena.server.pluggable.utils.HashAlgorithms;
import com.athena.server.pluggable.utils.bean.ResponseBean;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.spartan.pluggable.logger.alarms.AppAlarm;
import com.spartan.pluggable.logger.api.LogManager;
import com.spartan.pluggable.logger.api.LogManagerFactory;
import com.spartan.server.authenticate.repository.AuthenticateRepository;
import com.spartan.server.interfaces.UserAuthentication;
import com.spartan.server.interfaces.UserDataInterface;
import com.spartan.server.interfaces.UserDataRepositoryInterface;
import com.spartan.server.interfaces.UserInterface;
import com.spartan.server.interfaces.UserRepositoryInterface;
import com.spartan.server.password.interfaces.PassRecoveryInterface;
import com.spartan.server.password.interfaces.PasswordAlgoInterface;
import com.spartan.server.password.interfaces.PasswordAlgoRepositoryIntefrace;
import com.spartan.server.password.interfaces.PasswordPolicyInterface;
import com.spartan.server.password.interfaces.PasswordPolicyRepositoryInterface;

@Service
public class PasswordGeneratorBizServiceImpl implements PasswordGeneratorBizService {

	@Autowired
	private PasswordPolicyRepositoryInterface passwordPolicyRepository;

	@Autowired
	private EMailSender email;

	@Autowired
	private AuthenticateRepository authenticateRepo;

	@Autowired(required = true)
	private UserDataRepositoryInterface userDataRepo;

	@Autowired
	private PasswordAlgoRepositoryIntefrace passwordAlgoRepo;

	@Autowired
	private UserRepositoryInterface userRepo;

	@Autowired
	private UserManagementRepository userManagementRepo;

	@Autowired
	private RuntimeLogInfoHelper runtimeLogInfoHelper;

	private LogManager Log = LogManagerFactory.getInstance(AppLoggerConstant.LOGGER_ID);

	@Override
	public HttpEntity<ResponseBean> generatePassword(String findKey) throws Exception {

		ResponseBean responseBean = new ResponseBean();
		try {
			boolean success = true;
			String message = "";
			UserAuthentication entity = authenticateRepo.findById(findKey);

			String password = createPassword();
			String userEncodedPwd = encodePassword(password);
			userDataRepo.save(entity, userEncodedPwd);

			String emailBodyLoginId = "Your login id is : " + entity.getLoginId();
			boolean emailStatusLoginId = email.sendMail("This email contains confidential information of user ", emailBodyLoginId, entity.getLoginId());

			String emailBody = "Your password is : " + password;
			boolean emailStatus = email.sendPassword(emailBody, entity.getLoginId());
			if (emailStatus && emailStatusLoginId) {
				message = "Login Credentials sent to user's email id";

			} else {
				message = "Due to some cause email sending failed, <br>Please check network connectivity while creating user";
			}

			AppAlarm appAlarm = Log.getAlarm("ABSUM323951200");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", success);
			responseBean.add("message", message);
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "generatePassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

		} catch (Exception e) {
			AppAlarm appAlarm = Log.getAlarm("ABSUM343951400");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", false);

			responseBean.add("message", "Password generation failed " + e.getMessage());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "generatePassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

		}
	}

	@Override
	public HttpEntity<ResponseBean> generateAndSavePassword(String findKey) throws Exception {

		ResponseBean responseBean = new ResponseBean();
		String password = null;
		try {
			boolean success = true;
			String message = "";
			UserAuthentication entity = authenticateRepo.findById(findKey);

			password = createPassword();
			String userEncodedPwd = encodePassword(password);
			userDataRepo.save(entity, userEncodedPwd);

			AppAlarm appAlarm = Log.getAlarm("ABSUM323951200");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", success);
			responseBean.add("message", message);
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "generateAndSavePassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

		} catch (Exception e) {
			AppAlarm appAlarm = Log.getAlarm("ABSUM343951400");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", false);

			responseBean.add("message", "Password generation failed " + e.getMessage());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "generateAndSavePassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

		}
	}

	@Override
	public HttpEntity<ResponseBean> updateUser(String users) throws Exception {

		ResponseBean responseBean = new ResponseBean();
		try {
			List<UserInterface> lstUser = new ArrayList<UserInterface>();

			JsonArray userJsonArray = (JsonArray) new JsonParser().parse(users);
			for (int i = 0; i < userJsonArray.size(); i++) {
				JsonObject userJson = userJsonArray.get(i).getAsJsonObject();
				String userId = userJson.get("userId").getAsString();
				UserInterface user = userRepo.getByUserId(userId);

				if (userJson.has("isLocked")) {
					int isLocked = userJson.get("isLocked").getAsBoolean() == true ? 1 : 0;
					user.setIsLocked(isLocked);
				}
				if (userJson.has("changePasswordNextLogin")) {
					int changePasswordNextLogin = userJson.get("changePasswordNextLogin").getAsBoolean() == true ? 1 : 0;
					user.setChangePasswordNextLogin(changePasswordNextLogin);
				}
				if (userJson.has("genTempOneTimePassword")) {
					int genTempOneTimePassword = userJson.get("genTempOneTimePassword").getAsBoolean() == true ? 1 : 0;
					user.setGenTempOneTimePassword(genTempOneTimePassword);
				}
				if (userJson.has("sessionTimeout")) {
					user.setSessionTimeout(userJson.get("sessionTimeout").getAsInt());
				}
				lstUser.add(user);
			}
			userRepo.update(lstUser);

			AppAlarm appAlarm = Log.getAlarm("ABSUM323921200");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", true);

			responseBean.add("message", "Users updated successfully");
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "updateUser");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

		} catch (Exception e) {
			AppAlarm appAlarm = Log.getAlarm("ABSUM343921400");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", false);

			responseBean.add("message", "User updation failed" + e.getMessage());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "updateUser");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

		}
	}

	@Override
	public HttpEntity<ResponseBean> changePassword(String data, String userId) throws Exception {
		ResponseBean responseBean = new ResponseBean();
		try {
			AppAlarm appAlarm = null;
			boolean success = true;
			String message = "";
			JsonObject passwordJson = (JsonObject) new JsonParser().parse(data);
			String oldPassword = passwordJson.get("oldPassword").getAsString();
			String newPassword = passwordJson.get("newPassword").getAsString();

			String oldEncodedPassword = encodePassword(oldPassword);

			UserDataInterface userData = userDataRepo.findByUserId(userId);
			if (oldEncodedPassword.equals(userData.getPassword())) {

				PasswordPolicyInterface passwordPolicy = passwordPolicyRepository.findAll().get(0);
				PasswordGenerator passwordGenerator = new PasswordGenerator(passwordPolicy.getMinPwdLength(), passwordPolicy.getMaxPwdLength());
				if (passwordPolicy.getAllowedSpecialLetters() != null && passwordPolicy.getAllowedSpecialLetters().length() > 0) {
					char[] charArray = passwordPolicy.getAllowedSpecialLetters().toCharArray();
					Character[] speChars = ArrayUtils.toObject(charArray);
					passwordGenerator.setSPECIAL_LETTERS(speChars);
				}
				PasswordLogic[] pwdLogic = new PasswordLogic[] { new PasswordLogic('C', passwordPolicy.getMinCapitalLetters()),
						new PasswordLogic('S', passwordPolicy.getMinSmallLetters()), new PasswordLogic('N', passwordPolicy.getMinNumericValues()),
						new PasswordLogic('P', passwordPolicy.getMinSpecialLetters()) };

				if (!passwordGenerator.validatePwdData(pwdLogic)) {
					throw new Exception("Incorrect Password Policy Configuration Data Found.");
				}

				String result = passwordGenerator.validatePassword(pwdLogic, newPassword);
				if (result == null) {
					String newEncodedPassword = encodePassword(newPassword);
					userData.setPassword(newEncodedPassword);

					String last5Passwords = userData.getLast5Passwords();

					JsonArray last5PasswordsJsonArray;
					if (last5Passwords == null) {
						last5PasswordsJsonArray = new JsonArray();
						JsonObject oldPasswordJSON = new JsonObject();
						oldPasswordJSON.addProperty("password", oldEncodedPassword);
						last5PasswordsJsonArray.add(oldPasswordJSON);
					} else {
						last5PasswordsJsonArray = (JsonArray) new JsonParser().parse(last5Passwords);

						boolean newPasswordMatchedInLast5 = false;
						for (int i = 0; i < last5PasswordsJsonArray.size(); i++) {
							JsonObject lastPassword = last5PasswordsJsonArray.get(i).getAsJsonObject();
							if (lastPassword.get("password").getAsString().equals(newEncodedPassword)) {
								newPasswordMatchedInLast5 = true;
								break;
							}
						}
						if (newPasswordMatchedInLast5) {
							success = false;
							message = "New password mustn't present in your last 5 passwords";
							appAlarm = Log.getAlarm("ABSUM343950500");
						} else {
							if (last5PasswordsJsonArray.size() == 5) {
								last5PasswordsJsonArray.remove(0);
							}
							JsonObject oldPasswordJSON = new JsonObject();
							oldPasswordJSON.addProperty("password", oldEncodedPassword);
							last5PasswordsJsonArray.add(oldPasswordJSON);
						}
					}
					if (success) {
						userData.setLast5Passwords(last5PasswordsJsonArray.toString());
						userDataRepo.update(userData);
						message = "Password updated successfully";
						/* Updating status of changePasswordInNextLogin */
						if (passwordJson.has("changePasswordInNextLogin")) {
							boolean isChangePasswordInNextLogin = passwordJson.get("changePasswordInNextLogin").getAsBoolean();
							if (isChangePasswordInNextLogin) {
								List<UserInterface> lstUser = new ArrayList<UserInterface>();
								UserInterface user = userRepo.getByUserId(userId);
								user.setChangePasswordNextLogin(0);
								lstUser.add(user);
								userRepo.update(lstUser);
								appAlarm = Log.getAlarm("ABSUM323950200");
							}
						}
					}
				} else {
					success = false;
					message = result;
					appAlarm = Log.getAlarm("ABSUM343950500");
				}
			} else {
				success = false;
				message = "Please enter correct old password";
				appAlarm = Log.getAlarm("ABSUM343950500");
			}

			responseBean.addAlarm(appAlarm);
			responseBean.add("success", success);

			responseBean.add("message", message);
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "changePassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
		} catch (Exception e) {
			AppAlarm appAlarm = Log.getAlarm("ABSUM343950400");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", false);

			responseBean.add("message", "User updation failed" + e.getMessage());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "changePassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
		}

	}

	@Override
	public HttpEntity<ResponseBean> resetPassword(String data) throws Exception {
		ResponseBean responseBean = new ResponseBean();
		try {
			boolean success = true;
			String message = "";
			List<UserInterface> lstUser = new ArrayList<UserInterface>();
			JsonArray userJsonArray = (JsonArray) new JsonParser().parse(data);
			for (int i = 0; i < userJsonArray.size(); i++) {
				JsonObject userJson = userJsonArray.get(i).getAsJsonObject();
				String userId = userJson.get("userId").getAsString();
				String loginId = userJson.get("loginId").getAsString();

				String password = createPassword();
				String userEncodedPwd = encodePassword(password);

				UserInterface user = userRepo.getByUserId(userId);
				user.setChangePasswordNextLogin(1);

				user.getUserData().setPassword(userEncodedPwd);
				lstUser.add(user);

				String emailBody = "Your new password is : " + password;
				boolean emailStatus = email.sendPassword(emailBody, loginId);
				if (emailStatus) {
					message = "Password reset successfully and sent to your email address";
					// sprinkler.logger.log(runtimeLogInfoHelper.getRuntimeLogInfo(),
					// 4010,this.getClass().getName(),"generatePassword",loginId);

				} else {
					message = "Password reset successfully, due to some cause email sending failed";
				}
			}
			userRepo.update(lstUser);
			AppAlarm appAlarm = Log.getAlarm("ABSUM323951200");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", success);

			responseBean.add("message", message);
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "resetPassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

		} catch (Exception e) {

			AppAlarm appAlarm = Log.getAlarm("ABSUM343951400");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", false);

			responseBean.add("message", "Password reset failed" + e.getMessage());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "resetPassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

		}
	}

	@Override
	public HttpEntity<ResponseBean> resetAndUpdatePassword(String data) throws Exception {
		ResponseBean responseBean = new ResponseBean();
		String password = null;
		try {
			boolean success = true;
			String message = "";
			List<UserInterface> lstUser = new ArrayList<UserInterface>();
			JsonArray userJsonArray = (JsonArray) new JsonParser().parse(data);
			for (int i = 0; i < userJsonArray.size(); i++) {
				JsonObject userJson = userJsonArray.get(i).getAsJsonObject();
				String userId = userJson.get("userId").getAsString();
				String loginId = userJson.get("loginId").getAsString();

				password = createPassword();
				String userEncodedPwd = encodePassword(password);

				UserInterface user = userRepo.getByUserId(userId);
				user.setChangePasswordNextLogin(1);

				user.getUserData().setPassword(userEncodedPwd);
				lstUser.add(user);

			}
			userRepo.update(lstUser);

			AppAlarm appAlarm = Log.getAlarm("ABSUM323951200");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", success);

			responseBean.add("message", message);
			responseBean.add("password", password);

			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "resetAndUpdatePassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
		} catch (Exception e) {
			AppAlarm appAlarm = Log.getAlarm("ABSUM343951400");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", false);

			responseBean.add("message", "Password reset failed" + e.getMessage());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "resetAndUpdatePassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
		}
	}

	@Override
	public HttpEntity<ResponseBean> forgetPassword(String data) throws Exception {

		ResponseBean responseBean = new ResponseBean();
		try {
			boolean success = true;
			String message = "";
			AppAlarm appAlarm = null;
			JsonObject passwordJson = (JsonObject) new JsonParser().parse(data);
			String loginId = passwordJson.get("loginId").getAsString();
			String passRecoveryId = passwordJson.get("passRecoveryId").getAsString();
			String answer = passwordJson.get("answer").getAsString();

			UserAuthentication entity = authenticateRepo.getUserByLoginId(loginId);
			if (entity != null) {
				success = false;
				message = "Incorrect Username";
				appAlarm = Log.getAlarm("ABSUM343962500");

			} else {
				boolean isValid = userManagementRepo.isCorrectAnswer(passRecoveryId, answer);
				if (isValid) {
					String password = createPassword();
					String encodedPassword = encodePassword(password);

					UserDataInterface userData = userDataRepo.findByUserId(entity.getUserId());
					userData.setPassword(encodedPassword);
					userDataRepo.update(userData);

					String emailBody = "Your new password is : " + password;
					boolean emailStatus = email.sendPassword(emailBody, loginId);
					if (emailStatus) {
						message = "Password successfully sent to your registered email address";
					} else {
						message = "Due to some cause email sending failed";
					}
					appAlarm = Log.getAlarm("ABSUM323963200");

				} else {
					success = false;
					message = "Incorrect Answer";
					appAlarm = Log.getAlarm("ABSUM343962500");

				}
			}

			responseBean.addAlarm(appAlarm);
			responseBean.add("success", success);

			responseBean.add("message", message);
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "forgetPassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
		} catch (Exception e) {
			AppAlarm appAlarm = Log.getAlarm("ABSUM343963400");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", false);

			responseBean.add("message", "Forget password process failed" + e.getMessage());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "forgetPassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
		}
	}

	@Override
	public HttpEntity<ResponseBean> findSecurityQuestions(String loginId) throws Exception {

		ResponseBean responseBean = new ResponseBean();
		try {
			AppAlarm appAlarm = null;
			boolean success = true;
			String message = "";
			UserAuthentication entity = authenticateRepo.getUserByLoginId(loginId);
			if (entity != null) {
				success = false;
				message = "Incorrect Username";
				appAlarm = Log.getAlarm("ABSUM343952500");
			} else {
				// get the security questions
				List<PassRecoveryInterface> passRecovery = userManagementRepo.findByUserId(entity.getUserId());

				JsonArray passwordRecoveryJSONArray = new JsonArray();
				for (Iterator<PassRecoveryInterface> passRecoveryIterator = passRecovery.iterator(); passRecoveryIterator.hasNext();) {
					PassRecoveryInterface passRecoveryInterface = (PassRecoveryInterface) passRecoveryIterator.next();
					Object question = userManagementRepo.findQuestionById(passRecoveryInterface.getQuestionId());

					JsonObject passwordRecoveryJSON = new JsonObject();
					passwordRecoveryJSON.addProperty("passRecoveryId", passRecoveryInterface.getPassRecoveryId());
					passwordRecoveryJSON.addProperty("question", question.toString());
					passwordRecoveryJSONArray.add(passwordRecoveryJSON);
				}
				if (passwordRecoveryJSONArray.size() == 0) {
					success = false;
					message = "You didn't set password recovery questions and answers";
					appAlarm = Log.getAlarm("ABSUM343952500");

				} else {
					responseBean.add("data", passwordRecoveryJSONArray.toString());
					message = "Securiy questions fetched successfully";
					appAlarm = Log.getAlarm("ABSUM323952200");

				}

			}
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", success);

			responseBean.add("message", message);
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "findSecurityQuestions");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

		} catch (Exception e) {

			AppAlarm appAlarm = Log.getAlarm("ABSUM343952400");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", false);
			responseBean.add("message", "Password generation failed" + e.getMessage());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "findSecurityQuestions");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
		}
	}

	@Override
	public HttpEntity<ResponseBean> findLoggedInUser(String loggedInUserId) {
		ResponseBean responseBean = new ResponseBean();
		org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
		try {
			UserAuthentication user = authenticateRepo.findByUserId(loggedInUserId);

			responseBean.add("success", true);
			responseBean.add("message", "LoggedIn user found successfully");
			responseBean.add("data", user);
			httpStatus = org.springframework.http.HttpStatus.OK;
		} catch (Exception e) {
			responseBean.add("success", false);
			responseBean.add("message", "User finding failed" + e.getMessage());
			httpStatus = org.springframework.http.HttpStatus.BAD_REQUEST;
		}
		return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);
	}

	@Override
	public HttpEntity<ResponseBean> checkValidityOfLoginId(String loginId) throws Exception {

		ResponseBean responseBean = new ResponseBean();
		org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
		try {
			boolean success = true;
			String message = "";
			UserAuthentication entity = authenticateRepo.getUserByLoginId(loginId);

			if (entity == null) {
				message = "unique email id found";
			} else {
				success = false;
				message = "User present with this email id<br> Email Id should be unique!";
			}
			responseBean.add("success", success);
			responseBean.add("message", message);
			httpStatus = org.springframework.http.HttpStatus.OK;
		} catch (Exception e) {
			responseBean.add("success", false);
			responseBean.add("message", "Password generation failed" + e.getMessage());
			httpStatus = org.springframework.http.HttpStatus.BAD_REQUEST;
		}
		return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, httpStatus);
	}

	private String createPassword() throws Exception {

		PasswordPolicyInterface passwordPolicy = passwordPolicyRepository.findAll().get(0);

		PasswordGenerator passwordGenerator = new PasswordGenerator(passwordPolicy.getMinPwdLength(), passwordPolicy.getMaxPwdLength());
		if (passwordPolicy.getAllowedSpecialLetters() != null && passwordPolicy.getAllowedSpecialLetters().length() > 0) {
			char[] charArray = passwordPolicy.getAllowedSpecialLetters().toCharArray();
			Character[] speChars = ArrayUtils.toObject(charArray);
			passwordGenerator.setSPECIAL_LETTERS(speChars);
		}

		PasswordLogic[] pwdLogic = new PasswordLogic[] { new PasswordLogic('C', passwordPolicy.getMinCapitalLetters()),
				new PasswordLogic('S', passwordPolicy.getMinSmallLetters()), new PasswordLogic('N', passwordPolicy.getMinNumericValues()),
				new PasswordLogic('P', passwordPolicy.getMinSpecialLetters()) };

		if (!passwordGenerator.validatePwdData(pwdLogic)) {
			throw new Exception("Incorrect Password Policy Configuration Data Found.");
		}

		String password = passwordGenerator.generate(pwdLogic);
		return password;
	}

	private String encodePassword(String password) throws Exception {
		PasswordAlgoInterface passwordAlgo = passwordAlgoRepo.findAll().get(0);
		String userEncodedPwd = HashAlgorithms.getInstance().computeHash(password, passwordAlgo.getAlgorithm());
		return userEncodedPwd;
	}

	@Override
	public HttpEntity<ResponseBean> getNewPassword() throws Exception {
		ResponseBean responseBean = new ResponseBean();
		try {
			String password = createPassword();
			AppAlarm appAlarm = Log.getAlarm("ABSUM323951200");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", true);
			responseBean.add("message", "Password Generated Successfully");
			responseBean.add("data", password);
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "getNewPassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));

		} catch (Exception e) {
			AppAlarm appAlarm = Log.getAlarm("ABSUM343951400");
			responseBean.addAlarm(appAlarm);
			responseBean.add("success", false);
			responseBean.add("message", "Password generation failed" + e.getMessage());
			Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "PasswordGeneratorBizService", "getNewPassword");
			return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
		}

	}
}

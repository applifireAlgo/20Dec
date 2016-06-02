package com.app.server.businessservice.appbasicsetup.usermanagement;
import org.springframework.http.HttpEntity;

import com.athena.server.pluggable.utils.bean.ResponseBean;


public interface PasswordGeneratorBizService {

	public HttpEntity<ResponseBean> generatePassword(String findKey) throws Exception;

	public HttpEntity<ResponseBean> updateUser(String userJson) throws Exception;

	public HttpEntity<ResponseBean> changePassword(String data, String userId) throws Exception;

	public HttpEntity<ResponseBean> forgetPassword(String data) throws Exception;

	public HttpEntity<ResponseBean> resetPassword(String data) throws Exception;

	public HttpEntity<ResponseBean> findSecurityQuestions(String findKey) throws Exception;

	public HttpEntity<ResponseBean> findLoggedInUser(String loggedInUserId);

	public HttpEntity<ResponseBean> checkValidityOfLoginId(String findKey) throws Exception;

	public HttpEntity<ResponseBean> getNewPassword() throws Exception;

	public HttpEntity<ResponseBean> generateAndSavePassword(String findKey) throws Exception;

	public HttpEntity<ResponseBean> resetAndUpdatePassword(String findKey) throws Exception;

}

package com.app.server.repository.appbasicsetup.usermanagement;
import java.util.List;

import com.spartan.server.password.interfaces.PassRecoveryInterface;



public interface UserManagementRepository {

	public List<PassRecoveryInterface> findByUserId(String userId) throws Exception;

	public Object findQuestionById(String questionId);

	public boolean isCorrectAnswer(String passRecoveryId, String answer) throws Exception;

}

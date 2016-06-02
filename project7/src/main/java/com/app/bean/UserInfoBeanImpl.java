package com.app.bean;
import java.util.HashMap;

import com.spartan.pluggable.auth.UserBean;

public class UserInfoBeanImpl implements UserBean {
	private HashMap<String, Object> properties;
	private String loginID;
	private boolean isAthenticated;
	private boolean isLocked;
	

	public UserInfoBeanImpl() {
		super();
	}

	@Override
	public int errorCode() {
		return 0;
	}

	@Override
	public String errorMessage() {
		return null;
	}

	@Override
	public String getLoginID() {
		return loginID;
	}

	@Override
	public HashMap<String, Object> getProperties() {
		return properties;
	}

	@Override
	public boolean isAthenticated() {
		return isAthenticated;
	}

	@Override
	public boolean isLocked() {
		return isLocked;
	}

	public void setProperties(HashMap<String, Object> properties) {
		this.properties = properties;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public void setAthenticated(boolean isAthenticated) {
		this.isAthenticated = isAthenticated;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	

}

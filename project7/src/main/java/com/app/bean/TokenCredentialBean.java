package com.app.bean;
import com.spartan.pluggable.auth.TokenCredential;

public class TokenCredentialBean implements TokenCredential {
	private String password;
	private String appSessionId;

	public TokenCredentialBean() {
		super();
	}

	public TokenCredentialBean(String password, String appSessionId) {
		super();
		this.password = password;
		this.appSessionId = appSessionId;
	}

	@Override
	public String getTokenCredentials() {
		return password;
	}

	@Override
	public String getAppToken() {
		return appSessionId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAppSessionId(String appSessionId) {
		this.appSessionId = appSessionId;
	}
	

}

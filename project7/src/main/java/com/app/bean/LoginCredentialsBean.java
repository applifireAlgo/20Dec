package com.app.bean;
import com.spartan.pluggable.auth.LoginCredentials;

public class LoginCredentialsBean implements LoginCredentials {

	private String loginId;
	private String password;
	private String latitude;
	private String longitude;
	
	public LoginCredentialsBean(String loginId, String password, String latitude, String longitude) {
		super();
		this.loginId = loginId;
		this.password = password;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public LoginCredentialsBean(){
		
	}

	@Override
	public String getLoginId() {
		return loginId;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getLatitude() {
		return latitude;
	}

	@Override
	public String getLongitude() {
		return longitude;
	}
	
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		if (getLoginId() == null) {
			return 0;
		}
		return getLoginId().hashCode();
	}

}

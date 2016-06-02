package com.app.bean;
public class PwdPolicy {
	int pwdType;
	Integer minLength;
	Integer maxLength;
	Integer minCapLetters;
	Integer minSmlLetters;
	Integer minSplLetters;
	String allowedSplLetters;
	Integer minNumLeters;
	int encryptionType;
	int pwdExpireDays;
	int pwdReused;

	public PwdPolicy() {
		encryptionType = 0;// No encryption
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMinCapLetters() {
		return minCapLetters;
	}

	public void setMinCapLetters(Integer minCapLetters) {
		this.minCapLetters = minCapLetters;
	}

	public Integer getMinSmlLetters() {
		return minSmlLetters;
	}

	public void setMinSmlLetters(Integer minSmlLetters) {
		this.minSmlLetters = minSmlLetters;
	}

	public Integer getMinSplLetters() {
		return minSplLetters;
	}

	public void setMinSplLetters(Integer minSplLetters) {
		this.minSplLetters = minSplLetters;
	}

	public String getAllowedSplLetters() {
		return allowedSplLetters;
	}

	public void setAllowedSplLetters(String allowedSplLetters) {
		this.allowedSplLetters = allowedSplLetters;
	}

	public Integer getMinNumLeters() {
		return minNumLeters;
	}

	public void setMinNumLeters(Integer minNumLeters) {
		this.minNumLeters = minNumLeters;
	}

	public int getPwdType() {
		return pwdType;
	}

	public void setPwdType(int pwdType) {
		this.pwdType = pwdType;
	}

	public int getEncryptionType() {
		return encryptionType;
	}

	public void setEncryptionType(int encryptionType) {
		this.encryptionType = encryptionType;
	}

	public int getPwdExpireDays() {
		return pwdExpireDays;
	}

	public void setPwdExpireDays(int pwdExpireDays) {
		this.pwdExpireDays = pwdExpireDays;
	}

	public int getPwdReused() {
		return pwdReused;
	}

	public void setPwdReused(int pwdReused) {
		this.pwdReused = pwdReused;
	}
	
	
}

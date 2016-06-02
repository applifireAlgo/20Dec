package com.app.shared.appinsight.alarms;
public class LogException {

	private String rooException;
	
	private String exceptionValue;

	private Integer exceptionId;
	
	private String id;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRooException() {
		return rooException;
	}

	public void setRooException(String rooException) {
		this.rooException = rooException;
	}

	public String getExceptionValue() {
		return exceptionValue;
	}

	public void setExceptionValue(String exceptionValue) {
		this.exceptionValue = exceptionValue;
	}

	public Integer getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
	}

	@Override
	public String toString() {
		return "LogException [rooException=" + rooException + ", exceptionValue=" + exceptionValue + ", exceptionId=" + exceptionId + ", id=" + id + "]";
	}


}

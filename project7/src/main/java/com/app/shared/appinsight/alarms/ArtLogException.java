package com.app.shared.appinsight.alarms;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
//, CONSTRAINT fk_exception FOREIGN KEY (exception) REFERENCES art_log_exception_m (exceptionId)
@Entity
@Table(name = "art_log_exception_m")
@Cache(alwaysRefresh= true)
//@JsonIdentityInfo(generator = PropertyGenerator.class, property = "exceptionId")
public class ArtLogException implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exceptionId")
	private Integer exceptionId;

	@Column(name = "exception")
	private String exception;
	
	@Column(name = "exceptionName")
	private String exceptionName;
	
	@Column(name = "rootException")
	private String rootException;
	
	/*@ManyToOne
	@JoinColumn(name = "exceptionId", referencedColumnName = "id",insertable=false,updatable=false)
	private ArtLogEvents artLogEvents;*/

	public ArtLogException() {
		super();
	}
	
	public ArtLogException(Integer exceptionId, String exception,String exceptionName, String rootException) {
		super();
		this.exceptionId = exceptionId;
		this.exception = exception;
		this.exceptionName = exceptionName;
		this.rootException = rootException;
	}

	public Integer getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getRootException() {
		return rootException;
	}

	public void setRootException(String rootException) {
		this.rootException = rootException;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	@Override
	public String toString() {
		return "ArtLogException [exceptionId=" + exceptionId + ", exception=" + exception + ", exceptionName=" + exceptionName + ", rootException=" + rootException + "]";
	}

	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"exceptionId\":" + "\"" + getExceptionId() + "\"");
		sb.append(",\"exceptionName\":" + "\"" + getExceptionName() + "\"");
		sb.append(",\"exception\":" + "\"" + getException() + "\"");
		sb.append(",\"rootException\":" + "\"" + getRootException() + "\"");
		sb.append("}");
		return sb.toString();
	}
	
}

package com.app.shared.appinsight.alarms;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;

@Entity
@Table(name = "art_log_severity_m")
@Cache(alwaysRefresh= true)
//@JsonIdentityInfo(generator = PropertyGenerator.class, property = "severityId")
public class ArtLogSeverity implements Serializable {
	
	/*@Id()
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator="UUIDGenerator")
	@Basic(optional = false)
	@Column(name = "id")
	private String id;*/

	@Column(name = "severity")
	private String severity;

	@Id
	@Column(name = "severityId")
	private Integer severityId;
	
	public ArtLogSeverity() {
		super();
	}

	public ArtLogSeverity(Integer severityId, String severity) {
		this.severityId = severityId;
		this.severity = severity;
	}

	public Integer getSeverityId() {
		return severityId;
	}

	public void setSeverityId(Integer severityId) {
		this.severityId = severityId;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return "ArtLogSeverity [severityId=" + severityId + ", severity=" + severity + "]";
	}

	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"attributeName\":" + "\"" + getSeverityId() + "\"");
		sb.append(",\"attributeValue\":" + "\"" + getSeverity() + "\"");
		sb.append("}");
		return sb.toString();
	}
	
	public String toSeverityDetails(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"severityId\":" + "\"" + getSeverityId() + "\"");
		sb.append(",\"severity\":" + "\"" + getSeverity() + "\"");
		sb.append("}");
		return sb.toString();
	
	}

}

package com.app.shared.appinsight.alarms;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;

@Entity
@Table(name = "art_log_config_m")
@Cache(alwaysRefresh = true)
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "logConfigId")
public class ArtLogConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUIDGenerator")
	@Basic(optional = false)
	@Column(name = "logConfigId")
	private String logConfigId;
	
	@Column(name = "configData")
	private String configData;

	public ArtLogConfig() {
		super();
	}

	public ArtLogConfig(Long createdBy, Date createdDate, Long updatedBy, Date updatedDate, Long versionId, boolean activeStatus, String appCreatorId, String projectId,
			String projectVersionId) {
		super();
	}

/*	public ArtLogConfig(String logConfigId, List<ArtLoggingProperties> loggingProperties, List<ArtLogConnector> artLogConnectors, List<ArtLogSeverity> artLogSeverity) {
		this.logConfigId = logConfigId;
		this.loggingProperties = loggingProperties;
		this.artLogConnectors = artLogConnectors;
		this.artLogSeverity = artLogSeverity;
	}*/

	public ArtLogConfig(String logConfigId, String configData) {
		super();
		this.logConfigId = logConfigId;
		this.configData = configData;
	}

	public String getLogConfigId() {
		return logConfigId;
	}

	public void setLogConfigId(String logConfigId) {
		this.logConfigId = logConfigId;
	}
	
	public String getConfigData() {
		return configData;
	}

	public void setConfigData(String configData) {
		this.configData = configData;
	}

	@Override
	public String toString() {
		return "ArtLogConfig [logConfigId=" + logConfigId + ", configData=" + configData + "]";
	}

	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"appConfigId\":" + "\"" + getLogConfigId() + "\"");
		sb.append(",\"configData\":" + "\"" + getConfigData() + "\"");
		sb.append("}");
		return sb.toString();
	}

}

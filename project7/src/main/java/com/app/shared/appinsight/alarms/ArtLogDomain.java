package com.app.shared.appinsight.alarms;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//import com.app.shared.SystemInfo;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.athena.framework.server.helper.EntityValidatorHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
/**
 * @author Shubhangi Mane
 * */
@SuppressWarnings("serial")
@Table(name = "art_log_domain")
@Entity

@JsonIdentityInfo(generator = PropertyGenerator.class, property = "domainPkId")
/**
 * @author Shubhangi Mane
 * */
public class ArtLogDomain implements Serializable {
	@Column(name = "domain_name")
	@JsonProperty("domainName")
	@NotNull
	@Size(min = 1, max = 256)
	private String domainName;

	@Column(name = "domain_id")
	@JsonProperty("domainId")
	@NotNull
	@Size(min = 1, max = 256)
	private String domainId;

	@Column(name = "trace")
	@JsonProperty("trace")
	@NotNull
	private Boolean trace;

	@Column(name = "domain_criticality")
	@JsonProperty("domainCriticality")
	@NotNull
	private byte domainCriticality;

	@Column(name = "connector_log_priority")
	@JsonProperty("connectorLogPriority")
	@NotNull
	@Size(min = 1, max = 256)
	private String connectorLogPriority;

	@Transient
	private String primaryKey;

	@Id
	@Column(name = "domainPkId")
	@JsonProperty("domainPkId")
	@GeneratedValue(generator = "UUIDGenerator")
	private String domainPkId;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="domainPkId", columnDefinition="domainPkId", referencedColumnName = "domainPkId")
	private List<ArtLogAlarm> artLogAlarm;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "boundedContextId",referencedColumnName="boundedContextId")
	private ArtLogBoundedContext artLogBoundedContext;
	
	public ArtLogDomain(String domainName, String domainId, Boolean trace, byte domainCriticality, String connectorLogPriority, List<ArtLogAlarm> artLogAlarm) {
		super();
		this.domainName = domainName;
		this.domainId = domainId;
		this.trace = trace;
		this.domainCriticality = domainCriticality;
		this.connectorLogPriority = connectorLogPriority;
		this.artLogAlarm = artLogAlarm;
	}

	public ArtLogDomain() {
		super();
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String _domainName) {
		if (_domainName != null) {
			this.domainName = _domainName;
		}
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String _dominId) {
		if (_dominId != null) {
			this.domainId = _dominId;
		}
	}

	public Boolean gettrace() {
		return trace;
	}

	public void settrace(Boolean _trace) {
		if (_trace != null) {
			this.trace = _trace;
		}
	}

	public String getDomainPkId() {
		return domainPkId;
	}

	public void setDomainPkId(String domainPkId) {
		this.domainPkId = domainPkId;
	}

	public ArtLogBoundedContext getArtLogBoundedContext() {
		return artLogBoundedContext;
	}

	public void setArtLogBoundedContext(ArtLogBoundedContext artLogBoundedContext) {
		this.artLogBoundedContext = artLogBoundedContext;
	}

	public String getPrimaryKey() {
		return domainPkId;
	}

	public void setPrimaryKey(String _primaryKey) {
		this.primaryKey = _primaryKey;
	}

	public String _getPrimarykey() {
		return domainPkId;
	}

	public String getdomainPkId() {
		return domainPkId;
	}

	public void setdomainPkId(String _domainPkId) {
		this.domainPkId = _domainPkId;
	}

	public ArtLogDomain removeArtLogAlarm(ArtLogAlarm _artLogAlarm) {
		if (this.artLogAlarm != null) {
			this.artLogAlarm.remove(_artLogAlarm);
		}
		return this;
	}

	public Integer getTotalNumberOfArtLogAlarm() {
		if (this.artLogAlarm != null) {
			return this.artLogAlarm.size();
		}
		return 0;
	}

	public List<ArtLogAlarm> getArtLogAlarm() {
		return artLogAlarm;
	}

	public Boolean getTrace() {
		return trace;
	}

	public void setTrace(Boolean trace) {
		this.trace = trace;
	}

	public String getConnectorLogPriority() {
		return connectorLogPriority;
	}

	public void setConnectorLogPriority(String connectorLogPriority) {
		this.connectorLogPriority = connectorLogPriority;
	}

	public void setArtLogAlarm(List<ArtLogAlarm> artLogAlarm) {
		this.artLogAlarm = artLogAlarm;
	}
	
	/*public String toJson() throws JSONException
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("domainName",domainName);
		jsonObject.put("domainCriticality",domainCriticality);
		jsonObject.put("domainId",domainId);
		jsonObject.put("trace",trace);
		jsonObject.put("domainName",domainName);
		jsonObject.put("artLogAlarm",artLogAlarm);
		return jsonObject.toString();
		
	}*/

	public byte getDomainCriticality() {
		return domainCriticality;
	}

	public void setDomainCriticality(byte domainCriticality) {
		this.domainCriticality = domainCriticality;
	}
}

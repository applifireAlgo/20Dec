package com.app.shared.appinsight.alarms;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author Shubhangi Mane
 * */
@Entity
@Table(name = "art_log_bounded_context")
public class ArtLogBoundedContext{

	@Transient
	private String primaryKey;

	@Id
	@Column(name = "boundedContextId")
	@JsonProperty("boundedContextId")
	@GeneratedValue(generator = "UUIDGenerator")
	private String boundedContextId;

	@Column(name = "bounded_context_json")
	private String boundedContextJson;
	
	@Column(name = "bounded_context_prefix")
	private String boundedContextPrefix;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="boundedContextId", columnDefinition="boundedContextId", referencedColumnName = "boundedContextId")
//	@Column(name="boundedContextId")
	private List<ArtLogDomain> artLogDomain;

	public ArtLogBoundedContext(String boundedContextJson, List<ArtLogDomain> artLogDomain) {
		super();
		this.boundedContextJson = boundedContextJson;
		this.artLogDomain = artLogDomain;
	}

	public ArtLogBoundedContext() {
		super();
	}

	public String getBoundedContextJson() {
		return boundedContextJson;
	}

	public void setBoundedContextJson(String boundedContextJson) {
		this.boundedContextJson = boundedContextJson;
	}

	public List<ArtLogDomain> getArtLogDomain() {
		return artLogDomain;
	}

	public void setArtLogDomain(List<ArtLogDomain> artLogDomain) {
		this.artLogDomain = artLogDomain;
	}

	public String getBoundedContextPrefix() {
		return boundedContextPrefix;
	}

	public void setBoundedContextPrefix(String boundedContextPrefix) {
		this.boundedContextPrefix = boundedContextPrefix;
	}

	public String getBoundedContextId() {
		return boundedContextId;
	}

	public void setBoundedContextId(String boundedContextId) {
		this.boundedContextId = boundedContextId;
	}
	
	public String toJSON()
	{
		JSONObject jsonObj=new JSONObject();
		try {
			jsonObj.put("boundedContextId",boundedContextId);
			jsonObj.put("boundedContextJson",boundedContextJson);
			jsonObj.put("boundedContextPrefix",boundedContextPrefix);
			jsonObj.put("artLogDomain",artLogDomain);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return jsonObj.toString();
	}
	

}

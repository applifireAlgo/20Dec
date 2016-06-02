package com.app.shared.appinsight.alarms;
import java.io.Serializable;

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
@Table(name = "art_log_architecture_layer_m")
@Cache(alwaysRefresh= true)
//@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public class ArtLogArchitectureLayer implements Serializable {
	
	@Id
	@Column(name = "layerId")
	private Integer layerId;

	@Column(name = "layer")
	private String layer;
	
	public ArtLogArchitectureLayer() {
		super();
	}
	
	public ArtLogArchitectureLayer(Integer layerId, String layer) {
		super();
		this.layerId = layerId;
		this.layer = layer;
	}

	public Integer getLayerId() {
		return layerId;
	}

	public void setLayerId(Integer layerId) {
		this.layerId = layerId;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	@Override
	public String toString() {
//		return "ArtLogArchitectureLayer [id=" + id + ", layerId=" + layerId + ", layer=" + layer + /*", logConfigId=" + logConfigId +*/ "]";
		return "ArtLogArchitectureLayer [layerId=" + layerId + ", layer=" + layer + "]";
	}

	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"layerId\":" + "\"" + getLayerId() + "\"");
		sb.append(",\"layer\":" + "\"" + getLayer() + "\"");
		sb.append("}");
		return sb.toString();
	}
}

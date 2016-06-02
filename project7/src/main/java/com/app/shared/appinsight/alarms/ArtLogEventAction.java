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
@Table(name = "art_log_event_action_m")
@Cache(alwaysRefresh= true)
//@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public class ArtLogEventAction implements Serializable {

	/*@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUIDGenerator")
	private String id;*/
	
	@Id
	@Column(name = "eventActionId")
	private Integer eventActionId;

	@Column(name = "eventAction")
	private String eventAction;
	
	public ArtLogEventAction() {
		super();
	}
	
	public ArtLogEventAction(Integer eventActionId, String eventAction) {
		super();
		this.eventActionId = eventActionId;
		this.eventAction = eventAction;
	}

	public Integer getEventActionId() {
		return eventActionId;
	}

	public void setEventActionId(Integer eventActionId) {
		this.eventActionId = eventActionId;
	}

	public String getEventAction() {
		return eventAction;
	}

	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}

	@Override
	public String toString() {
		return "ArtLogEventAction [eventActionId=" + eventActionId + ", eventAction=" + eventAction + "]";
	}

	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"eventActionId\":" + "\"" + getEventActionId() + "\"");
		sb.append(",\"eventAction\":" + "\"" + getEventAction() + "\"");
		sb.append("}");
		return sb.toString();
	}
}

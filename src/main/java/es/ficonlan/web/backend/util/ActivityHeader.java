package es.ficonlan.web.backend.util;

import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.entities.Activity.ActivityType;
import es.ficonlan.web.backend.jersey.util.JsonActivityTypeDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonActivityTypeSerializer;
import es.ficonlan.web.backend.jersey.util.JsonDateDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonDateSerializer;

public class ActivityHeader {
	
	private int activityId;
	private int event;
	private String name;
	private ActivityType type;
	private Calendar startDate;
	private Calendar endDate;
	
	public ActivityHeader(int activityId, int event, String name, ActivityType type, Calendar startDate, Calendar endDate) {
		this.activityId = activityId;
		this.event = event;
		this.name = name;
		this.type = type;
		this.startDate = startDate;
		this.endDate  = endDate;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonDeserialize(using = JsonActivityTypeDeserializer.class)
	@JsonSerialize(using = JsonActivityTypeSerializer.class)
	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using=JsonDateSerializer.class)
	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using=JsonDateSerializer.class)
	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	
}

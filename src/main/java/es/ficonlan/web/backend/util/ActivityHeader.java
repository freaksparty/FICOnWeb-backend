package es.ficonlan.web.backend.util;

import java.util.Calendar;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.JsonActivityTypeDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonActivityTypeSerializer;
import es.ficonlan.web.backend.model.activity.Activity.ActivityType;

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

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}

	@JsonDeserialize(using = JsonActivityTypeDeserializer.class)
	@JsonSerialize(using = JsonActivityTypeSerializer.class)
	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	@JsonDeserialize(using = JsonActivityTypeDeserializer.class)
	@JsonSerialize(using = JsonActivityTypeSerializer.class)
	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	
}

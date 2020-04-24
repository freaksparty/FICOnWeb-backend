/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.ficonlan.web.backend.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.serializer.JsonActivityTypeDeserializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonActivityTypeSerializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonDateDeserializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonDateSerializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonEntityIdSerializer;
import es.ficonlan.web.backend.util.ActivityHeader;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
public class Activity {

	public enum ActivityType {
		Production, Conference, Tournament, Workshop
	};

	private int activityId;
	private Event event;
	private String name;
	private String description;
	private String imageurl;
	private int numParticipants;
	private ActivityType type;
	private boolean oficial;
	private Calendar startDate;
	private Calendar endDate;
	private Calendar regDateOpen;
	private Calendar regDateClose;
	private List<User> participants = new ArrayList<User>();

	public Activity() {
	}

	public Activity(int activityId, String name, String description,
			int numParticipants, ActivityType type, boolean oficial,
			Calendar startDate, Calendar endDate, Calendar regDateOpen,
			Calendar regDateClose) {
		this.activityId = activityId;
		this.name = name;
		this.description = description;
		this.numParticipants = numParticipants;
		this.type = type;
		this.oficial = oficial;
		this.startDate = startDate;
		this.endDate = endDate;
		this.regDateOpen = regDateOpen;
		this.regDateClose = regDateClose;
	}

	public Activity(Event event, String name, String description,
			int numParticipants, ActivityType type, boolean oficial,
			Calendar startDate, Calendar endDate, Calendar regDateOpen,
			Calendar regDateClose) {
		this(0, name, description, numParticipants, type, oficial, startDate,
				endDate, regDateOpen, regDateClose);
		this.event = event;

	}

	@Id
	@Column(name = "Activity_id")
	@SequenceGenerator(name = "ActivityIdGenerator", sequenceName = "ActivitySeq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ActivityIdGenerator")
	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	@Column(name = "Activity_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Activity_description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "Activity_imageurl")
	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	@Column(name = "Activity_num_participants")
	public int getNumParticipants() {
		return numParticipants;
	}

	public void setNumParticipants(int numParticipants) {
		this.numParticipants = numParticipants;
	}

	@JsonDeserialize(using = JsonActivityTypeDeserializer.class)
	@JsonSerialize(using = JsonActivityTypeSerializer.class)
	@Column(name = "Activity_type_activity")
	@Enumerated(EnumType.ORDINAL)
	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}

	@Column(name = "Activity_official")
	public boolean isOficial() {
		return oficial;
	}

	public void setOficial(boolean oficial) {
		this.oficial = oficial;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "Activity_date_start")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "Activity_date_end")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "Activity_reg_date_open")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Calendar getRegDateOpen() {
		return regDateOpen;
	}

	public void setRegDateOpen(Calendar regDateOpen) {
		this.regDateOpen = regDateOpen;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "Activity_reg_date_close")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Calendar getRegDateClose() {
		return regDateClose;
	}

	public void setRegDateClose(Calendar regDateClose) {
		this.regDateClose = regDateClose;
	}

	@JsonSerialize(using = JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Activity_event_id")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "User_Activity", joinColumns = { @JoinColumn(name = "User_Activity_Activity_id") }, inverseJoinColumns = { @JoinColumn(name = "User_Activity_User_id") })
	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	public ActivityHeader generateActivityHeader() {
		return new ActivityHeader(activityId,event.getEventId(),name,type,startDate, endDate);
	}

}

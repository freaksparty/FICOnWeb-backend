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

import java.util.Calendar;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.serializer.JsonDateDeserializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonDateSerializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonEntityIdSerializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonRegistrationStateDeserializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonRegistrationStateSerializer;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Entity
public class Registration {
	
	public enum RegistrationState {registered, inQueue, paid};
	
	private int registrationId;
	private User user;
	private Event event;
	private RegistrationState state;
	private Calendar registrationDate;
	private Calendar paidDate;
	private boolean paid = false;
	private int place;
	@Transient
	private int placeOnQueue;

	public Registration() { }
	
    public Registration(User user, Event event) {
		this.user = user;
		this.event = event;
		this.state = RegistrationState.registered;
		this.registrationDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		this.paidDate = null;
		this.paid = false;
	}

	@Column(name = "Registration_id")
    @SequenceGenerator(name = "registrationIdGenerator", sequenceName = "registrationSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "registrationIdGenerator")
	public int getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(int registrationId) {
		this.registrationId = registrationId;
	}

	@JsonSerialize(using=JsonEntityIdSerializer.class)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="Registration_User_id")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonSerialize(using = JsonEntityIdSerializer.class)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Registration_Event_id")
	public Event getEvent() {
		return event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}
	
	
	@JsonDeserialize(using = JsonRegistrationStateDeserializer.class)
	@JsonSerialize(using = JsonRegistrationStateSerializer.class)
	@Column(name="Registration_state") 
	@Enumerated(EnumType.ORDINAL)  
	public RegistrationState getState() {
		return state;
	}
	
	public void setState(RegistrationState state) {
		this.state = state;
	}
	
	@JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
	@Column(name = "Registration_date_created")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Calendar getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(Calendar registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	@JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
	@Column(name = "Registration_date_paid")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Calendar getPaidDate() {
		return paidDate;
	}
	
	public void setPaidDate(Calendar paidDate) {
		this.paidDate = paidDate;
	}
	
	@Column(name = "Registration_paid")
	public boolean isPaid() {
		return paid;
	}
	
	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	@Column(name = "Registration_place")
	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}
	
	@Transient
	public int getPlaceOnQueue() {
		return placeOnQueue;
	}

	@Transient
	public void setPlaceOnQueue(int placeOnQueue) {
		this.placeOnQueue = placeOnQueue;
	}

}

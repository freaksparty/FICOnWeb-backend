package es.ficonlan.web.backend.util;

import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.entities.Registration.RegistrationState;
import es.ficonlan.web.backend.jersey.util.serializer.JsonDateDeserializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonDateSerializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonRegistrationStateDeserializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonRegistrationStateSerializer;

public class RegistrationData {

	private String login;
	private String dni;
	private int registrationId;
	private int userID;
	private int eventID;
	private RegistrationState state;
	private Calendar registrationDate;
	private Calendar paidDate;
	private boolean paid = false;
	private int place;
	private int placeOnQueue;
	
	public RegistrationData(String login, String dni, int registrationId, int userID, int eventID, RegistrationState state, Calendar registrationDate, Calendar paidDate,
			boolean paid, int place, int placeOnQueue) {
		super();
		this.login = login;
		this.dni = dni;
		this.registrationId = registrationId;
		this.userID = userID;
		this.eventID = eventID;
		this.state = state;
		this.registrationDate = registrationDate;
		this.paidDate = paidDate;
		this.paid = paid;
		this.place = place;
		this.placeOnQueue = placeOnQueue;
	}
	
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public int getRegistrationId() {
		return registrationId;
	}
	
	public void setRegistrationId(int registrationId) {
		this.registrationId = registrationId;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public int getEventID() {
		return eventID;
	}
	
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	
	@JsonDeserialize(using = JsonRegistrationStateDeserializer.class)
	@JsonSerialize(using = JsonRegistrationStateSerializer.class)
	public RegistrationState getState() {
		return state;
	}
	
	public void setState(RegistrationState state) {
		this.state = state;
	}
	
	@JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
	public Calendar getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(Calendar registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public Calendar getPaidDate() {
		return paidDate;
	}
	
	@JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
	public void setPaidDate(Calendar paidDate) {
		this.paidDate = paidDate;
	}
	
	public boolean isPaid() {
		return paid;
	}
	
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	public int getPlace() {
		return place;
	}
	
	public void setPlace(int place) {
		this.place = place;
	}
	
	public int getPlaceOnQueue() {
		return placeOnQueue;
	}
	
	public void setPlaceOnQueue(int placeOnQueue) {
		this.placeOnQueue = placeOnQueue;
	}
	
	@Override
	public String toString() {
		return  this.login + " - " + 
				this.state + " - " + 
				this.place + " - " + 
				this.placeOnQueue + " - " +
				this.paid ;
	}
}

package es.ficonlan.web.backend.output;

import java.util.Calendar;

import es.ficonlan.web.backend.entities.Registration;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;

public class EventRegistrationData {
	
	/** When sending the RegistrationData event only can be open */
	private final boolean open = true;
	private int registrationId;
	private int user;
	private int event;
	private RegistrationState state;
	private Calendar registrationDate;
	private Calendar paidDate;
	private boolean paid = false;
	private int place;
	private int placeOnQueue;
	
	public EventRegistrationData(Registration r) {
		this.registrationId = r.getRegistrationId();
		this.user = r.getUser().getUserId();
		this.event = r.getEvent().getEventId();
		this.state = r.getState();
		this.registrationDate = r.getRegistrationDate();
		this.paidDate = r.getPaidDate();
		this.paid = r.isPaid();
		this.place = r.getPlace();
		this.placeOnQueue = r.getPlaceOnQueue();
		
	}
	
	public int getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(int registrationId) {
		this.registrationId = registrationId;
	}
	
	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

	public RegistrationState getState() {
		return state;
	}
	public void setState(RegistrationState state) {
		this.state = state;
	}
	public Calendar getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Calendar registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Calendar getPaidDate() {
		return paidDate;
	}
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
	public boolean isOpen() {
		return open;
	}
}

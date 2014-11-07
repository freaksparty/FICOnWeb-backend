package es.ficonlan.web.backend.model.registration;

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

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.JsonDateDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonDateSerializer;
import es.ficonlan.web.backend.jersey.util.JsonEmailDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonEntityIdSerializer;
import es.ficonlan.web.backend.jersey.util.JsonEventDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonRegistrationStateDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonRegistrationStateSerializer;
import es.ficonlan.web.backend.jersey.util.JsonUserDeserializer;
import es.ficonlan.web.backend.model.email.Email;
import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.user.User;

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
	private Email lastemail;

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

	@JsonDeserialize(using = JsonUserDeserializer.class)
	@JsonSerialize(using=JsonEntityIdSerializer.class)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Registration_User_id")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonDeserialize(using = JsonEventDeserializer.class)
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

	@JsonDeserialize(using = JsonEmailDeserializer.class)
    @JsonSerialize(using=JsonEntityIdSerializer.class)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Registration_lastemail")
	public Email getLastemail() {
		return lastemail;
	}

	public void setLastemail(Email lastemail) {
		this.lastemail = lastemail;
	}
	
	
}

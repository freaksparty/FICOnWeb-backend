package es.ficonlan.web.backend.model.registration;

import java.util.Calendar;

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

import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.user.User;

/**
 * @author Daniel Gómez Silva
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
	
	public Registration(){}
	
    public Registration(User user, Event event) {
		this.user = user;
		this.event = event;
		this.state = RegistrationState.registered;
		this.registrationDate = Calendar.getInstance();
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Registration_User_id")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Registration_Event_id")
	public Event getEvent() {
		return event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}
	
	@Column(name="Registration_state") 
	@Enumerated(EnumType.ORDINAL)  
	public RegistrationState getState() {
		return state;
	}
	
	public void setState(RegistrationState state) {
		this.state = state;
	}
	
	@Column(name = "Registration_date_created")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Calendar getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(Calendar registrationDate) {
		this.registrationDate = registrationDate;
	}
	
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
	
}

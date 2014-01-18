package es.ficonlan.web.backend.model.activity;

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

import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.user.User;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
public class Activity {
	
	public enum ActivityType {Production, Conference, Tournament};
	
	private int activityId;
	private User organizer;
	private Event event;
	private String name;
	private String description;
	private int numParticipants;
	private ActivityType type;
	private boolean oficial; 
	private Calendar startDate;
	private Calendar endDate;
	private Calendar regDateOpen;
	private Calendar regDateClose;
	private List<User> participants = new ArrayList<User>();
	
	@Column(name = "Activity_id")
	@SequenceGenerator(name = "ActivityIdGenerator", sequenceName = "ActivitySeq")
	@Id
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
	
	@Column(name = "Activity_num_participants")
	public int getNumParticipants() {
		return numParticipants;
	}
	
	public void setNumParticipants(int numParticipants) {
		this.numParticipants = numParticipants;
	}
	
	@Column(name = "Activity_type_activity")
	@Enumerated(EnumType.ORDINAL) 
	public ActivityType getType() {
		return type;
	}
	
	public void setType(ActivityType type) {
		this.type = type;
	}
	
	@Column(name = "Activity_kind")
	public boolean isOficial() {
		return oficial;
	}
	
	public void setOficial(boolean oficial) {
		this.oficial = oficial;
	}
	
	@Column(name = "Activity_date_start")
	public Calendar getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
	@Column(name = "Activity_date_end")
	public Calendar getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	
	@Column(name = "Activity_reg_date_open")
	public Calendar getRegDateOpen() {
		return regDateOpen;
	}
	
	public void setRegDateOpen(Calendar regDateOpen) {
		this.regDateOpen = regDateOpen;
	}
	
	@Column(name = "Activity_reg_date_close")
	public Calendar getRegDateClose() {
		return regDateClose;
	}
	
	public void setRegDateClose(Calendar regDateClose) {
		this.regDateClose = regDateClose;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Activity_organizer_id ")
	public User getOrganizer() {
		return organizer;
	}

	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Activity_event_id")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_activity", joinColumns = {
	      @JoinColumn(name = "User_Activity_Activity_id")}, inverseJoinColumns = {
	      @JoinColumn(name = "User_Activity_User_id")})
	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}
}

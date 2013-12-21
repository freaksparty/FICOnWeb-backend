package es.ficonlan.web.backend.model.activity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.codehaus.jackson.annotate.JsonIgnore;

import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.user.User;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
public class Activity {
	
	private long activityId;
	private User organizer;
	private Event event;
	private String name;
	private String description;
	private int numParticipantes;
	private int tipo;              // Siguiendo el modelo 1=Produccion, 2=Conferencia 3=Torneo
	private boolean oficial; 
	private Calendar dateStart;
	private Calendar dateEnd;
	@JsonIgnore
	private Calendar regDateOpen;
	@JsonIgnore
	private Calendar regDateClose;
	
	@Column(name = "Activity_id")
	@SequenceGenerator(name = "ActivityIdGenerator", sequenceName = "ActivitySeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ActivityIdGenerator")
	public long getActivityId() {
		return activityId;
	}
	
	public void setActivityId(long activityId) {
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
	public int getNumParticipantes() {
		return numParticipantes;
	}
	
	public void setNumParticipantes(int numParticipantes) {
		this.numParticipantes = numParticipantes;
	}
	
	@Column(name = "Activity_type_activity")
	public int getTipo() {
		return tipo;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	@Column(name = "Activity_kind")
	public boolean isOficial() {
		return oficial;
	}
	
	public void setOficial(boolean oficial) {
		this.oficial = oficial;
	}
	
	@Column(name = "Activity_date_start")
	public Calendar getDateStart() {
		return dateStart;
	}
	
	public void setDateStart(Calendar dateStart) {
		this.dateStart = dateStart;
	}
	
	@Column(name = "Activity_date_end")
	public Calendar getDateEnd() {
		return dateEnd;
	}
	
	public void setDateEnd(Calendar dateEnd) {
		this.dateEnd = dateEnd;
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
	
}

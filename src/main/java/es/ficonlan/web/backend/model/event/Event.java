package es.ficonlan.web.backend.model.event;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

@Entity
public class Event {

    private int eventId;
    private String name;
    private String description;
    private int numParticipants;
    private Calendar startDate;
    private Calendar endDate;
    private Calendar registrationOpenDate;
    private Calendar registrationCloseDate;
    //private Set<Activity> activities;

    public Event() {
    }

    public Event(int eventId, String name, String description, int numParticipants,
            Calendar startDate, Calendar endDate, Calendar registrationOpenDate,
            Calendar registrationCloseDate) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.numParticipants = numParticipants;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationOpenDate = registrationOpenDate;
        this.registrationCloseDate = registrationCloseDate;
    }

    @Column(name = "Event_id")
    @SequenceGenerator(name = "eventIdGenerator", sequenceName = "eventSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "eventIdGenerator")
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Column(name = "Event_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Event_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "Event_num_participants")
    public int getNumParticipants() {
        return numParticipants;
    }

    public void setNumParticipants(int numParticipants) {
        this.numParticipants = numParticipants;
    }

    @Column(name = "Event_date_start")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    @Column(name = "Event_date_end")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    @Column(name = "Event_reg_date_open")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Calendar getRegistrationOpenDate() {
        return registrationOpenDate;
    }

    public void setRegistrationOpenDate(Calendar registrationOpenDate) {
        this.registrationOpenDate = registrationOpenDate;
    }

    @Column(name = "Event_reg_date_close")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Calendar getRegistrationCloseDate() {
        return registrationCloseDate;
    }

    public void setRegistrationCloseDate(Calendar registrationCloseDate) {
        this.registrationCloseDate = registrationCloseDate;
    }

    /*@OneToMany(mappedBy = "Activity_event_id", fetch = FetchType.LAZY)
     public Set<Activity> getActivities() {
     return activities;
     }

     public void setActivities(Set<Activity> activities) {
     this.activities = activities;
     }*/
}

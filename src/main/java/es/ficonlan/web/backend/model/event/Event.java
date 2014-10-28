package es.ficonlan.web.backend.model.event;

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
import javax.persistence.Temporal;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.JsonDateDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonDateSerializer;
import es.ficonlan.web.backend.jersey.util.JsonEntityIdSerializer;
import es.ficonlan.web.backend.model.emailtemplate.EmailTemplate;
import es.ficonlan.web.backend.jersey.util.JsonEmailTemplateDeserializer;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
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
    private EmailTemplate setPaidTemplate;
    private EmailTemplate onQueueTemplate;
    private EmailTemplate outstandingTemplate;
    private EmailTemplate outOfDateTemplate;
    private EmailTemplate fromQueueToOutstanding;
    

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

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
    @Column(name = "Event_date_start")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
    @Column(name = "Event_date_end")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }
    
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
    @Column(name = "Event_reg_date_open")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Calendar getRegistrationOpenDate() {
        return registrationOpenDate;
    }

    public void setRegistrationOpenDate(Calendar registrationOpenDate) {
        this.registrationOpenDate = registrationOpenDate;
    }
    
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
    @Column(name = "Event_reg_date_close")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Calendar getRegistrationCloseDate() {
        return registrationCloseDate;
    }

    public void setRegistrationCloseDate(Calendar registrationCloseDate) {
        this.registrationCloseDate = registrationCloseDate;
    }
    
    @JsonDeserialize(using = JsonEmailTemplateDeserializer.class)
    @JsonSerialize(using=JsonEntityIdSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Event_setPaidTemplate_id")
	public EmailTemplate getSetPaidTemplate() {
		return setPaidTemplate;
	}

	public void setSetPaidTemplate(EmailTemplate setPaidTemplate) {
		this.setPaidTemplate = setPaidTemplate;
	}

	@JsonDeserialize(using = JsonEmailTemplateDeserializer.class)
    @JsonSerialize(using=JsonEntityIdSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Event_onQueueTemplate_id")
	public EmailTemplate getOnQueueTemplate() {
		return onQueueTemplate;
	}

	public void setOnQueueTemplate(EmailTemplate onQueueTemplate) {
		this.onQueueTemplate = onQueueTemplate;
	}

	@JsonDeserialize(using = JsonEmailTemplateDeserializer.class)
    @JsonSerialize(using=JsonEntityIdSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Event_outstandingTemplate_id")
	public EmailTemplate getOutstandingTemplate() {
		return outstandingTemplate;
	}

	public void setOutstandingTemplate(EmailTemplate outstandingTemplate) {
		this.outstandingTemplate = outstandingTemplate;
	}

	@JsonDeserialize(using = JsonEmailTemplateDeserializer.class)
    @JsonSerialize(using=JsonEntityIdSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Event_outOfDateTemplate_id")
	public EmailTemplate getOutOfDateTemplate() {
		return outOfDateTemplate;
	}

	public void setOutOfDateTemplate(EmailTemplate outOfDateTemplate) {
		this.outOfDateTemplate = outOfDateTemplate;
	}

	@JsonDeserialize(using = JsonEmailTemplateDeserializer.class)
    @JsonSerialize(using=JsonEntityIdSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Event_fromQueueToOutstanding_id")
	public EmailTemplate getFromQueueToOutstanding() {
		return fromQueueToOutstanding;
	}

	public void setFromQueueToOutstanding(EmailTemplate fromQueueToOutstanding) {
		this.fromQueueToOutstanding = fromQueueToOutstanding;
	}

}

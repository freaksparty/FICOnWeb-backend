package es.ficonlan.web.backend.output;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ws.rs.core.EntityTag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.entities.Activity;
import es.ficonlan.web.backend.entities.Activity.ActivityType;
import es.ficonlan.web.backend.jersey.util.serializer.JsonActivityTypeDeserializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonDateSerializer;
import es.ficonlan.web.backend.util.cache.Cacheable;

/*
 * @author Siro González <xiromoreira>
 */
public class ActivityData implements Cacheable {

	private int activityId;
//	private Event event;
	private String name;
	private String description;
	private String imageurl;
	private int participantCapacity;
	@JsonDeserialize(using = JsonActivityTypeDeserializer.class)
	private ActivityType type;
	private boolean oficial;
	@JsonSerialize(using = JsonDateSerializer.class)
	private Calendar startDate;
	@JsonSerialize(using = JsonDateSerializer.class)
	private Calendar endDate;
	@JsonSerialize(using = JsonDateSerializer.class)
	private Calendar regDateOpen;
	@JsonSerialize(using = JsonDateSerializer.class)
	private Calendar regDateClose;
	private int participantsRegistered;
	
    public boolean isOpen;
	
    @JsonIgnore
    public long timeToOpen;
    @JsonIgnore
    private EntityTag tag;
	
	public ActivityData(Activity a) {
		this.activityId = a.getActivityId();
		this.name = a.getName();
		this.description = a.getDescription();
		this.imageurl = a.getImageurl();
		this.participantCapacity = a.getNumParticipants();
		this.type = a.getType();
		this.oficial = a.isOficial();
		this.startDate = a.getStartDate();
		this.endDate = a.getEndDate();
		this.regDateOpen = a.getRegDateOpen();
		this.regDateClose = a.getRegDateClose();
		if(a.getParticipants() != null)
			this.participantsRegistered = a.getParticipants().size();
		else
			this.participantsRegistered = 0;
		
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		isOpen = (now.after(regDateOpen) && now.before(regDateClose));
		timeToOpen = (regDateOpen.getTimeInMillis() - now.getTimeInMillis()) / 1000;
		tag = new EntityTag(Long.toString(System.currentTimeMillis()));
	}
	
    public long getTimeToOpen() {
    	Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    	timeToOpen = (regDateOpen.getTimeInMillis() - now.getTimeInMillis()) / 1000;
    	return timeToOpen;
    }
    
    public void updateIsOpen() {
    	Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    	timeToOpen = (regDateOpen.getTimeInMillis() - now.getTimeInMillis()) / 1000;
    	if(isOpen != (now.after(regDateOpen) && now.before(regDateClose))) {
    		isOpen = !isOpen;
    		tag = new EntityTag(Long.toString(System.currentTimeMillis()));
    	}
    }
	
	@JsonIgnore
    @Override
    public EntityTag getTag() {
    	return tag;
    }

	@Override
	public long timeToExpire() {
		if(isOpen) {
			return 300;
		} else {
			return timeToOpen;
		}
	}

	public int getActivityId() {
		return activityId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getImageurl() {
		return imageurl;
	}

	public int getParticipantCapacity() {
		return participantCapacity;
	}

	public ActivityType getType() {
		return type;
	}

	public boolean isOficial() {
		return oficial;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public Calendar getRegDateOpen() {
		return regDateOpen;
	}

	public Calendar getRegDateClose() {
		return regDateClose;
	}

	public int getParticipantsRegistered() {
		return participantsRegistered;
	}

	public boolean isOpen() {
		return isOpen;
	}
	
}
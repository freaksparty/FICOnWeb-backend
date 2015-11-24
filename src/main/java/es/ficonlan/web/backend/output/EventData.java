package es.ficonlan.web.backend.output;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.core.EntityTag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.entities.Activity;
import es.ficonlan.web.backend.entities.EmailTemplate;
import es.ficonlan.web.backend.entities.Event;
import es.ficonlan.web.backend.jersey.util.JsonDateSerializer;
import es.ficonlan.web.backend.util.cache.Cacheable;

/*
 * @author Siro González <xiromoreira>
 */
public class EventData implements Cacheable {
	public static class ActivityDataShort {
		public long activityId;
		public String name;
		
		public ActivityDataShort(Activity a) {
			activityId = a.getActivityId();
			name = a.getName();
		}
	}
	
	
    public int eventId;
    public String name;
    public String description;
    public int minimunAge;
    public int price;
    @JsonSerialize(using = JsonDateSerializer.class)
    public Calendar startDate;
    @JsonSerialize(using = JsonDateSerializer.class)
    public Calendar endDate;
    @JsonSerialize(using = JsonDateSerializer.class)
    public Calendar openInscriptionDate;
    public String rules;
    public ArrayList<ActivityDataShort> tournaments = new ArrayList<ActivityDataShort>();
    public ArrayList<ActivityDataShort> conferences = new ArrayList<ActivityDataShort>();
    public ArrayList<ActivityDataShort> productions = new ArrayList<ActivityDataShort>();
    @JsonIgnore
    private Calendar closeInscriptionDate;
    @JsonIgnore
    private EntityTag tag;
    //Set when Inscriptions are open, it is not calculated all the time to preserve tag coherence.
    public boolean isOpen;
    
    @JsonIgnore
    public long timeToOpen;
    
    public EventData(Event ev) {
    	eventId = ev.getEventId();
    	name = ev.getName();
    	description = ev.getDescription();
    	minimunAge = ev.getMinimunAge();
    	price = ev.getPrice();
    	startDate = ev.getStartDate();
    	endDate = ev.getEndDate();
    	openInscriptionDate = ev.getRegistrationOpenDate();
    	closeInscriptionDate = ev.getRegistrationCloseDate();
		rules = ev.getNormas();
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		isOpen = (now.after(openInscriptionDate) && now.before(closeInscriptionDate));
		timeToOpen = (now.getTimeInMillis() - openInscriptionDate.getTimeInMillis()) / 1000;
		tag = new EntityTag(Long.toString(System.currentTimeMillis()));
    }
    
    public void updateIsOpen() {
    	Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    	timeToOpen = (now.getTimeInMillis() - openInscriptionDate.getTimeInMillis()) / 1000;
    	if(isOpen != (now.after(openInscriptionDate) && now.before(closeInscriptionDate))) {
    		isOpen = !isOpen;
    		tag = new EntityTag(Long.toString(System.currentTimeMillis()));
    	}
    }
    
    public void addActivities(List<Activity> tl) {
    	for(Activity a : tl) {
    		switch(a.getType()) {
    			case Conference:
    				conferences.add(new ActivityDataShort(a));
    				break;
    			case Tournament:
    				tournaments.add(new ActivityDataShort(a));
    				break;
    			case Production:
    				tournaments.add(new ActivityDataShort(a));
    				break;
    		}
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
}

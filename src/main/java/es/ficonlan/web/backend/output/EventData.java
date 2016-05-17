package es.ficonlan.web.backend.output;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.core.EntityTag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.entities.Activity;
import es.ficonlan.web.backend.entities.Event;
import es.ficonlan.web.backend.entities.Sponsor;
import es.ficonlan.web.backend.jersey.util.JsonDateSerializer;
import es.ficonlan.web.backend.util.cache.Cacheable;

//TODO flexible way of defining the different Activity types. These should depend only on the DB and not in the program logic.

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
	
	public static class SponsorDataShort {
		public int sponsorId;
		public String name;
		public String url;
		public String imageurl;
		
		public SponsorDataShort(Sponsor s) {
			sponsorId = s.getSponsorId();
			name = s.getName();
			url = s.getUrl();
			imageurl = s.getImageurl();
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
    @JsonSerialize(using = JsonDateSerializer.class)
    private Calendar closeInscriptionDate;
    public String rules;
    public List<ActivityDataShort> tournaments = new ArrayList<ActivityDataShort>();
    public List<ActivityDataShort> conferences = new ArrayList<ActivityDataShort>();
    public List<ActivityDataShort> productions = new ArrayList<ActivityDataShort>();
    public List<ActivityDataShort> workshops = new ArrayList<ActivityDataShort>();
    public List<SponsorDataShort> sponsors = new ArrayList<SponsorDataShort>();
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
		timeToOpen = (openInscriptionDate.getTimeInMillis() - now.getTimeInMillis()) / 1000;
		tag = new EntityTag(Long.toString(System.currentTimeMillis()));
    }
    
    public long getTimeToOpen() {
    	Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    	timeToOpen = (openInscriptionDate.getTimeInMillis() - now.getTimeInMillis()) / 1000;
    	return timeToOpen;
    }
    
    public void updateIsOpen() {
    	Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    	timeToOpen = (openInscriptionDate.getTimeInMillis() - now.getTimeInMillis()) / 1000;
    	if(isOpen != (now.after(openInscriptionDate) && now.before(closeInscriptionDate))) {
    		isOpen = !isOpen;
    		tag = new EntityTag(Long.toString(System.currentTimeMillis()));
    	}
    }
    
    public void addActivities(Collection<Activity> tl) {
    	for(Activity a : tl) {
    		if(a==null) continue;
    		switch(a.getType()) {
    			case Conference:
    				conferences.add(new ActivityDataShort(a));
    				break;
    			case Tournament:
    				tournaments.add(new ActivityDataShort(a));
    				break;
    			case Production:
    				productions.add(new ActivityDataShort(a));
    				break;
    			case Workshop:
    				workshops.add(new ActivityDataShort(a));
    				break;
    		}
    	}
    }
    
    public void addSponsors(Collection<Sponsor> sponsors) {
    	for(Sponsor s : sponsors) {
    		this.sponsors.add(new SponsorDataShort(s));
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

package es.ficonlan.web.backend.output;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ws.rs.core.EntityTag;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.ficonlan.web.backend.entities.EmailTemplate;
import es.ficonlan.web.backend.entities.Event;

/*
 * @author Siro González <xiromoreira>
 */
public class EventData {
    public int eventId;
    public String name;
    public String description;
    public int minimunAge;
    public int price;
    public Calendar startDate;
    public Calendar endDate;
    public Calendar openInscriptionDate;
    public String rules;
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
    
    @JsonIgnore
    public EntityTag getTag() {
    	return tag;
    }
}

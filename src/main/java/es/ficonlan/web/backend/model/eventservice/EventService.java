package es.ficonlan.web.backend.model.eventservice;

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.model.activity.Activity;
import es.ficonlan.web.backend.model.activity.Activity.ActivityType;
import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.newsitem.NewsItem;
import es.ficonlan.web.backend.model.registration.Registration;
import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.sponsor.Sponsor;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author David Pereiro
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
public interface EventService {   

	public Event createEvent(String sessionId, Event event) throws ServiceException;
	
	public void removeEvent(String sessionId, int eventId) throws ServiceException;
	
	public Event getEvent(String sessionId, int eventId) throws ServiceException;
	
	public Event changeEventData(String sessionId, int eventId, Event eventData) throws ServiceException;
	
	public List<Event> getAllEvents(String sessionId) throws ServiceException;

    public List<Event> findEventByName(String sessionId, String name) throws ServiceException;
    
    
    public String getEventRules(String sessionId, int eventId) throws ServiceException;
    
    public boolean eventIsOpen(String sessionId, int eventId) throws ServiceException;
    
		
	public Registration addParticipantToEvent(String sessionId, int userId, int eventId) throws ServiceException;
	
	public void removeParticipantFromEvent(String sessionId, int userId, int eventId) throws ServiceException;
	
	public void setPaid(String sessionId, int userId, int eventId) throws ServiceException;
	
    public Registration getRegistration(String sessionId, int userId, int eventId) throws ServiceException;
	
	public void changeRegistrationState(String sessionId, int userId, int eventId, RegistrationState state) throws ServiceException;
	
	public void eventNumParticipantsChanged(String sessionId, int eventId) throws ServiceException;
	
    
    public Activity addActivity(String sessionId, int eventId,  Activity activity) throws ServiceException;
    
    public void removeActivity(String sessionId, int activityId) throws ServiceException;
    
    public Activity changeActivityData(String sessionId, int activityId, Activity activityData) throws ServiceException;
    
    public Activity getActivity(String sessionId, int activityId) throws ServiceException;
    
    public List<Activity> getAllActivities(String sessionId) throws ServiceException;
    
    public List<Activity> getActivitiesByEvent(String sessionId, int eventId, ActivityType type) throws ServiceException;
    
    public void addParticipantToActivity(String sessionId, int userId, int activityId) throws ServiceException;
    
    public void removeParticipantFromActivity(String sessionId, int userId, int activityId) throws ServiceException;
    
    public List<User> getActivityParticipants(String sessionId, int activityId) throws ServiceException;
    
    
    public NewsItem addNews(String sessionId, int eventId, NewsItem newsItem) throws ServiceException;
    
    public void changeNewsData(String sessionId, int newsItemId, NewsItem newsData) throws ServiceException;
    
    public NewsItem getNewsItem(String sessionId, int newsItemId) throws ServiceException;
    
    public List<NewsItem> getAllNewsItem(String sessionId) throws ServiceException;
    
    public List<NewsItem> getAllNewsItemFormEvent(String sessionId, int eventId, int startIndex, int cont, String orderBy, boolean desc) throws ServiceException;
    
    public List<NewsItem>getAllPublishedNewsItemFormEvent(String sessionId, int eventId, int startIndex, int cont) throws ServiceException;
    
    public long getAllPublishedNewsItemFromEventTam(String sessionId, int eventId) throws ServiceException;
    
    public long getAllNewsItemFromEventTam(String sessionId, int eventId) throws ServiceException;
    
    public List<NewsItem> getLastNews(String sessionId, Calendar dateLimit, boolean onlyOutstandingNews) throws ServiceException;
    
    public List<NewsItem> getLastNewsFromEvent(String sessionId, int eventId, Calendar dateLimit, boolean onlyOutstandingNews) throws ServiceException;
    
    public void removeNews(String sessionId, int newsItemId) throws ServiceException;
    
    
    public Sponsor addSponsor(String sessionId, Sponsor sponsor) throws ServiceException;
    
    public Sponsor addSponsor(String sessionId, int eventId, Sponsor sponsor) throws ServiceException;
    
    public void removeSponsor(String sessionId, int sponsorId) throws ServiceException;
    
    public List<Sponsor> getSponsors(String sessionId) throws ServiceException;
    
    public List<Sponsor> getSponsorsByEvent(String sessionId, int eventId) throws ServiceException;
    
    

	public void setPaidTemplate (String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void onQueueTemplate (String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void outstandingTemplate (String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void outOfDateTemplate (String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void fromQueueToOutstanding (String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
     
}

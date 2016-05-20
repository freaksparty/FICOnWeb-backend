package es.ficonlan.web.backend.services.eventservice;

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.entities.Activity;
import es.ficonlan.web.backend.entities.Activity.ActivityType;
import es.ficonlan.web.backend.entities.Event;
import es.ficonlan.web.backend.entities.NewsItem;
import es.ficonlan.web.backend.entities.Registration;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;
import es.ficonlan.web.backend.entities.Sponsor;
import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.output.ShirtData;
import es.ficonlan.web.backend.util.EventRegistrationState;
import es.ficonlan.web.backend.util.RegistrationData;

/**
 * @author David Pereiro
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
public interface EventService {

	public Event createEvent(Event event) throws ServiceException;
	
	public void removeEvent(String sessionId, int eventId) throws ServiceException;
	
	public Event getEvent(int eventId) throws ServiceException;
	
	public Event changeEventData(int eventId, Event eventData) throws ServiceException;
	
	public List<Event> getAllEvents(String sessionId) throws ServiceException;

    public List<Event> findEventByName(String sessionId, String name) throws ServiceException;
    
    public boolean eventIsOpen(int eventId) throws ServiceException;
    
	public Registration addParticipantToEvent(String sessionId, int userId, int eventId) throws ServiceException;
	
	public void removeParticipantFromEvent(String sessionId, int userId, int eventId) throws ServiceException;
	
	public void setPaid(String sessionId, int userId, int eventId) throws ServiceException;
	
    public Registration getRegistration(String sessionId, int userId, int eventId) throws ServiceException;
    
    public void sendRegistrationMail(String sessionId, int userId, int eventId) throws ServiceException;
	
	public void changeRegistrationState(String sessionId, int userId, int eventId, RegistrationState state) throws ServiceException;
	
	public void eventNumParticipantsChanged(int eventId) throws ServiceException;
	
	public List<RegistrationData> getRegistrationByEvent(int eventId, RegistrationState state, int startindex, int maxResults, String orderBy, boolean desc) throws ServiceException;
	
	public long getRegistrationByEventTAM(int eventId, RegistrationState state) throws ServiceException;
	
	public List<ShirtData> getShirtSizes(int eventId) throws ServiceException;
	
	public EventRegistrationState getEventRegistrationState(String sessionId, int eventId, int userId) throws ServiceException;
	
    
    public Activity addActivity(int eventId,  Activity activity) throws ServiceException;
    
    public void removeActivity(String sessionId, int activityId) throws ServiceException;
    
    public Activity changeActivityData(int activityId, Activity activityData) throws ServiceException;
    
    public Activity getActivity(int activityId) throws ServiceException;
    
    public List<Activity> getAllActivities(String sessionId) throws ServiceException;
    
    public List<Activity> getActivitiesByEvent(int eventId, ActivityType type);
    
    public List<Activity> getActivitiesByEvent(int eventId, int startIndex, int cont, String orderBy, boolean desc, ActivityType type) throws ServiceException;
    
    public long getActivitiesByEventTAM(int eventId, ActivityType type) throws ServiceException;
    
    public void addParticipantToActivity(String sessionId, int userId, int activityId) throws ServiceException;
    
    public void removeParticipantFromActivity(String sessionId, int userId, int activityId) throws ServiceException;
    
    public List<User> getActivityParticipants(String sessionId, int activityId) throws ServiceException;
    
    /**
     * Gets all the activities a given user is registered for a given event.
     * @param eventId
     * @param userId
     * @return
     */
    public List<Integer> getActivitiesRegistered(int eventId, int userId);
    
    
    public NewsItem addNews(String sessionId, int eventId, NewsItem newsItem) throws ServiceException;
    
    public void changeNewsData(String sessionId, int newsItemId, NewsItem newsData) throws ServiceException;
    
    public NewsItem getNewsItem(String sessionId, int newsItemId) throws ServiceException;
    
    public List<NewsItem> getAllNewsItem(String sessionId) throws ServiceException;
    
    public List<NewsItem> getAllNewsItemFormEvent(int eventId, int startIndex, int cont, String orderBy, boolean desc) throws ServiceException;
    
    public long getAllNewsItemFromEventTam(int eventId) throws ServiceException;
    
    public List<NewsItem> getPublishedNewsForEvent(int eventId, int startIndex, int cont) throws ServiceException;
    
    public long countPublishedNewsFromEvent(int eventId) throws ServiceException;
    
    /*
     * Returns the publishion time of the next non-published NewsItem
     */
    public Calendar nextNewsUpdate(int eventId) throws ServiceException;
    
    public List<NewsItem> getLastNews(String sessionId, Calendar dateLimit, boolean onlyOutstandingNews) throws ServiceException;
    
    public List<NewsItem> getLastNewsFromEvent(String sessionId, int eventId, Calendar dateLimit, boolean onlyOutstandingNews) throws ServiceException;
    
    public void removeNews(String sessionId, int newsItemId) throws ServiceException;
    
    public Sponsor addSponsor(String sessionId, Sponsor sponsor) throws ServiceException;
    
    public Sponsor addSponsor(String sessionId, int eventId, Sponsor sponsor) throws ServiceException;
    
    public void removeSponsor(String sessionId, int sponsorId) throws ServiceException;
    
    public List<Sponsor> getSponsors(String sessionId, int startIndex, int cont, String orderBy, boolean desc) throws ServiceException;
    
    public long getSponsorsTAM(String sessionId) throws ServiceException;
    
    public List<Sponsor> getSponsorsByEvent(int eventId) throws ServiceException;
    

	public void setPaidTemplate (String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void onQueueTemplate (String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void outstandingTemplate (String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void outOfDateTemplate (String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void fromQueueToOutstanding (String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
     
}

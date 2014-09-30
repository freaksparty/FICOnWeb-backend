package es.ficonlan.web.backend.model.eventservice;

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.model.activity.Activity;
import es.ficonlan.web.backend.model.activity.Activity.ActivityType;
import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.newsitem.NewsItem;
import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author David Pereiro
 * @author Daniel Gómez Silva
 */
public interface EventService {   

	public Event createEvent(String sessionId, Event event) throws ServiceException;
	
	public void changeEventData(String sessionId, Event eventData) throws ServiceException;
		
	public void addParticipantToEvent(String sessionId, int userId, int eventId) throws ServiceException;
	
	public void removeParticipantFromEvent(String sessionId, int userId, int eventId) throws ServiceException;
	
	public void setPaid(String sessionId, int userId, int eventId) throws ServiceException;
	
	public void changeRegistrationState(String sessionId, int userId, int eventId, RegistrationState state) throws ServiceException;

    public List<Event> findEventByName(String sessionId, String name) throws ServiceException;
    
    public Activity addActivity(String sessionId, int eventId,  Activity activity) throws ServiceException;
    
    public void removeActivity(String sessionId, int activityId) throws ServiceException;
    
    public void changeActivityData(String sessionId, Activity activityData) throws ServiceException;
    
    public void setActivityOrganizer(String sessionId, int activityId,  int userId) throws ServiceException;
    
    public List<Activity> getActivitiesByEvent(String sessionId, int eventId, ActivityType type) throws ServiceException;
    
    public void addParticipantToActivity(String sessionId, int userId, int activityId) throws ServiceException;
    
    public void removeParticipantFromActivity(String sessionId, int userId, int activityId) throws ServiceException;
    
    public List<User> getActivityParticipants(String sessionId, int activityId) throws ServiceException;
    
    public NewsItem addNews(String sessionId, int eventId, NewsItem newsItem) throws ServiceException;
    
    public void changeNewsData(String sessionId, NewsItem newsData) throws ServiceException;
    
    public List<NewsItem> getLastNews(String sessionId, Calendar dateLimit, boolean onlyOutstandingNews) throws ServiceException;
    
    public void removeNews(String sessionId, int newsItemId) throws ServiceException;
    
}

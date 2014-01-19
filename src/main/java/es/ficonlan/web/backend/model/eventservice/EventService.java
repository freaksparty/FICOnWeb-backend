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

	public Event createEvent(long sessionId, Event event) throws ServiceException;
	
	public void changeEventData(long sessionId, Event eventData) throws ServiceException;
		
	public void addParticipantToEvent(long sessionId, int userId, int eventId) throws ServiceException;
	
	public void removeParticipantFromEvent(long sessionId, int userId, int eventId) throws ServiceException;
	
	public void changeRegistrationState(long sessionId, int userId, int eventId, RegistrationState state) throws ServiceException;

    public Event findEventByName(long sessionId, String name) throws ServiceException;
    
    public Activity addActivity(long sessionId, int eventId,  Activity activity) throws ServiceException;
    
    public void removeActivity(long sessionId, int activityId) throws ServiceException;
    
    public void changeActivityData(long sessionId, Activity activityData) throws ServiceException;
    
    public void setActivityOrganizer(long sessionId, int activityId,  int userId) throws ServiceException;
    
    public List<Activity> getActivitiesByEvent(long sessionId, int eventId, ActivityType type) throws ServiceException;
    
    public void addParticipantToActivity(long sessionId, int userId, int activityId) throws ServiceException;
    
    public void removeParticipantFromActivity(long sessionId, int userId, int activityId) throws ServiceException;
    
    public List<User> getActivityParticipants(long sessionId, int activityId) throws ServiceException;
    
    public NewsItem addNews(long sessionId, int eventId, NewsItem newsItem) throws ServiceException;
    
    public void changeNewsData(long sessionId, NewsItem newsData) throws ServiceException;
    
    public List<NewsItem> getLastNews(long sessionId, Calendar dateLimit, boolean onlyOutstandingNews) throws ServiceException;
    
    public void removeNews(long sessionId, int newsItemId) throws ServiceException;
    
}

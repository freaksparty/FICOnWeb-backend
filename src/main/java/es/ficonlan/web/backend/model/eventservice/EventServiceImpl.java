package es.ficonlan.web.backend.model.eventservice;

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.model.activity.Activity;
import es.ficonlan.web.backend.model.activity.Activity.ActivityType;
import es.ficonlan.web.backend.model.activity.ActivityDao;
import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.event.EventDao;
import es.ficonlan.web.backend.model.newsItem.NewsDao;
import es.ficonlan.web.backend.model.newsItem.NewsItem;
import es.ficonlan.web.backend.model.registration.Registration;
import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.registration.RegistrationDao;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.user.UserDao;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author David Pereiro
 * @author Daniel Gómez Silva
 */
@Service("EventService")
@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;
    
	@Autowired
	private RegistrationDao registrationDao;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private NewsDao newsDao;
    
	@Autowired
	private UserDao userDao;
    
    @Transactional
	public Event createEvent(long sessionId, Event event) throws ServiceException {
    	SessionManager.checkPermissions(sessionId, "createEvent");
		if(event.getName()==null) throw new ServiceException(05,"createEvent","name");
		if(event.getStartDate()==null) throw new ServiceException(05,"createEvent","startDate");
		if(event.getEndDate()==null) throw new ServiceException(05,"createEvent","endDate");
		if(event.getRegistrationOpenDate()==null) throw new ServiceException(05,"createEvent","registrationOpenDate");
		if(event.getRegistrationCloseDate()==null) throw new ServiceException(05,"createEvent","registrationCloseDate");
    	eventDao.save(event);
    	return event;
	}

    @Transactional
	public void changeEventData(long sessionId, Event eventData) throws ServiceException {
    	SessionManager.checkPermissions(sessionId, "changeEventData");
    	try{
    		Event event = eventDao.find(eventData.getEventId());
    		if(eventData.getName()!=null) event.setName(eventData.getName());
    		if(eventData.getDescription()!=null) event.setDescription(eventData.getDescription());
    	    if(eventData.getNumParticipants()>0) event.setNumParticipants(eventData.getNumParticipants());
    		if(eventData.getStartDate()!=null) event.setStartDate(eventData.getStartDate());
    		if(eventData.getEndDate()!=null) event.setEndDate(eventData.getEndDate()); 
    		if(eventData.getRegistrationOpenDate()!=null) event.setRegistrationOpenDate(eventData.getRegistrationOpenDate());
    		if(eventData.getRegistrationCloseDate()!=null) event.setRegistrationCloseDate(eventData.getRegistrationCloseDate());
        	eventDao.save(event);
    	} catch (InstanceException e) {
			throw new  ServiceException(06,"changeEventData","Event");
		}	
	}

	@Transactional
	public void addParticipantToEvent(long sessionId, int userId, int eventId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "addParticipantToEvent");
    	try{
    		User user = userDao.find(userId);
    		Event event = eventDao.find(eventId);
    		if(registrationDao.geNumRegistrations(event.getEventId())>=event.getNumParticipants()) throw new ServiceException(8,"addParticipantToEvent");
    		if(event.getRegistrationOpenDate().compareTo(Calendar.getInstance())>0||event.getRegistrationCloseDate().compareTo(Calendar.getInstance())<0) throw new ServiceException(9,"addParticipantToEvent");
    		Registration registration = new Registration(user, event);
        	registrationDao.save(registration);
    	} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"addParticipantToEvent","User");
			else throw new  ServiceException(06,"addParticipantToEvent","Event");
		}	
		
	}

	@Transactional
	public void removeParticipantFromEvent(long sessionId, int userId, int eventId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "removeParticipantFromEvent");
    	Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
    	if (registration==null) throw new  ServiceException(06,"removeParticipantFromEvent","Registration");	
        try {
			registrationDao.remove(registration.getRegistrationId());
		} catch (InstanceException e) {
			 throw new  ServiceException(06,"removeParticipantFromEvent","Registration");
		}	
	}

	@Transactional
	public void changeRegistrationState(long sessionId, int userId, int eventId, RegistrationState state) throws ServiceException {
		if(state==null) throw new  ServiceException(05,"changeRegistrationState","state");
		SessionManager.checkPermissions(sessionId, "changeRegistrationState");
    	Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
    	if (registration==null) throw new  ServiceException(06,"changeRegistrationState","Registration");
    	if(state==RegistrationState.paid){
    		registration.setPaidDate(Calendar.getInstance());
    		registration.setPaid(true);
    	}
    	registration.setState(state);
		registrationDao.save(registration);
	}

    @Transactional(readOnly = true)
    public Event findEventByName(long sessionId, String name) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "findEventByName");
        Event event = eventDao.findEventByName(name);
        if (event == null) {
            throw new ServiceException(06,"findEventByName","Event");
        } else {
            return event;
        }
    }

    @Transactional
	public Activity createActivity(long sessionId, int eventId, Activity activity) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "createActivity");
		Event event;
		try {
				event = eventDao.find(eventId);
		} catch (InstanceException e) {
			throw new ServiceException(06,"createActivity","Event");
		}
		if(activity.getName()==null) throw new ServiceException(05,"createActivity","name");
		if(activity.getNumParticipants()<1) throw new ServiceException(04,"createActivity","numParticipants");
		if(activity.getNumParticipants()<1) throw new ServiceException(04,"createActivity","numParticipants");
		if(activity.getStartDate()==null) throw new ServiceException(05,"createActivity","startDate");
		if(activity.getStartDate().compareTo(event.getStartDate())<0) throw new ServiceException(04,"createActivity","startDate");
		if(activity.getEndDate()==null) throw new ServiceException(05,"createActivity","endDate");
		if(activity.getEndDate().compareTo(event.getEndDate())>0) throw new ServiceException(04,"createActivity","endDate");
		if(activity.getRegDateOpen()==null) throw new ServiceException(05,"createActivity","regDateOpen");
		if(activity.getRegDateClose()==null) throw new ServiceException(05,"createActivity","regDateClose");
		activity.setEvent(event);
		activityDao.save(activity);
		return activity;
	}
    
    @Transactional
	public void changeActivityData(long sessionId, Activity activityData) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "changeActivityData");
		try {
			Activity activity = activityDao.find(activityData.getActivityId());
			if(activityData.getDescription()!=null) activity.setDescription(activityData.getDescription());
			if(activityData.getName()!=null) activity.setName(activityData.getName());
			if(activityData.getDescription()!=null) activity.setDescription(activityData.getDescription());
			if(activityData.getNumParticipants()>0) activity.setNumParticipants(activityData.getNumParticipants());
			if(activityData.getType()!=null) activity.setType(activityData.getType());
			activity.setOficial(activityData.isOficial());
			if(activityData.getStartDate()!=null){
				if(activityData.getStartDate().compareTo(activity.getEvent().getStartDate())<0) throw new ServiceException(04,"changeActivityData","startDate");
				activity.setStartDate(activityData.getStartDate());
			}
			if(activityData.getEndDate()!=null){
				if(activityData.getEndDate().compareTo(activity.getEvent().getEndDate())>0) throw new ServiceException(04,"changeActivityData","endDate");
				activity.setEndDate(activityData.getEndDate());
			}
			if(activityData.getRegDateOpen()!=null) activity.setRegDateOpen(activityData.getRegDateOpen());
			if(activityData.getRegDateClose()!=null) activity.setRegDateOpen(activityData.getRegDateClose());
			activityDao.save(activity);
		} catch (InstanceException e) {
			throw new ServiceException(06,"changeActivityData","Activity");
		}	
	}
    
    @Transactional
	public void setActivityOrganizer(long sessionId, int activityId, int userId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "setActivityOrganizer");
		try {
			Activity activity = activityDao.find(activityId);
			User user = userDao.find(userId);
			activity.setOrganizer(user);
			activityDao.save(activity);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"setActivityOrganizer","User");
			else throw new  ServiceException(06,"setActivityOrganizer","Activity");

		}
			
	}

    @Transactional(readOnly = true)
	public List<Activity> getActivitiesByEvent(long sessionId, int eventId, ActivityType type) throws ServiceException {
		try {
			eventDao.find(eventId);
		} catch (InstanceException e) {
			throw new ServiceException(06,"getActivitiesByEvent","Event");
		}
		return activityDao.findActivitiesByEvent(eventId, type);
	}

    @Transactional
	public void addParticipantToActivity(long sessionId, int userId, int activityId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "addParticipantToActivity");
		try {
			Activity activity = activityDao.find(activityId);
			User user = userDao.find(userId);
			if (!activity.getParticipants().contains(user)) activity.getParticipants().add(user);
			activityDao.save(activity);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"addParticipantToActivity","User");
			else throw new  ServiceException(06,"addParticipantToActivity","Activity");

		}
		
	}

    @Transactional
	public void removeParticipantFormActivity(long sessionId, int userId, int activityId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "removeParticipantFormActivity");
		try {
			Activity activity = activityDao.find(activityId);
			User user = userDao.find(userId);
			if (activity.getParticipants().contains(user)) activity.getParticipants().remove(user);
			activityDao.save(activity);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"removeParticipantFormActivity","User");
			else throw new  ServiceException(06,"removeParticipantFormActivity","Activity");

		}
		
	}
    
    @Transactional(readOnly = true)
	public List<User> getActivityParticipants(long sessionId, int activityId) throws ServiceException {
    	SessionManager.checkPermissions(sessionId, " getActivityParticipants");
		try {
			Activity activity = activityDao.find(activityId);
			return activity.getParticipants();
		} catch (InstanceException e) {
			throw new  ServiceException(06,"getActivityParticipants","Activity");
		}
	}

    @Transactional
	public NewsItem addNews(long sessionId, int eventId, NewsItem newsItem) throws ServiceException {
    	SessionManager.checkPermissions(sessionId, "addNews");
		try {
			Event event = eventDao.find(eventId);
			if(newsItem.getTitle()==null) throw new ServiceException(05,"addNews","title");
			if(newsItem.getUrl()==null) throw new ServiceException(05,"addNews","url");
			if(newsItem.getPublishDate()==null) throw new ServiceException(05,"addNews","publishDate");
			newsItem.setEvent(event);
			newsItem.setPublisher(SessionManager.getSession(sessionId).getUser());
			newsItem.setCreationDate(Calendar.getInstance());
			newsDao.save(newsItem);
			return newsItem;
		} catch (InstanceException e) {
			throw new  ServiceException(06,"addNews","Event");
		}
	}

    @Transactional
	public void changeNewsData(long sessionId, NewsItem newsData) throws ServiceException {
    	SessionManager.checkPermissions(sessionId, "changeNewsItemData");
		try {
			NewsItem news = newsDao.find(newsData.getNewsItemId());
			if(newsData.getTitle()!=null) news.setTitle(newsData.getTitle());
			if(newsData.getPublishDate()!=null) news.setPublishDate(newsData.getPublishDate());
			if(newsData.getUrl()!=null) news.setUrl(newsData.getUrl());
			if(newsData.getPriorityHours()!=0) news.setPriorityHours(newsData.getPriorityHours());
			newsDao.save(news);
		} catch (InstanceException e) {
			throw new  ServiceException(06,"changeNewsData","NewsItem");
		}

	}

    @Transactional(readOnly = true)
	public List<NewsItem> getLastNews(long sessionId, Calendar dateLimit, boolean onlyOutstandingNews) throws ServiceException {
    	SessionManager.checkPermissions(sessionId, "getLastNews");
		return newsDao.getLastNews(sessionId, dateLimit, onlyOutstandingNews);
	}

    @Transactional
	public void removeNews(long sessionId, int newsItemId) throws ServiceException {
    	SessionManager.checkPermissions(sessionId, "removeNews");
    	try {
			newsDao.remove(newsItemId);
		} catch (InstanceException e) {
			throw new  ServiceException(06,"removeNews","NewsItem");
		}
		
	}
}

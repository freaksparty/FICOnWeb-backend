package es.ficonlan.web.backend.model.eventservice;

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.email.*;
import es.ficonlan.web.backend.model.activity.Activity;
import es.ficonlan.web.backend.model.activity.Activity.ActivityType;
import es.ficonlan.web.backend.model.activity.ActivityDao;
import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.event.EventDao;
import es.ficonlan.web.backend.model.newsitem.NewsDao;
import es.ficonlan.web.backend.model.newsitem.NewsItem;
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
 * @author Miguel Ángel Castillo Bellagona
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
	public Event createEvent(String sessionId, Event event) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "createEvent")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		if(event.getName()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"name");
		if(event.getStartDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"startDate");
		if(event.getEndDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"endDate");
		if(event.getRegistrationOpenDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"registrationOpenDate");
		if(event.getRegistrationCloseDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"registrationCloseDate");
		if(eventDao.findEventByName(event.getName())!=null) throw new ServiceException(ServiceException.DUPLICATED_FIELD,"name");
    	eventDao.save(event);
    	return event;
	}

    @Transactional
	public void changeEventData(String sessionId, Event eventData) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "changeEventData")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
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
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}	
	}

	@Transactional
	public void addParticipantToEvent(String sessionId, int userId, int eventId) throws ServiceException {
		if (!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "addParticipantToEvent")) throw new ServiceException(ServiceException.PERMISSION_DENIED);		
    	try{
    		User user = userDao.find(userId);
    		Event event = eventDao.find(eventId);    		
    		if(event.getRegistrationOpenDate().compareTo(Calendar.getInstance())>0||event.getRegistrationCloseDate().compareTo(Calendar.getInstance())<0) throw new ServiceException(9,"addParticipantToEvent");
    		Registration registration = new Registration(user, event);
    		int currentParticipants = registrationDao.geNumRegistrations(event.getEventId(),RegistrationState.registered) + 
    								  registrationDao.geNumRegistrations(event.getEventId(),RegistrationState.paid);
    		int queueParticipants = currentParticipants + registrationDao.geNumRegistrations(event.getEventId(),RegistrationState.inQueue);
    		if(currentParticipants>=event.getNumParticipants()) {
    			registration.setState(RegistrationState.inQueue);
    			//FIXME: Mandar correo electrónico
    			/*
    			Email mail = new EmailInQueue(user.getEmail(), event.getName(), 1 +  queueParticipants);
    			if(mail.sendMail()) System.out.println("Correo de InQueue enviado a " + user.getLogin() + " al correo " + user.getEmail());
    			else System.out.println("Error en envio de coreo de InQueue a " + user.getLogin() + " al correo " + user.getEmail());
    			*/
    		}
    		else if(user.isInBlackList()) {
    			registration.setState(RegistrationState.inQueue);
    			//FIXME: Mandar correo electrónico
    			/*
    			Email mail = new EmailInQueue(user.getEmail(), event.getName(), event.getNumParticipants() + 50);
    			if(mail.sendMail()) System.out.println("BLACKLIST : Correo de InQueue enviado a " + user.getLogin() + " al correo " + user.getEmail());
    			else System.out.println("BLACKLIST : Error en envio de coreo de InQueue a " + user.getLogin() + " al correo " + user.getEmail());
    		}
    		else registration.setState(RegistrationState.registered); {
    			registrationDao.save(registration);
    			Email mail = new EmailOutstanding(user.getEmail(), event.getName(), currentParticipants + 1);
    			if(mail.sendMail()) System.out.println("Correo de Outstanding enviado a " + user.getLogin() + " al correo " + user.getEmail());
    			else System.out.println("BLACKLIST : Error en envio de coreo de Outstanding a " + user.getLogin() + " al correo " + user.getEmail());
            	*/
            	//FIXME: Mandar correo electrónico
    		}
    		
    	} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}	
		
	}

	@Transactional
	public void removeParticipantFromEvent(String sessionId, int userId, int eventId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "removeParticipantFromEvent")) throw new ServiceException(ServiceException.PERMISSION_DENIED);	
    	Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
    	if (registration==null) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Registration");
    	Event event = registration.getEvent();
        try {
			registrationDao.remove(registration.getRegistrationId());
			//FIXME: MAndar correo elecrónico if registration.getState()==registered Mandar correo electrónico registration.User()
			if (registration.getState()==RegistrationState.registered) {
				/*
				Email mail = new EmailTimeToPayExceeded(registration.getUser().getEmail(), event.getName());
				if(mail.sendMail()) System.out.println("Correo de TimeToPayExceeded enviado a " + registration.getUser().getLogin() + " al correo " + registration.getUser().getEmail());
				else System.out.println("Error en envio de coreo de TimeToPayExceeded a " + registration.getUser().getLogin() + " al correo " + registration.getUser().getEmail());
	        */
			}
		} catch (InstanceException e) {
			 throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Registration");
		}
        Registration firstInQueue = registrationDao.getFirstInQueue(eventId);
        if(firstInQueue!=null){
        	firstInQueue.setState(RegistrationState.registered);
        	registrationDao.save(firstInQueue);
        	//FIXME: Mandar correo electrónico
        	/*
        	Email mail = new EmailOutstandingFromInQueue(registration.getUser().getEmail(), event.getName());
			if(mail.sendMail()) System.out.println("Correo de OutstandingFromInQueue enviado a " + firstInQueue.getUser().getLogin() + " al correo " + firstInQueue.getUser().getEmail());
			else System.out.println("Error en envio de coreo de OutstandingFromInQueue a " + firstInQueue.getUser().getLogin() + " al correo " + firstInQueue.getUser().getEmail());
        	*/
        }
	}
	
	@Transactional
	public void setPaid(String sessionId, int userId, int eventId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "setPaid")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
    	if (registration==null) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Registration");
    	registration.setPaidDate(Calendar.getInstance());
    	registration.setPaid(true);
    	registration.setState(RegistrationState.paid);
		registrationDao.save(registration);
    	//FIXME: Mandar correo electrónico
		/*
		Event event = registration.getEvent();
		Email mail = new EmailPaied(registration.getUser().getEmail(),  event.getName());
		if(mail.sendMail()) System.out.println("Correo de PaiedMail enviado a " + registration.getUser().getLogin() + " al correo " + registration.getUser().getEmail());
		else System.out.println("Error en envio de coreo de PaiedMail a " + registration.getUser().getLogin() + " al correo " + registration.getUser().getEmail());
	    */
	}

	/**
	 * This method doesn,t check constraints. And dont send e-mails
	 * Use only in exceptional circumstances. Otherways use "setPaid" method.
	 */
	@Transactional
	public void changeRegistrationState(String sessionId, int userId, int eventId, RegistrationState state) throws ServiceException {
		if(state==null) throw new  ServiceException(ServiceException.MISSING_FIELD,"state");
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "changeRegistrationState")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
    	if (registration==null) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Registration");
    	if(state==RegistrationState.paid){
    		registration.setPaidDate(Calendar.getInstance());
    		registration.setPaid(true);
    	}
    	registration.setState(state);
		registrationDao.save(registration);
	}

    @Transactional(readOnly = true)
    public List<Event> findEventByName(String sessionId, String name) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "findEventByName")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		return eventDao.searchEventsByName(name);
    }

    @Transactional
	public Activity addActivity(String sessionId, int eventId, Activity activity) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addActivity")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		Event event;
		try {
				event = eventDao.find(eventId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
		if(activity.getName()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"name");
		if(activity.getNumParticipants()<1) throw new ServiceException(ServiceException.INCORRECT_FIELD,"numParticipants");
		if(activity.getNumParticipants()<1) throw new ServiceException(ServiceException.INCORRECT_FIELD,"numParticipants");
		if(activity.getStartDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"startDate");
		if(activity.getStartDate().compareTo(event.getStartDate())<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"startDate");
		if(activity.getEndDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"endDate");
		if(activity.getEndDate().compareTo(event.getEndDate())>0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"endDate");
		if(activity.getRegDateOpen()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"regDateOpen");
		if(activity.getRegDateClose()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"regDateClose");
		activity.setEvent(event);
		activityDao.save(activity);
		return activity;
	}
    

    @Transactional
	public void removeActivity(String sessionId, int activityId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removeActivity")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			activityDao.remove(activityId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Activity");
		}
	}
    
    @Transactional
	public void changeActivityData(String sessionId, Activity activityData) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "changeActivityData")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Activity activity = activityDao.find(activityData.getActivityId());
			if(activityData.getDescription()!=null) activity.setDescription(activityData.getDescription());
			if(activityData.getName()!=null) activity.setName(activityData.getName());
			if(activityData.getDescription()!=null) activity.setDescription(activityData.getDescription());
			if(activityData.getNumParticipants()>0) activity.setNumParticipants(activityData.getNumParticipants());
			if(activityData.getType()!=null) activity.setType(activityData.getType());
			activity.setOficial(activityData.isOficial());
			if(activityData.getStartDate()!=null){
				if(activityData.getStartDate().compareTo(activity.getEvent().getStartDate())<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"startDate");
				activity.setStartDate(activityData.getStartDate());
			}
			if(activityData.getEndDate()!=null){
				if(activityData.getEndDate().compareTo(activity.getEvent().getEndDate())>0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"endDate");
				activity.setEndDate(activityData.getEndDate());
			}
			if(activityData.getRegDateOpen()!=null) activity.setRegDateOpen(activityData.getRegDateOpen());
			if(activityData.getRegDateClose()!=null) activity.setRegDateClose(activityData.getRegDateClose());
			activityDao.save(activity);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Activity");
		}	
	}
    
    @Transactional
	public void setActivityOrganizer(String sessionId, int activityId, int userId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "setActivityOrganizer")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Activity activity = activityDao.find(activityId);
			User user = userDao.find(userId);
			if(registrationDao.findByUserAndEvent(user.getUserId(), activity.getEvent().getEventId())==null) throw new ServiceException(ServiceException.USER_NOT_REGISTERED_IN_EVENT,"userId");
			activity.setOrganizer(user);
			activityDao.save(activity);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Activity");

		}
			
	}

    @Transactional(readOnly = true)
	public List<Activity> getActivitiesByEvent(String sessionId, int eventId, ActivityType type) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getActivitiesByEvent")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	try {
			eventDao.find(eventId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
		return activityDao.findActivitiesByEvent(eventId, type);
	}

    @Transactional
	public void addParticipantToActivity(String sessionId, int userId, int activityId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "addParticipantToActivity")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Activity activity = activityDao.find(activityId);
			User user = userDao.find(userId);
			if(registrationDao.findByUserAndEvent(user.getUserId(), activity.getEvent().getEventId())==null) throw new ServiceException(ServiceException.USER_NOT_REGISTERED_IN_EVENT,"userId");
			if(activity.getParticipants().size()>=activity.getNumParticipants()) throw new ServiceException(ServiceException.MAX_NUM_PARTICIPANTS_REACHED,"Activity(Id="+activity.getActivityId()+")");
			if (!activity.getParticipants().contains(user)) activity.getParticipants().add(user);
			activityDao.save(activity);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Activity");

		}
		
	}

    @Transactional
	public void removeParticipantFromActivity(String sessionId, int userId, int activityId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "removeParticipantFromActivity")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Activity activity = activityDao.find(activityId);
			User user = userDao.find(userId);
			if (activity.getParticipants().contains(user)) activity.getParticipants().remove(user);
			activityDao.save(activity);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Activity");

		}
		
	}
    
    @Transactional(readOnly = true)
	public List<User> getActivityParticipants(String sessionId, int activityId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getActivityParticipants")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	return activityDao.getParticipants(activityId);
	}

    @Transactional
	public NewsItem addNews(String sessionId, int eventId, NewsItem newsItem) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addNews")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Event event = eventDao.find(eventId);
			if(newsItem.getTitle()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"title");
			if(newsItem.getUrl()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"url");
			if(newsItem.getPublishDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"publishDate");
			newsItem.setEvent(event);
			newsItem.setPublisher(SessionManager.getSession(sessionId).getUser());
			newsItem.setCreationDate(Calendar.getInstance());
			newsDao.save(newsItem);
			return newsItem;
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
	}

    @Transactional
	public void changeNewsData(String sessionId, NewsItem newsData) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "changeNewsData")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			NewsItem news = newsDao.find(newsData.getNewsItemId());
			if(newsData.getTitle()!=null) news.setTitle(newsData.getTitle());
			if(newsData.getPublishDate()!=null) news.setPublishDate(newsData.getPublishDate());
			if(newsData.getUrl()!=null) news.setUrl(newsData.getUrl());
			if(newsData.getPriorityHours()!=0) news.setPriorityHours(newsData.getPriorityHours());
			newsDao.save(news);
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"NewsItem");
		}

	}

    @Transactional(readOnly = true)
	public List<NewsItem> getLastNews(String sessionId, Calendar dateLimit, boolean onlyOutstandingNews) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getLastNews")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	if(dateLimit==null) throw new ServiceException(ServiceException.MISSING_FIELD,"limitDate");
		return newsDao.getLastNews(dateLimit, onlyOutstandingNews);
	}

    @Transactional
	public void removeNews(String sessionId, int newsItemId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removeNews")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	try {
			newsDao.remove(newsItemId);
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"NewsItem");
		}
	}
}

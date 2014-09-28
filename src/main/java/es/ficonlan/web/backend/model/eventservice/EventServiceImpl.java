package es.ficonlan.web.backend.model.eventservice;

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.email.Email;
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
	public Event createEvent(long sessionId, Event event) throws ServiceException {
    	SessionManager.checkPermissions(sessionId, "createEvent");
		if(event.getName()==null) throw new ServiceException(05,"createEvent","name");
		if(event.getStartDate()==null) throw new ServiceException(05,"createEvent","startDate");
		if(event.getEndDate()==null) throw new ServiceException(05,"createEvent","endDate");
		if(event.getRegistrationOpenDate()==null) throw new ServiceException(05,"createEvent","registrationOpenDate");
		if(event.getRegistrationCloseDate()==null) throw new ServiceException(05,"createEvent","registrationCloseDate");
		if(eventDao.findEventByName(event.getName())!=null) throw new ServiceException(03,"createEvent","name");
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
		//FIXME: Añadido nuevo permiso
		try{
			SessionManager.checkPermissions(sessionId, "addParticipantToEvent");
		}
		catch(ServiceException e){
			SessionManager.checkPermissions(sessionId, userId, "USERaddParticipantToEvent");
		}
		
    	try{
    		User user = userDao.find(userId);
    		Event event = eventDao.find(eventId);    		
    		if(event.getRegistrationOpenDate().compareTo(Calendar.getInstance())>0||event.getRegistrationCloseDate().compareTo(Calendar.getInstance())<0) throw new ServiceException(9,"addParticipantToEvent");
    		Registration registration = new Registration(user, event);
    		int currentParticipants = registrationDao.geNumRegistrations(event.getEventId(),RegistrationState.registered) + 
    								  registrationDao.geNumRegistrations(event.getEventId(),RegistrationState.paid);
    		if(currentParticipants>=event.getNumParticipants()) {
    			registration.setState(RegistrationState.inQueue);
    			//FIXME: Mandar correo electrónico
    			Email mail = new Email("mail/mail.properties", "mail/InQueue.properties",user.getEmail());
    			if(mail.sendMail()) System.out.println("Correo de InQueue enviado a " + user.getLogin() + " al correo " + user.getEmail());
    			else System.out.println("Error en envio de coreo de InQueue a " + user.getLogin() + " al correo " + user.getEmail());
    		}
    		else if(user.isInBlackList()) {
    			registration.setState(RegistrationState.inQueue);
    			//FIXME: Mandar correo electrónico
    			Email mail = new Email("mail/mail.properties", "mail/InQueue.properties",user.getEmail());
    			if(mail.sendMail()) System.out.println("BLACKLIST : Correo de InQueue enviado a " + user.getLogin() + " al correo " + user.getEmail());
    			else System.out.println("BLACKLIST : Error en envio de coreo de InQueue a " + user.getLogin() + " al correo " + user.getEmail());
    		}
    		else registration.setState(RegistrationState.registered); {
    			registrationDao.save(registration);
    			Email mail = new Email("mail/mail.properties", "mail/Outstanding.properties",user.getEmail());
    			if(mail.sendMail()) System.out.println("Correo de Outstanding enviado a " + user.getLogin() + " al correo " + user.getEmail());
    			else System.out.println("BLACKLIST : Error en envio de coreo de Outstanding a " + user.getLogin() + " al correo " + user.getEmail());
            	//FIXME: Mandar correo electrónico
    		}
    		
    	} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"addParticipantToEvent","User");
			else throw new  ServiceException(06,"addParticipantToEvent","Event");
		}	
		
	}

	@Transactional
	public void removeParticipantFromEvent(long sessionId, int userId, int eventId) throws ServiceException {
		//FIXME: Añadido nuevo permiso
		try{
			SessionManager.checkPermissions(sessionId, "removeParticipantFromEvent");
		}
		catch(ServiceException e){
			SessionManager.checkPermissions(sessionId, userId, "USERremoveParticipantFromEvent");
		}
    	Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
    	if (registration==null) throw new  ServiceException(06,"removeParticipantFromEvent","Registration");	
        try {
			registrationDao.remove(registration.getRegistrationId());
			//FIXME: MAndar correo elecrónico if registration.getState()==registered Mandar correo electrónico registration.User()
			if (registration.getState()==RegistrationState.registered) {
				Email mail = new Email("mail/mail.properties", "mail/TimeToPayExceeded.properties",registration.getUser().getEmail());
				if(mail.sendMail()) System.out.println("Correo de TimeToPayExceeded enviado a " + registration.getUser().getLogin() + " al correo " + registration.getUser().getEmail());
				else System.out.println("Error en envio de coreo de TimeToPayExceeded a " + registration.getUser().getLogin() + " al correo " + registration.getUser().getEmail());
	        
			}
		} catch (InstanceException e) {
			 throw new  ServiceException(06,"removeParticipantFromEvent","Registration");
		}
        Registration firstInQueue = registrationDao.getFirstInQueue(eventId);
        if(firstInQueue!=null){
        	firstInQueue.setState(RegistrationState.registered);
        	registrationDao.save(firstInQueue);
        	//FIXME: Mandar correo electrónico
        	Email mail = new Email("mail/mail.properties", "mail/OutstandingFromInQueue.properties",firstInQueue.getUser().getEmail());
			if(mail.sendMail()) System.out.println("Correo de OutstandingFromInQueue enviado a " + firstInQueue.getUser().getLogin() + " al correo " + firstInQueue.getUser().getEmail());
			else System.out.println("Error en envio de coreo de OutstandingFromInQueue a " + firstInQueue.getUser().getLogin() + " al correo " + firstInQueue.getUser().getEmail());
        	
        }
	}
	
	@Transactional
	public void setPaid(long sessionId, int userId, int eventId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "setPaid");
		Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
    	if (registration==null) throw new  ServiceException(06,"changeRegistrationState","Registration");
    	registration.setPaidDate(Calendar.getInstance());
    	registration.setPaid(true);
    	registration.setState(RegistrationState.paid);
		registrationDao.save(registration);
    	//FIXME: Mandar correo electrónico
		Email mail = new Email("mail/mail.properties", "mail/PaiedMail.properties",registration.getUser().getEmail());
		if(mail.sendMail()) System.out.println("Correo de PaiedMail enviado a " + registration.getUser().getLogin() + " al correo " + registration.getUser().getEmail());
		else System.out.println("Error en envio de coreo de PaiedMail a " + registration.getUser().getLogin() + " al correo " + registration.getUser().getEmail());
	}

	/**
	 * This method doesn,t check constraints. And dont send e-mails
	 * Use only in exceptional circumstances. Otherways use "setPaid" method.
	 */
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
    public List<Event> findEventByName(long sessionId, String name) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "findEventByName");
		return eventDao.searchEventsByName(name);
    }

    @Transactional
	public Activity addActivity(long sessionId, int eventId, Activity activity) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "addActivity");
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
	public void removeActivity(long sessionId, int activityId) throws ServiceException {
    	SessionManager.checkPermissions(sessionId, "removeActivity");
		try {
			activityDao.remove(activityId);
		} catch (InstanceException e) {
			throw new ServiceException(06,"removeActivity","Activity");
		}
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
			if(activityData.getRegDateClose()!=null) activity.setRegDateClose(activityData.getRegDateClose());
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
			if(registrationDao.findByUserAndEvent(user.getUserId(), activity.getEvent().getEventId())==null) throw new ServiceException(10,"setActivityOrganizer","userId");
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
			if(registrationDao.findByUserAndEvent(user.getUserId(), activity.getEvent().getEventId())==null) throw new ServiceException(10,"addParticipantToActivity","userId");
			if(activity.getParticipants().size()>=activity.getNumParticipants()) throw new ServiceException(11,"addParticipantToActivity","Activity(Id="+activity.getActivityId()+")");
			if (!activity.getParticipants().contains(user)) activity.getParticipants().add(user);
			activityDao.save(activity);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"addParticipantToActivity","User");
			else throw new  ServiceException(06,"addParticipantToActivity","Activity");

		}
		
	}

    @Transactional
	public void removeParticipantFromActivity(long sessionId, int userId, int activityId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "removeParticipantFromActivity");
		try {
			Activity activity = activityDao.find(activityId);
			User user = userDao.find(userId);
			if (activity.getParticipants().contains(user)) activity.getParticipants().remove(user);
			activityDao.save(activity);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"removeParticipantFromActivity","User");
			else throw new  ServiceException(06,"removeParticipantFromActivity","Activity");

		}
		
	}
    
    @Transactional(readOnly = true)
	public List<User> getActivityParticipants(long sessionId, int activityId) throws ServiceException {
    	SessionManager.checkPermissions(sessionId, "getActivityParticipants");
    	return activityDao.getParticipants(activityId);
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
    	SessionManager.checkPermissions(sessionId, "changeNewsData");
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
    	if(dateLimit==null) throw new ServiceException(05,"getLastNews","limitDate");
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

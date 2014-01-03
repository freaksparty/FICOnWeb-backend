package es.ficonlan.web.backend.model.eventservice;

import java.util.Calendar;

import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.event.EventDao;
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
    	    if(eventData.getNumParticipants()!=0) event.setNumParticipants(eventData.getNumParticipants());
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

	

}

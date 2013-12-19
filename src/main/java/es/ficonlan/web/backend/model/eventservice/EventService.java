package es.ficonlan.web.backend.model.eventservice;

import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
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
    
}

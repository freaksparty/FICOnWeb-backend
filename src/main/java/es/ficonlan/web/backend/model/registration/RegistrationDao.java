package es.ficonlan.web.backend.model.registration;

import java.util.Calendar;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.util.dao.GenericDao;

/**
 * @author Daniel Gómez Silva
 */
public interface RegistrationDao extends GenericDao<Registration, Integer> {
	
	public Registration findByUserAndEvent(int userId, int eventId);
	
	public Registration getFirstInQueue(int eventId);
	
	public int geNumRegistrations(int eventId, RegistrationState state);
	
	public int geNumRegistrationsBeforeDate(int eventId, RegistrationState state, Calendar date);
	
}

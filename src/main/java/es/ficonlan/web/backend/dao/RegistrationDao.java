package es.ficonlan.web.backend.dao;

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.entities.Registration;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;
import es.ficonlan.web.backend.output.ShirtData;

/**
 * @author Daniel Gómez Silva
 */
public interface RegistrationDao extends GenericDao<Registration, Integer> {
	
	public Registration findByUserAndEvent(int userId, int eventId);
	
	public Registration getFirstInQueue(int eventId);
	
	public int geNumRegistrations(int eventId, RegistrationState state);
	
	public int geNumRegistrationsBeforeDate(int eventId, RegistrationState state, Calendar date);
	
	public List<Registration> getRegistrationByEvent(int eventId, RegistrationState state, int startindex, int maxResults, String orderBy, boolean desc);
	
	public long getRegistrationByEventTAM(int eventId, RegistrationState state);
	
	public List<ShirtData> getShirtSizesPaid(int eventId);
	
}

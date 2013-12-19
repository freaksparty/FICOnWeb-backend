package es.ficonlan.web.backend.model.registration;

import es.ficonlan.web.backend.model.util.dao.GenericDao;

/**
 * @author Daniel Gómez Silva
 */
public interface RegistrationDao extends GenericDao<Registration, Integer> {
	
	public Registration findByUserAndEvent(int userId, int eventId);
	
}

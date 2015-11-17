package es.ficonlan.web.backend.dao;

import java.util.List;

import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
public interface UserDao extends GenericDao<User,Integer> {
	
	public List<User> getAllUsers(int startindex, int maxResults, String orderBy, boolean desc);
	
	public long getAllUsersTAM();
	
	public User findUserBylogin(String login);
	
	public User findUserByDni(String dni);
	
	public User findUserByEmail(String email);

	public List<User> getUsersByEvent(int eventId, RegistrationState state, int startindex, int maxResults, String orderBy, boolean desc);
	
	public long getUsersByEventTAM(int eventId, RegistrationState state);

	public List<User> findUsersByName(String name, int startindex, int maxResults);

	public List<User> getBlacklistedUsers(int startIndex, int maxResults);

}

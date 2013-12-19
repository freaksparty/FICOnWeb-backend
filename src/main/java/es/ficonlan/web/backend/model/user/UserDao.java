package es.ficonlan.web.backend.model.user;

import java.util.List;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.util.dao.GenericDao;

/**
 * @author Daniel Gómez Silva
 */
public interface UserDao extends GenericDao<User,Integer> {
	
	public List<User> getAllUsers();
	
	public User findUserBylogin(String login);
	
	public User findUserByDni(String dni);

	public List<User> getUsersByEvet(int eventId, RegistrationState state);

}

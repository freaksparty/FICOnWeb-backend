package es.ficonlan.web.backend.model.userservice;

import java.util.List;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
public interface UserService {

	public User addUser(long sessionId, String name, String login, String password, String dni, String email, String phoneNumber, int shirtSize) throws ServiceException;
	
	public Session login (String login, String password, boolean passwordEncripted)  throws ServiceException;
	
	public void closeSession(long sessionId)  throws ServiceException;
	
	public void changeUserData(long sessionId, int UserId,  String name, String dni, String email, String phoneNumber, int shirtSize)  throws ServiceException;
	
	public void changeUserPassword(long sessionId, int userId, String oldPassword, String newPassword)  throws ServiceException;
	
	public List<User> getUsersByEvet(long sessionId, int eventId, RegistrationState state)  throws ServiceException;
	
	public List<User> getAllUsers(long sessionId)  throws ServiceException;
	
}

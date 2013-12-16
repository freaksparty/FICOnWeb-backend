package es.ficonlan.web.backend.model.userservice;

import java.util.List;
import java.util.Set;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.usecase.UseCase;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
public interface UserService {
	
	public Session newAnonymousSession();

	public User addUser(long sessionId, User user) throws ServiceException;
	
	public Session login (long sessionId, String login, String password)  throws ServiceException;
	
	public void closeSession(long sessionId)  throws ServiceException;
	
	public void changeUserData(long sessionId, User user)  throws ServiceException;
	
	public void changeUserPassword(long sessionId, int userId, String oldPassword, String newPassword)  throws ServiceException;
	
	public List<User> getUsersByEvet(long sessionId, int eventId, RegistrationState state)  throws ServiceException;
	
	public List<User> getAllUsers(long sessionId)  throws ServiceException;
	
	public void setDefaultLanguage(long sessionId, int userId, int languageId) throws ServiceException;
	
	public void addUserToBlackList(long sessionId, int userId) throws ServiceException;
	
	public void removeUserFromBlackList(long sessionId, int userId) throws ServiceException;
	
	public void removeUser(long sessionId, int userId) throws ServiceException;
	
	public Role createRole(long sessionId, String roleName) throws ServiceException;
	
	public void addRole(long sessionId, int roleId, int userId) throws ServiceException;
	
	public void removeRole(long sessionId, int roleId, int userId) throws ServiceException;
	
	public Set<Role> getUserRoles(long sessionId, int userId) throws ServiceException;
	
	public UseCase createUseCase(long sessionId, String useCaseName) throws ServiceException;
	
	public void addUseCase(long sessionId, int roleId, int useCaseId) throws ServiceException;
	
	public void removeUseCase(long sessionId, int roleId, int useCaseId) throws ServiceException;
	
	public Set<UseCase> getRolePermissions(long sessionId, int roleId) throws ServiceException;
	
}

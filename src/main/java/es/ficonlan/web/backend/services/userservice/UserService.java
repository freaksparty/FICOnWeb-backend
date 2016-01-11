package es.ficonlan.web.backend.services.userservice;

import java.util.List;
import java.util.Set;

import es.ficonlan.web.backend.entities.Registration.RegistrationState;
import es.ficonlan.web.backend.entities.Role;
import es.ficonlan.web.backend.entities.UseCase;
import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.Session;

/**
 * @author Daniel Gómez Silva
 */
public interface UserService {
	
	public void initialize();
	
	public Session newAnonymousSession();

	public User addUser(String sessionId, User user) throws ServiceException;
	
	public Session login (String login, String password)  throws ServiceException;
	
	public void closeAllUserSessions(String sessionId, int userId) throws ServiceException;
	
	public User getCurrenUser(String sessionId) throws ServiceException;
	
	public void closeSession(String sessionId)  throws ServiceException;
	
	public void changeUserData(String sessionId, int userId, User userData)  throws ServiceException;
	
	public void changeUserPassword(String sessionId, int userId, String oldPassword, String newPassword)  throws ServiceException;
	
	public boolean passwordRecover(String sessionId, String email) throws ServiceException;
	
	public List<User> getUsersByEvent(int eventId, RegistrationState state, int startIndex, int maxResults, String orderBy, boolean desc)  throws ServiceException;
    
	public long getUsersByEventTAM(int eventId, RegistrationState state) throws ServiceException;
	
	public List<User> getAllUsers(int startIndex, int maxResults, String orderBy, boolean desc) throws ServiceException;
	
	public long getAllUsersTAM() throws ServiceException;
	
	public List<User> findUsersByName(String sessionId, String name, int startIndex, int maxResults)  throws ServiceException;
		
	public void addUserToBlackList(String sessionId, int userId) throws ServiceException;
	
	public void removeUserFromBlackList(String sessionId, int userId) throws ServiceException;
	
	public List<User> getBlacklistedUsers(String sessionId, int startIndex, int maxResults)  throws ServiceException;
	
	public void setDefaultLanguage(String sessionId, int userId, int languageId) throws ServiceException;
	
	public void removeUser(String sessionId, int userId) throws ServiceException;
	
	public Role createRole(String sessionId, String rolename) throws ServiceException;
	
	public void removeRole(String sessionId, int roleid) throws ServiceException;
	
	public void addRole(String sessionId, int roleId, int userId) throws ServiceException;
	
	public void removeRole(String sessionId, int roleId, int userId) throws ServiceException;
	
	public Set<Role> getUserRoles(String sessionId, int userId) throws ServiceException;
	
	public List<Role> getAllRoles(String sessionId) throws ServiceException;
	
	public UseCase createUseCase(String sessionId, String useCaseName) throws ServiceException;
	
	public void addPermission(String sessionId, int roleId, int useCaseId) throws ServiceException;
	
	public void removePermission(String sessionId, int roleId, int useCaseId) throws ServiceException;
	
	public Set<UseCase> getRolePermissions(String sessionId, int roleId) throws ServiceException;
	
	public List<UseCase> getAllUseCases(String sessionId) throws ServiceException;

	
		
}

package es.ficonlan.web.backend.model.userservice;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.role.RoleDao;
import es.ficonlan.web.backend.model.supportedlanguage.SupportedLanguage;
import es.ficonlan.web.backend.model.supportedlanguage.SupportedLanguageDao;
import es.ficonlan.web.backend.model.usecase.UseCase;
import es.ficonlan.web.backend.model.usecase.UseCaseDao;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.user.UserDao;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.Session;
import es.ficonlan.web.backend.model.util.session.SessionManager;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {
		
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UseCaseDao useCaseDao;
	
	@Autowired
	private SupportedLanguageDao languageDao;

	/**
	 * If the target user of the operation is the session owner -> Operation allowed<br>
	 * If the target user of the operation isn't the session owner -> Check permissions
	 * 
	 * @param sessionId
	 * @param userId Id of the target user of the operation.
	 * @param useCase
	 * @throws ServiceException
	 */
	
	@Transactional
	public Session newAnonymousSession() {
		User user = userDao.findUserBylogin("anonymous");
		if (user == null){
			user = new User("-", "anonymous", "-", "-", "-", "-", "_");
			Role role = new Role("Anonymous");
	    	UseCase addUser = new UseCase("addUser");
	    	role.getUseCases().add(addUser);
	    	user.getRoles().add(role);
	    	useCaseDao.save(addUser);
	    	roleDao.save(role);
	    	userDao.save(user);
		}
		Session s = new Session(user);
		SessionManager.addSession(s);
		return s;
	}
	
	@Transactional
	public User addUser(long sessionId, User user) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "addUser");
		User u = userDao.findUserBylogin(user.getLogin());
		if (u != null) throw new ServiceException(03,"addUser","login");
		u = userDao.findUserByDni(user.getDni());
		if (u != null) throw new ServiceException(03,"addUser","dni");
		if(user.getLogin()==null) throw new ServiceException(05,"addUser","login");
		if(user.getPassword()==null) throw new ServiceException(05,"addUser","password");
		if(user.getName()==null) throw new ServiceException(05,"addUser","name");
		if(user.getDni()==null) throw new ServiceException(05,"addUser","dni");
		if(user.getEmail()==null) throw new ServiceException(05,"addUser","email");
		userDao.save(user);
		return user;
		
	}
	
	@Transactional(readOnly=true)
	public Session login(long sessionId, String login, String password) throws ServiceException {
		if (!SessionManager.exists(sessionId)) throw new ServiceException(01,"login");
		Session session = SessionManager.getSession(sessionId);
		if(login.contentEquals("anonymous")) return session;
		if(!session.getUser().getLogin().contentEquals("anonymous")) throw new ServiceException(07,"login");
		User user = userDao.findUserBylogin(login);
		if (user == null) throw new ServiceException(04,"login","login");
		else if (!password.contentEquals(user.getPassword()))  throw new ServiceException(04,"login","password");
		session.setUser(user);
		return session;
	}

	public void closeSession(long sessionId) throws ServiceException {
		if (!SessionManager.exists(sessionId)) throw new ServiceException(01,"closeSession");
		SessionManager.removeSession(sessionId);
	}

	/**
	 * DNI field can't be changed by the user, only by an admin; 
	 */
	@Transactional
	public void changeUserData(long sessionId, User userData) throws ServiceException {
		SessionManager.checkPermissions(sessionId, userData.getUserId(), "changeUserData");
		try {
			User user = userDao.find(userData.getUserId());
			user.setName(userData.getName());
			if(SessionManager.getSession(sessionId).getUser().getUserId()!=userData.getUserId()) user.setDni(userData.getDni());
			user.setEmail(userData.getEmail());
			user.setPhoneNumber(userData.getPhoneNumber());
			user.setShirtSize(userData.getShirtSize());
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(06,"changeUserData","User");
		}
	}

	/**
	 * Password changed by the user -> Old password required.<br> 
	 * Password changed by an admin -> Old password not required.
	 */
	@Transactional
	public void changeUserPassword(long sessionId, int userId, String oldPassword, String newPassword) throws ServiceException {
		SessionManager.checkPermissions(sessionId, userId, "changeUserPassword");
		Session session = SessionManager.getSession(sessionId);
		try {
			User user = userDao.find(userId);
			if(session.getUser().getUserId() == userId && !oldPassword.contentEquals(user.getPassword()))  throw new ServiceException(04,"changeUserPassword","password"); 
			user.setPassword(newPassword);
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(06,"changeUserPassword","User");
		}	
	}

	@Transactional(readOnly=true)
	public List<User> getUsersByEvet(long sessionId, int eventId, RegistrationState state) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "getUsersByEvet");
		return userDao.getUsersByEvet(eventId,state);
	}
	
	@Transactional(readOnly=true)
	public List<User> getAllUsers(long sessionId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "getAllUsers");
		return userDao.getAllUsers();
	}

	@Transactional
	public void setDefaultLanguage(long sessionId, int userId, int languageId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, userId, "setDefaultLanguage");
		try {
			User user = userDao.find(userId);
			SupportedLanguage language = languageDao.find(languageId);
			user.setDefaultLanguage(language);
			userDao.save(user);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"setDefaultLanguage","User");
			else throw new ServiceException(06,"setDefaultLanguage","Language");
		}	
	}

	@Transactional
	public void addUserToBlackList(long sessionId, int userId) throws ServiceException{
		SessionManager.checkPermissions(sessionId, "addUserToBlackList");
		try {
			User user = userDao.find(userId);
			user.setInBlackList(true);
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(06,"addUserToBlackList","User");
		}	
	}

	@Transactional
	public void removeUserFromBlackList(long sessionId, int userId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "removeUserFromBlackList");
		try {
			User user = userDao.find(userId);
			user.setInBlackList(false);
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(06,"removeUserFromBlackList","User");
		}
	}

	@Transactional
	public void removeUser(long sessionId, int userId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "removeUser");
		try {
			User user = userDao.find(userId);
			user.setDeleted(true);
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(06,"removeUser","User");
		}
	}

	@Transactional
	public Role createRole(long sessionId, String roleName) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "createRole");
		Role role = new Role(roleName);
		roleDao.save(role);
		return role;
	}

	@Transactional
	public void addRole(long sessionId, int roleId, int userId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "addRole");
		try {
			User user = userDao.find(userId);
			Role role = roleDao.find(roleId);
			user.getRoles().add(role);
			userDao.save(user);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"addRole","User");
			else throw new ServiceException(06,"addRole","Role");
		}
	}

	@Transactional
	public void removeRole(long sessionId, int roleId, int userId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "removeRole");
		try {
			User user = userDao.find(userId);
			Role role = roleDao.find(roleId);
			user.getRoles().remove(role);
			userDao.save(user);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"removeRole","User");
			else throw new  ServiceException(06,"removeRole","Role");
		}
		
	}
	
	@Transactional(readOnly=true)
	public Set<Role> getUserRoles(long sessionId, int userId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "getUserRoles");
		try {
			User user = userDao.find(userId);
			return user.getRoles();
		} catch (InstanceException e) {
			throw new  ServiceException(06,"getUserRoles","User");
		}
	}

	@Transactional
	public UseCase createUseCase(long sessionId, String useCaseName) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "createUseCase");
		UseCase useCase = new UseCase(useCaseName);
		useCaseDao.save(useCase);
		return useCase;
	}

	@Transactional
	public void addUseCase(long sessionId, int roleId, int useCaseId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "addUseCase");
		try {
			Role role = roleDao.find(roleId);
			UseCase useCase = useCaseDao.find(useCaseId);
			role.getUseCases().add(useCase);
			roleDao.save(role);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("UseCase")) throw new  ServiceException(06,"addUseCase","UseCase");
			else throw new  ServiceException(06,"addUseCase","Role");
		}
		
	}

	@Transactional
	public void removeUseCase(long sessionId, int roleId, int useCaseId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "removeUseCase");
		try {
			Role role = roleDao.find(roleId);
			UseCase useCase = useCaseDao.find(useCaseId);
			role.getUseCases().remove(useCase);
			roleDao.save(role);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("UseCase")) throw new  ServiceException(06,"removeUseCase","UseCase");
			else throw new  ServiceException(06,"removeUseCase","Role");
		}
		
	}

	@Transactional(readOnly=true)	
	public Set<UseCase> getRolePermissions(long sessionId, int roleId) throws ServiceException {
		SessionManager.checkPermissions(sessionId, "getRolePermissions");
		try {
			Role role = roleDao.find(roleId);
			return role.getUseCases();
		} catch (InstanceException e) {
			throw new  ServiceException(06,"getRolePermissions","Role");
		}
	}	
	
}

package es.ficonlan.web.backend.model.userservice;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.role.RoleDao;
import es.ficonlan.web.backend.model.useCase.UseCase;
import es.ficonlan.web.backend.model.useCase.UseCaseDao;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.user.UserDao;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {
	
	private static ConcurrentHashMap<Long, Session> openSessions = new ConcurrentHashMap<Long, Session>();
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UseCaseDao useCaseDao;

	/**
	 * If the target user of the operation is the session owner -> Operation allowed<br>
	 * If the target user of the operation isn't the session owner -> Check permissions
	 * 
	 * @param sessionId
	 * @param userId Id of the target user of the operation.
	 * @param useCase
	 * @throws ServiceException
	 */
	private void checkPermissions(long sessionId, int userId, String useCase) throws ServiceException{
		if (!openSessions.containsKey(sessionId)) throw new ServiceException(01,useCase,"Invalid session");
		Session session = openSessions.get(sessionId);
		if (session.getUser().getUserId() != userId) checkPermissions(session, useCase);
	}
	
	private void checkPermissions(long sessionId, String useCase) throws ServiceException {
		if (!openSessions.containsKey(sessionId)) throw new ServiceException(01,useCase,"Invalid session");
		checkPermissions(openSessions.get(sessionId), useCase);	
	}
	
	private void checkPermissions(Session session, String useCase) throws ServiceException{
		for(Role r:session.getUser().getRoles()){
		    for (UseCase uc: r.getUseCases()){
		    	if (uc.getUserCaseName().contentEquals(useCase)) return;
		    }
		}
		throw new ServiceException(02, useCase,"Permission denied");	
	}
	
	@Transactional
	public User addUser(long sessionId, String name, String login, String password, String dni, String email, String phoneNumber, int shirtSize) throws ServiceException {
		checkPermissions(sessionId, "addUser");
		User user = userDao.findUserBylogin(login);
		if (user != null){
			throw new ServiceException(03,"addUser","Duplicted login");
		} else {
			User u = new User(name, login, password, dni, email, phoneNumber, shirtSize);
			userDao.save(u);
			return u;
		}
	}
	
	@Transactional(readOnly=true)
	public Session login(String login, String password, boolean passwordEncripted) throws ServiceException {
		//TODO: Encriptado de passwords
		User user = userDao.findUserBylogin(login);
		if (user == null) throw new ServiceException(04,"login","Incorrect login");
		else if (!password.contentEquals(user.getPassword()))  throw new ServiceException(05,"login","Incorrect password");
		Session session = new Session(user);
		openSessions.put(session.getSessionId(), session);
		return session;
	}

	public void closeSession(long sessionId) throws ServiceException {
		if (!openSessions.containsKey(sessionId)) throw new ServiceException(01,"closeSession","Invalid session");
		openSessions.remove(sessionId);
	}

	@Transactional
	public void changeUserData(long sessionId, int userId, String name, String dni, String email, String phoneNumber, int shirtSize) throws ServiceException {
		checkPermissions(sessionId, userId, "changeUserData");
		try {
			User user = userDao.find(userId);
			user.setName(name);
			user.setDni(dni);
			user.setEmail(email);
			user.setPhoneNumber(phoneNumber);
			user.setSirtSize(shirtSize);
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(06,"changeUserData","User Not Found");
		}
	}

	/**
	 * Password changed by the user -> Old password required.<br> 
	 * Password changed by an admin -> Old password not required.
	 */
	@Transactional
	public void changeUserPassword(long sessionId, int userId, String oldPassword, String newPassword) throws ServiceException {
		checkPermissions(sessionId, userId, "changeUserPassword");
		Session session = openSessions.get(sessionId);
		try {
			User user = userDao.find(userId);
			if(session.getUser().getUserId() == userId && !oldPassword.contentEquals(user.getPassword()))  throw new ServiceException(05,"changeUserPassword","Incorrect password"); 
			user.setPassword(newPassword);
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(06,"changeUserPassword","User Not Found");
		}	
	}

	@Transactional(readOnly=true)
	public List<User> getUsersByEvet(long sessionId, int eventId, RegistrationState state) throws ServiceException {
		checkPermissions(sessionId, "getUsersByEvet");
		return userDao.getUsersByEvet(eventId,state);
	}
	
	@Transactional(readOnly=true)
	public List<User> getAllUsers(long sessionId) throws ServiceException {
		checkPermissions(sessionId, "getAllUsers");
		return userDao.getAllUsers();
	}


	public void setDefaultLanguage(long sessionId, int userId, int languageId) {
		// TODO Auto-generated method stub
		
	}


	public void addUserToBlackList(long sessionId, int userId) {
		// TODO Auto-generated method stub
		
	}


	public void removeUserFromBlackList(long sessionId, int userId) {
		// TODO Auto-generated method stub
		
	}


	@Transactional
	public void removeUser(long sessionId, int userId) throws ServiceException {
		checkPermissions(sessionId, "removeUser");
		try {
			User user = userDao.find(userId);
			user.setDeleted(true);
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(06,"removeUser","User Not Found");
		}
	}

	@Transactional
	public Role createRole(long sessionId, String roleName) throws ServiceException {
		checkPermissions(sessionId, "createRole");
		Role role = new Role(roleName);
		roleDao.save(role);
		return role;
	}

	@Transactional
	public void addRole(long sessionId, int roleId, int userId) throws ServiceException {
		checkPermissions(sessionId, "addRole");
		try {
			User user = userDao.find(userId);
			Role role = roleDao.find(roleId);
			user.getRoles().add(role);
			userDao.save(user);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"addRole","User Not Found");
			else throw new ServiceException(07,"addRole","Role Not Found");
		}
	}

	@Transactional
	public void removeRole(long sessionId, int roleId, int userId) throws ServiceException {
		checkPermissions(sessionId, "removeRole");
		try {
			User user = userDao.find(userId);
			Role role = roleDao.find(roleId);
			user.getRoles().remove(role);
			userDao.save(user);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(06,"removeRole","User Not Found");
			else throw new  ServiceException(07,"removeRole","Role Not Found");
		}
		
	}
	
	@Transactional(readOnly=true)
	public Set<Role> getUserRoles(long sessionId, int userId) throws ServiceException {
		checkPermissions(sessionId, "getUserRoles");
		try {
			User user = userDao.find(userId);
			return user.getRoles();
		} catch (InstanceException e) {
			throw new  ServiceException(06,"getUserRoles","User Not Found");
		}
	}

	@Transactional
	public UseCase createUseCase(long sessionId, String useCaseName) throws ServiceException {
		checkPermissions(sessionId, "createUseCase");
		UseCase useCase = new UseCase(useCaseName);
		useCaseDao.save(useCase);
		return useCase;
	}

	@Transactional
	public void addUseCase(long sessionId, int roleId, int useCaseId) throws ServiceException {
		checkPermissions(sessionId, "addUseCase");
		try {
			Role role = roleDao.find(roleId);
			UseCase useCase = useCaseDao.find(useCaseId);
			role.getUseCases().add(useCase);
			roleDao.save(role);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("UseCase")) throw new  ServiceException(8,"addUseCase","User Case Not Found");
			else throw new  ServiceException(07,"addUseCase","Role Not Found");
		}
		
	}

	@Transactional
	public void removeUseCase(long sessionId, int roleId, int useCaseId) throws ServiceException {
		checkPermissions(sessionId, "removeUseCase");
		try {
			Role role = roleDao.find(roleId);
			UseCase useCase = useCaseDao.find(useCaseId);
			role.getUseCases().remove(useCase);
			roleDao.save(role);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("UseCase")) throw new  ServiceException(8,"removeUseCase","User Case Not Found");
			else throw new  ServiceException(07,"removeUseCase","Role Not Found");
		}
		
	}

	@Transactional(readOnly=true)	
	public Set<UseCase> getRolePermissions(long sessionId, int roleId) throws ServiceException {
		checkPermissions(sessionId, "getRolePermissions");
		try {
			Role role = roleDao.find(roleId);
			return role.getUseCases();
		} catch (InstanceException e) {
			throw new  ServiceException(07,"getRolePermissions","Role Not Found");
		}
	}	
	
}

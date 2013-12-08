package es.ficonlan.web.backend.model.userservice;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.useCase.UseCase;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.user.UserDao;
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
	
	private boolean checkPermissions(Session session, String useCase) throws ServiceException {
		for(Role r:session.getUser().getRoles()){
		    for (UseCase uc: r.getUseCases()){
		    	if (uc.getUserCaseName().contentEquals("useCase")) return true;
		    }
		}
		return false;
	}
	
	@Transactional
	public User addUser(long sessionId, String name, String login, String password, String dni, String email, String phoneNumber, int shirtSize) throws ServiceException {
		if (!openSessions.containsKey(sessionId)) throw new ServiceException(01,"addUser","Invalid session");
		if(!checkPermissions(openSessions.get(sessionId),"addUser")) throw new ServiceException(02,"addUser","Permission denied");
		User user;
		user = userDao.findUserBylogin(login);
		if (user != null)
		throw new ServiceException(03,"addUser","Duplicted login");
		else {
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
		Session s = new Session(user);
		openSessions.put(s.getSessionId(), s);
		return s;
	}

	public void closeSession(long sessionId) throws ServiceException {
		if (!openSessions.containsKey(sessionId)) throw new ServiceException(01,"closeSession","Invalid session");
		openSessions.remove(sessionId);
	}

	@Transactional
	public void changeUserData(long sessionId, int userId, String name, String dni, String email, String phoneNumber, int shirtSize) throws ServiceException {
		if (!openSessions.containsKey(sessionId)) throw new ServiceException(01,"changeUserData","Invalid session");
		Session session = openSessions.get(sessionId);
		User user = session.getUser();
		if (user.getUserId() != userId && !checkPermissions(session,"changeUserData")) throw new ServiceException(02,"changeUserData","Permission denied");
		user.setName(name);
		user.setDni(dni);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		user.setSirtSize(shirtSize);
		userDao.save(user);
	}

	@Transactional
	public void changeUserPassword(long sessionId, int userId, String oldPassword, String newPassword) throws ServiceException {
		//TODO: Encriptado de passwords
		if (!openSessions.containsKey(sessionId)) throw new ServiceException(01,"changeUserPassword","Invalid session");
		Session session = openSessions.get(sessionId);
		User user = session.getUser();
		if (user.getUserId() != userId && !checkPermissions(session,"changeUserData")) throw new ServiceException(02,"changeUserPassword","Permission denied");
		// Password changed by the user -> Old password required. | Password changed by an admin -> Old password not required.
		if(user.getUserId() == userId && !oldPassword.contentEquals(user.getPassword()))  throw new ServiceException(05,"changeUserPassword","Incorrect password"); 
		user.setPassword(newPassword);
		userDao.save(user);
	}

	public List<User> getUsersByEvet(long sessionId, int eventId, RegistrationState state) throws ServiceException {
		if (!openSessions.containsKey(sessionId)) throw new ServiceException(01,"getUsersByEvet","Invalid session");
		if(!checkPermissions(openSessions.get(sessionId),"getAllUsers")) throw new ServiceException(02,"getUsersByEvet","Permission denied");
		return userDao.getUsersByEvet(eventId,state);
	}
	
	@Transactional(readOnly=true)
	public List<User> getAllUsers(long sessionId) throws ServiceException {
		if (!openSessions.containsKey(sessionId)) throw new ServiceException(01,"getAllUsers","Invalid session");
		if(!checkPermissions(openSessions.get(sessionId),"getAllUsers")) throw new ServiceException(02,"getAllUsers","Permission denied");
		return userDao.getAllUsers();
	}
	
	
	

}

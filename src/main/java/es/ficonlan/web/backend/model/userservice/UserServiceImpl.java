package es.ficonlan.web.backend.model.userservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.user.UserDao;
import es.ficonlan.web.backend.util.exceptions.DuplicatedInstanceException;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Transactional
	public User addUser(long sessionId, String name, String login, String password, String dni, String email, String phoneNumber, int shirtSize) throws DuplicatedInstanceException{
		//TODO: Comprobar permisoso con sessionId
		User user;
		user = userDao.findUserBylogin(login);
		
		if (user != null)
		throw new DuplicatedInstanceException(login,
				User.class.getName());
		else {
		User u = new User(name, login, password, dni, email, phoneNumber, shirtSize);
		userDao.save(u);
		return u;
		}
	}
	@Transactional(readOnly=true)
	public List<User> getAllUsers(long sessionId){
		//TODO: Comprobar permisoso con sessionId
		return userDao.getAllUsers();
	}
	
	

}

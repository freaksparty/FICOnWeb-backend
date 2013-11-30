package es.ficonlan.web.backend.model.user;

import java.util.List;

import es.ficonlan.web.backend.util.dao.GenericDao;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
public interface UserDao extends GenericDao<User,Integer> {
	
	public List<User> getAllUsers();
	
	public User findUserBylogin(String login);

}

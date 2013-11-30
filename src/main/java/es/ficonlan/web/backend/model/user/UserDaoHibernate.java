package es.ficonlan.web.backend.model.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.util.dao.GenericDaoHibernate;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User,Integer> implements UserDao {
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers(){
		return getSession().createQuery(
	        	"SELECT u " +
		        "FROM User u " +
	        	"ORDER BY u.User_id").list();
	}
	
	@Override
	public User findUserBylogin(String login) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u Where User_login = :login")
				.setParameter("login", login).uniqueResult();
	}

}

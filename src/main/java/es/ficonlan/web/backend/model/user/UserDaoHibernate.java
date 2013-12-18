package es.ficonlan.web.backend.model.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

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
		        "WHERE u.login!='anonymous'" +
	        	"ORDER BY u.userId").list();
	}
	
	public User findUserBylogin(String login) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u Where User_login = :login")
				.setParameter("login", login).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsersByEvet(int eventId, RegistrationState state) {
		if (state==null) return getSession().createQuery( "SELECT u " +
   			                                             "FROM User u JOIN Registration r" +
	                                                     "WHERE r.Event.id = :eventId" +
                                                         "ORDER BY u.User_id" 
	                                                    ).setParameter("eventId",eventId).list();
		else return getSession().createQuery( "SELECT u " +
                   							  "FROM User u JOIN Registration r" +
                   							  "WHERE r.Event.id = :eventId AND r.state = :state" +
                   							  "ORDER BY u.User_id" 
				 						    ).setParameter("eventId",eventId).setParameter("state",state).list(); 			
	}

	public User findUserByDni(String dni) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u Where User_dni = :dni")
				.setParameter("dni", dni).uniqueResult();
	}

}

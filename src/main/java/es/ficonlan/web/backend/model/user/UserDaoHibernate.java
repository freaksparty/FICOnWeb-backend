package es.ficonlan.web.backend.model.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Daniel Gómez Silva
 */
@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User,Integer> implements UserDao {
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers(){
		return getSession().createQuery(
	        	"SELECT u " +
		        "FROM User u " +
		        "WHERE u.login!='anonymous' AND u.deleted=FALSE " +
	        	"ORDER BY u.userId").list();
	}
	
	public User findUserBylogin(String login) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u WHERE User_login = :login AND u.deleted=FALSE ")
				.setParameter("login", login).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsersByEvent(int eventId, RegistrationState state) {
		if (state==null) return getSession().createQuery("SELECT u " +
   			                                             "FROM Registration r INNER JOIN r.user u " +
	                                                     "WHERE r.event.id = :eventId AND u.deleted=FALSE " +
                                                         "ORDER BY r.registrationDate" 
	                                                    ).setParameter("eventId",eventId).list();
		else return getSession().createQuery( "SELECT u " +
                   							  "FROM Registration r INNER JOIN r.user u " +
                   							  "WHERE r.event.id = :eventId AND r.state = :state AND u.deleted=FALSE " +
                   							  "ORDER BY r.registrationDate" 
				 						    ).setParameter("eventId",eventId).setParameter("state",state).list(); 			
	}

	public User findUserByDni(String dni) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u Where User_dni = :dni AND u.deleted=FALSE")
				.setParameter("dni", dni).uniqueResult();
	}

}

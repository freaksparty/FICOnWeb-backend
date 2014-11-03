package es.ficonlan.web.backend.model.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User,Integer> implements UserDao {
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers(int startindex, int maxResults){
		return getSession().createQuery(
	        	"SELECT u " +
		        "FROM User u " +
		        "WHERE u.login!='anonymous' AND u.deleted=FALSE " +
	        	"ORDER BY u.login").setFirstResult(startindex).setMaxResults(maxResults).list();
	}
	
	public User findUserBylogin(String login) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u WHERE User_login = :login AND u.deleted=FALSE ")
				.setParameter("login", login).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsersByEvent(int eventId, RegistrationState state, int startindex, int maxResults) {
		if (state==null) return getSession().createQuery("SELECT u " +
   			                                             "FROM Registration r INNER JOIN r.user u " +
	                                                     "WHERE r.event.id = :eventId AND u.deleted=FALSE " +
                                                         "ORDER BY r.registrationDate" 
	                                                    ).setParameter("eventId",eventId).setFirstResult(startindex).setMaxResults(maxResults).list();
		else return getSession().createQuery( "SELECT u " +
                   							  "FROM Registration r INNER JOIN r.user u " +
                   							  "WHERE r.event.id = :eventId AND r.state = :state AND u.deleted=FALSE " +
                   							  "ORDER BY r.registrationDate" 
				 						    ).setParameter("eventId",eventId).setParameter("state",state).setFirstResult(startindex).setMaxResults(maxResults).list(); 			
	}
	
	public User findUserByEmail(String email) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u Where User_email = :email AND u.deleted=FALSE")
				.setParameter("email", email).uniqueResult();
	}

	public User findUserByDni(String dni) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u Where User_dni = :dni AND u.deleted=FALSE")
				.setParameter("dni", dni).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersByName(String name, int startindex, int maxResults) {
		return getSession().createQuery(
	        	"SELECT u " +
		        "FROM User u " +
		        "WHERE u.login!='anonymous' AND u.deleted=FALSE " +
		          "AND ( LOWER(u.name) LIKE '%'||LOWER(:name)||'%' " +
		           "OR  LOWER(u.login) LIKE '%'||LOWER(:name)||'%' ) " +
	        	"ORDER BY u.login").setParameter("name",name).setFirstResult(startindex).setMaxResults(maxResults).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getBlacklistedUsers(int startIndex, int maxResults) {
		return getSession().createQuery(
	        	"SELECT u " +
		        "FROM User u " +
		        "WHERE u.login!='anonymous' AND u.deleted=FALSE " +
		          "AND u.inBlackList=true " +
	        	"ORDER BY u.login").setFirstResult(startIndex).setMaxResults(maxResults).list();
	}

}

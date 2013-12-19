package es.ficonlan.web.backend.model.registration;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Daniel Gómez Silva
 */
@Repository("rigistrationDao")
public class RegistrationDaoHibernate extends GenericDaoHibernate<Registration,Integer> implements RegistrationDao {
	
	public Registration findByUserAndEvent(int userId, int eventId){
			return (Registration) getSession().createQuery(
		        	"SELECT r " +
			        "FROM Registration r " +
			        "WHERE r.User_id=:userId AND r.Event_id=:eventId "
			       ).setInteger("userId",userId).setInteger("eventId", eventId).uniqueResult();
	}
	
}

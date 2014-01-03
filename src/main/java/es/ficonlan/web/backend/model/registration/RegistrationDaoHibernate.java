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
			        "WHERE r.user.userId=:userId AND r.event.eventId=:eventId "
			       ).setInteger("userId",userId).setInteger("eventId", eventId).uniqueResult();
	}
	
	public int geNumRegistrations(int eventId){
		return ((Long) getSession().createQuery(
	        	"SELECT COUNT(r) " +
		        "FROM Registration r " +
		        "WHERE r.event.eventId=:eventId "
		       ).setInteger("eventId", eventId).uniqueResult()).intValue();
	}
	
}

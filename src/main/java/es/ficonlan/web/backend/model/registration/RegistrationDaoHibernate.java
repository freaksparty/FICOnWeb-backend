package es.ficonlan.web.backend.model.registration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;
import es.ficonlan.web.backend.util.ShirtData;

/**
 * @author Daniel Gómez Silva
 */
@Repository("registrationDao")
public class RegistrationDaoHibernate extends GenericDaoHibernate<Registration,Integer> implements RegistrationDao {
	
	@Override
	public Registration findByUserAndEvent(int userId, int eventId){
			return (Registration) getSession().createQuery(
		        	"SELECT r " +
			        "FROM Registration r " +
			        "WHERE r.user.userId=:userId AND r.event.eventId=:eventId "
			       ).setInteger("userId",userId).setInteger("eventId", eventId).uniqueResult();
	}
	
	@Override
	public int geNumRegistrations(int eventId, RegistrationState state){
		return ((Long) getSession().createQuery(
	        	"SELECT COUNT(r) " +
		        "FROM Registration r " +
		        "WHERE r.event.eventId=:eventId AND r.state=:state "
		       ).setParameter("state", state).setInteger("eventId", eventId).uniqueResult()).intValue();
	}

	@Override
	public Registration getFirstInQueue(int eventId) {
		return (Registration) getSession().createQuery(
	        	"SELECT r " +
		        "FROM Registration r " +
		        "WHERE r.event.eventId=:eventId " +
		          "AND r.state=:state " +
		          "AND r.user.inBlackList=FALSE"
		       ).setParameter("state",RegistrationState.inQueue).setInteger("eventId", eventId).setFirstResult(0).setMaxResults(1).uniqueResult();
	}
	
	@Override
	public int geNumRegistrationsBeforeDate(int eventId, RegistrationState state, Calendar date) {
		return ((Long) getSession().createQuery(
	        	"SELECT COUNT(r) " +
		        "FROM Registration r " +
		        "WHERE r.event.eventId=:eventId AND r.state=:state AND r.Registration_date_created < :date"
		       ).setParameter("state", state).setInteger("eventId", eventId).setCalendar("date", date).uniqueResult()).intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Registration> getRegistrationByEvent(int eventId, RegistrationState state, int startindex, int maxResults, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		String ss = " ";
		String order = null;
		switch(orderBy) {
			case "registrationId"   : order = "r.registrationId"; break;
			case "state"            : order = "r.state"; break;
			case "registrationDate" : order = "r.registrationDate"; break;
			case "paidDate"         : order = "r.paidDate"; break;
			case "place"            : order = "r.place"; break;
			case "dni"              : order = "u.dni"; break;
			case "login"            : order = "u.login"; break;
			case "dob"              : order = "u.dob"; break;
			default                 : order = "r.registrationId"; break;	
		}
		
		Query query = null;
		if(state==null) {
			query = getSession().createQuery("SELECT r " +
                   "FROM Registration r INNER JOIN r.user u " +
                   "WHERE r.event.id = :eventId AND u.deleted=FALSE " + ss +
                   " ORDER BY " + order + aux
                  ).setParameter("eventId",eventId);
		}
		else {
			query = getSession().createQuery("SELECT r " +
	                   "FROM Registration r INNER JOIN r.user u " +
	                   "WHERE r.event.id = :eventId AND u.deleted=FALSE AND r.state = :state " +
	                   " ORDER BY " + order + aux
	                  ).setParameter("eventId",eventId).setParameter("state",state);
		}
		if(maxResults<1) return query.list();
		else return query.setFirstResult(startindex).setMaxResults(maxResults).list();
	}
	
	@Override
	public List<ShirtData> getShirtSizesPaid(int eventId) {
		Query query = getSession().createQuery("SELECT count(User_id) as number,User_shirtSize as size " +
		"FROM User JOIN Registration ON User_id = Registration_user_id " +
		"WHERE Registration_event_id = :eventId AND Registration_state = 3 " +
		"GROUP BY user_shirtSize ").setInteger("eventId", eventId);
		
		List<ShirtData> list = new ArrayList<ShirtData>();
		
		@SuppressWarnings("rawtypes")
		Iterator it = query.iterate();
		
		while(it.hasNext()) 
		  {  
		   Object[] row = (Object[]) it.next();  
		   list.add(new ShirtData((String) row[1], (int) row[0]));  
		  }  
		
		return list;
	}
	
	
}

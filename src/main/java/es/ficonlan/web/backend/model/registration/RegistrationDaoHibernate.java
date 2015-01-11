package es.ficonlan.web.backend.model.registration;

import java.util.ArrayList;
import java.util.Calendar;
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
		        "WHERE r.event.eventId=:eventId AND r.state=:state AND r.registrationDate < :date"
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
		
		Query query = getSession().createQuery(
				"SELECT count(r) " +
                "FROM Registration r INNER JOIN r.user u " +
                "WHERE r.event.id = :eventId AND Registration_state = 2 AND u.shirtSize = :size"
                ).setInteger("eventId", eventId);
		
		long sizeXS  = (long) query.setParameter("size", "XS" ).uniqueResult();
		long sizeS   = (long) query.setParameter("size", "S"  ).uniqueResult();
		long sizeM   = (long) query.setParameter("size", "M"  ).uniqueResult();
		long sizeL   = (long) query.setParameter("size", "L"  ).uniqueResult();
		long sizeXL  = (long) query.setParameter("size", "XL" ).uniqueResult();
		long sizeXXL = (long) query.setParameter("size", "XXL").uniqueResult();
		
		List<ShirtData> list = new ArrayList<ShirtData>();
		
		list.add(new ShirtData("XS" ,sizeXS ));
		list.add(new ShirtData("S"  ,sizeS  ));
		list.add(new ShirtData("M"  ,sizeM  ));
		list.add(new ShirtData("L"  ,sizeL  ));
		list.add(new ShirtData("XL" ,sizeXL ));
		list.add(new ShirtData("XXL",sizeXXL));
		
		return list;
		
	}
	
	
}

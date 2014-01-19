package es.ficonlan.web.backend.model.activity;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.activity.Activity.ActivityType;
import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Repository("activityDao")
public class ActivityDaoHibernate extends GenericDaoHibernate<Activity,Integer> implements ActivityDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Activity> findActivitiesByEvent(int eventId, ActivityType type) {
		/*String hql = "SELECT u FROM Activity u";
		if ((eventId != 0) || (type != 0)) hql = hql + " WHERE ";
		String aux = "";
		if (eventId != 0) { hql = hql + aux + "(u.category.eventId = :eventId)"; }
		if ((eventId != 0) && (type != 0)) hql = hql + " AND ";
		if (type != 0)    { hql = hql + aux + "(u.type = :type)";                }
		
		hql = hql + " ORDER BY u.dateStart";
		
		Query query = getSession().createQuery(hql);
		
		if (eventId != 0) { query = query.setParameter("eventId", eventId); }
		if (type != 0)    { query = query.setParameter("type", type);       }
		
		return query.list();*/
		if(type==null) return getSession().createQuery( "SELECT a "  +
			      										"FROM Activity a " +
			      										"WHERE a.event.id = :eventId " +
			      										"ORDER BY a.startDate"
         											  ).setParameter("eventId",eventId).list(); 	
		else return getSession().createQuery( "SELECT a "  +
			      						      "FROM Activity a " +
			      						      "WHERE a.event.id = :eventId AND a.type = :type " +
			      						      "ORDER BY a.startDate"
				                       		).setParameter("eventId",eventId).setParameter("type",type).list(); 			
	}

	/*@Override
	public List<User> getParticipants(Activity activity) {	
		//Force initialization
		Hibernate.initialize(activity.getParticipants());
		for(User u:activity.getParticipants()){
			Hibernate.initialize(u);
		}
		return activity.getParticipants();
	}*/
	
	

}

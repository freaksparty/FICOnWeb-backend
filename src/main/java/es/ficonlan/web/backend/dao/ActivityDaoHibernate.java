package es.ficonlan.web.backend.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.entities.Activity;
import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.entities.Activity.ActivityType;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @author Siro González <xiromoreira>
 */
@Repository("activityDao")
public class ActivityDaoHibernate extends
		GenericDaoHibernate<Activity, Integer> implements ActivityDao {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Activity> getAllActivity() {
		return getSession().createQuery("SELECT a FROM Activity a ").list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Activity> findActivitiesByEvent(int eventId, ActivityType type, int startIndex, int cont, String orderBy, boolean desc) {

		String aux = " ";
		if(desc) aux=" DESC";
		Query query = null;
		
		if (type == null)
			query = getSession().createQuery("SELECT a " + "FROM Activity a "
									+ "WHERE a.event.eventId = :eventId "
									+ " ORDER BY a." + orderBy +  aux )
					.setParameter("eventId", eventId);
		else
			query = getSession().createQuery(
							"SELECT a "
									+ "FROM Activity a "
									+ "WHERE a.event.eventId = :eventId AND a.type = :type "
									+ " ORDER BY a." + orderBy +  aux )
					.setParameter("eventId", eventId)
					.setParameter("type", type);
		
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}
	
	@Override
	public long findActivitiesByEventTAM(int eventId, ActivityType type) {
		
		Query query = null;
		
		if (type == null)
			query = getSession().createQuery("SELECT count(a) "
									+ "FROM Activity a "
									+ "WHERE a.event.eventId = :eventId ")
					.setParameter("eventId", eventId);
		else
			query = getSession().createQuery(
							"SELECT count(a) "
							+ "FROM Activity a "
							+ "WHERE a.event.eventId = :eventId AND a.type = :type ")
					.setParameter("eventId", eventId)
					.setParameter("type", type);
		
		return (long) query.uniqueResult();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> findActivitiesByEvent(int eventId, ActivityType type) {
		
		Query query = null;
		
		if (type == null)
			query = getSession().createQuery("SELECT a "
									+ "FROM Activity a "
									+ "WHERE a.event.eventId = :eventId ")
					.setParameter("eventId", eventId);
		else
			query = getSession().createQuery(
							"SELECT a "
							+ "FROM Activity a "
							+ "WHERE a.event.eventId = :eventId AND a.type = :type ")
					.setParameter("eventId", eventId)
					.setParameter("type", type);
		
		return (List<Activity>) query.list();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getParticipants(int activityId) {
		return getSession()
				.createQuery(
						"SELECT p "
								+ "FROM Activity a INNER JOIN a.participants p "
								+ "WHERE a.activityId=:activityId "
								+ "ORDER BY p.login")
				.setParameter("activityId", activityId).list();
	}
	

}

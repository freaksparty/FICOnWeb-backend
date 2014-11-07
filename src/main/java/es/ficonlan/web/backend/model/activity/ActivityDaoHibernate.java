package es.ficonlan.web.backend.model.activity;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.activity.Activity.ActivityType;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
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
	public List<Activity> findActivitiesByEvent(int eventId, ActivityType type) {

		if (type == null)
			return getSession()
					.createQuery(
							"SELECT a " + "FROM Activity a "
									+ "WHERE a.event.eventId = :eventId "
									+ "ORDER BY a.startDate")
					.setParameter("eventId", eventId).list();
		else
			return getSession()
					.createQuery(
							"SELECT a "
									+ "FROM Activity a "
									+ "WHERE a.event.eventId = :eventId AND a.type = :type "
									+ "ORDER BY a.startDate")
					.setParameter("eventId", eventId)
					.setParameter("type", type).list();
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

package es.ficonlan.web.backend.model.activity;

import java.util.List;

import es.ficonlan.web.backend.model.activity.Activity.ActivityType;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.util.dao.GenericDao;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
public interface ActivityDao extends GenericDao<Activity, Integer> {
	
	public List<Activity> findActivitiesByEvent(int eventId, ActivityType type);
	
	public List<User> getParticipants( int activityId);

}

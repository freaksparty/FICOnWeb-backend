package es.ficonlan.web.backend.dao;

import java.util.List;

import es.ficonlan.web.backend.entities.Activity;
import es.ficonlan.web.backend.entities.Activity.ActivityType;
import es.ficonlan.web.backend.entities.User;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
public interface ActivityDao extends GenericDao<Activity, Integer> {
	
	public List<Activity> getAllActivity();

	public List<Activity> findActivitiesByEvent(int eventId, ActivityType type, int startIndex, int cont, String orderBy, boolean desc);
	
	public long findActivitiesByEventTAM(int eventId, ActivityType type);
	
	public List<Activity> findActivitiesByEvent(int eventId, ActivityType type);

	public List<User> getParticipants(int activityId);
	
	/**
     * Gets all the activities a given user is registered for a given event.
     * @param eventId
     * @param userId
     * @return
     */
    public List<Integer> getActivitiesRegistered(int eventId, int userId);

}

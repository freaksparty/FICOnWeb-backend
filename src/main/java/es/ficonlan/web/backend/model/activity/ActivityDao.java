package es.ficonlan.web.backend.model.activity;

import java.util.List;

import es.ficonlan.web.backend.model.activity.Activity.ActivityType;
import es.ficonlan.web.backend.model.util.dao.GenericDao;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
public interface ActivityDao extends GenericDao<Activity, Integer> {
	
	
	/**
	 * @param eventId Id del evento del que queremos obtener la lista de Actividades (si es 0 se devolverán las de todos los eventos)
	 * @param type Tipo de actividades que queremos obtener (si es 0 se devolverán todos los tipos)
	 * @return List<Activity>
	 */
	public List<Activity> findActivitiesByEvent(long eventId, ActivityType type);

}

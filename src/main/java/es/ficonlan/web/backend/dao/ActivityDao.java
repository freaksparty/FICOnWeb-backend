/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

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

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.entities.Registration;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;
import es.ficonlan.web.backend.output.ShirtData;

/**
 * @author Daniel Gómez Silva
 */
public interface RegistrationDao extends GenericDao<Registration, Integer> {
	
	public Registration findByUserAndEvent(int userId, int eventId);
	
	public Registration getFirstInQueue(int eventId);
	
	public int geNumRegistrations(int eventId, RegistrationState state);
	
	public int geNumRegistrationsBeforeDate(int eventId, RegistrationState state, Calendar date);
	
	public List<Registration> getRegistrationByEvent(int eventId, RegistrationState state, int startindex, int maxResults, String orderBy, boolean desc);
	
	public long getRegistrationByEventTAM(int eventId, RegistrationState state);
	
	public List<ShirtData> getShirtSizesPaid(int eventId);
	
}

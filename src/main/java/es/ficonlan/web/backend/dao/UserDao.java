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

import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
public interface UserDao extends GenericDao<User,Integer> {
	
	public List<User> getAllUsers(int startindex, int maxResults, String orderBy, boolean desc);
	
	public long getAllUsersTAM();
	
	public User findUserByLogin(String login);
	
	public User findUserByDni(String dni);
	
	public User findUserByEmail(String email);

	public List<User> getUsersByEvent(int eventId, RegistrationState state, int startindex, int maxResults, String orderBy, boolean desc);
	
	public long getUsersByEventTAM(int eventId, RegistrationState state);

	public List<User> findUsersByName(String name, int startindex, int maxResults);

	public List<User> getBlacklistedUsers(int startIndex, int maxResults);

}

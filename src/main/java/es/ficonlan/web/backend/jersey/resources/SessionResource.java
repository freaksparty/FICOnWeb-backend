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

package es.ficonlan.web.backend.jersey.resources;


import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.Session;
import es.ficonlan.web.backend.model.util.session.SessionManager;
import es.ficonlan.web.backend.services.userservice.UserService;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("/session")
public class SessionResource {
	
	@Autowired
	private UserService userService;
	
	public SessionResource(){
		userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
	}
	    
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Session newSession() throws ServiceException {
    	Session s = userService.newAnonymousSession().clone();
    	s.setUser(null);
    	return s;
	}
	
	@DELETE
	public void close(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		userService.closeSession(sessionId);
	}
	
	//FIXME Ya está cambiado a user, si haces un GET a user/{userId} con userId < 0
	@Path("/currentUser/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User currentUser(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return userService.getCurrenUser(sessionId);
	}
	
	@Path("/isvalid")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public boolean validSesion(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return SessionManager.exists(sessionId);
	}
	
	@Path("/closeAllUserSession/{userId}")
	@DELETE
	public void closeAllUserSessions(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId) throws ServiceException {
		userService.closeAllUserSessions(sessionId,userId);
	}
}

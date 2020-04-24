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

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.annotations.UseCasePermission;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.Session;
import es.ficonlan.web.backend.services.userservice.UserService;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @author Daniel Gómez Silva
 */
@Path("/login")
@Singleton
public class LoginResource {
	
	static class LoginData {

		private String login;
		private String password;

		public LoginData() {
		}

		public LoginData(String login, String password) {
			this.login = login;
			this.password = password;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	@Autowired
	private UserService userService;

	public LoginResource() {
		userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
	}

	@POST
	@UseCasePermission("login")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Session login(@Context ContainerRequestContext context, LoginData loginData) throws ServiceException {		
		return userService.login(loginData.getLogin(), loginData.getPassword());
	}
	
	@DELETE
	public void logout(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		userService.closeSession(sessionId);
	}

}

package es.ficonlan.web.backend.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.Session;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @author Daniel Gómez Silva
 */
@Path("/login")
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
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Session login(@HeaderParam("sessionId") String sessionId, LoginData loginData) throws ServiceException {
		return userService.login(sessionId, loginData.getLogin(), loginData.getPassword());
	}
	
	@DELETE
	public void logout(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		userService.closeSession(sessionId);
	}

}

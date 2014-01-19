package es.ficonlan.web.backend.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.Session;

/**
 * @author Daniel Gómez Silva
 */
@Path("/session")
public class SessionResource {
	
	static class LoginData{
		private String login;
		private String password;

		public LoginData(){}
		
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
	
	private UserService userService;
	
	public SessionResource(){
		userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
	}
	    
	@Path("/anonymous")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Session newSession() throws ServiceException {
    	Session s = userService.newAnonymousSession().clone();
    	s.setUser(null);
    	return s;
	}
	
	@Path("/login")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Session login(@HeaderParam("sessionId") long sessionId, LoginData loginData) throws ServiceException {
    	return userService.login(sessionId, loginData.getLogin(), loginData.getPassword());
	}
	
	@Path("/close")
	@POST
	public void close(@HeaderParam("sessionId") long sessionId) throws ServiceException{
    	userService.closeSession(sessionId);
	}
	
}

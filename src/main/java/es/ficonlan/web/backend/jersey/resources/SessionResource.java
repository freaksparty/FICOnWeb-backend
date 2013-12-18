package es.ficonlan.web.backend.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.userservice.Session;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
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
		try{
    		Session s = userService.newAnonymousSession().clone();
    		s.setUser(null);
    		return s;
    	}catch(RuntimeException e){
    		throw new ServiceException(99,"newAnonymousSession");
    	}
	}
	
	@Path("/login")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Session login(@HeaderParam("sessionId") long sessionId, LoginData loginData) throws ServiceException {
		try{
    		return userService.login(sessionId, loginData.getLogin(), loginData.getPassword());
    	}catch(RuntimeException e){
    		throw new ServiceException(99,"login");
    	}
	}
	
	@Path("/close")
	@POST
	public void close(@HeaderParam("sessionId") long sessionId) throws ServiceException{
		try{
    		userService.closeSession(sessionId);
    	}catch(RuntimeException e){
    		throw new ServiceException(99,"closeSession");
    	}
	}
	
}

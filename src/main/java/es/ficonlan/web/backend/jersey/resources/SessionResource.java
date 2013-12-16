package es.ficonlan.web.backend.jersey.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.userservice.Session;
import es.ficonlan.web.backend.model.userservice.UserService;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
@Path("/session")
public class SessionResource {
	
	private UserService userService;
	
	public SessionResource(){
		userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
	}
	    
	@Path("/new")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Session newSession() {
		return  userService.newAnonymousSession();
	}
}

package es.ficonlan.web.backend.jersey.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @author Daniel Gómez Silva
 */
@Path("users")
public class UsersResource {
	
	@Autowired
    private UserService userService;
    
	public UsersResource(){
		userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
	}
	
	@Path("/all/{statrtIndex}/{maxResults}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getAll(@HeaderParam("sessionId") String sessionId, @PathParam("startIndex") int startIndex,  @PathParam("maxResults") int maxResults) throws ServiceException {
		return userService.getAllUsers(sessionId, startIndex, maxResults);

	}
	
	@Path("/findByName/{name}/{statrtIndex}/{maxResults}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> findByName(@HeaderParam("sessionId") String sessionId, @PathParam("name") String name, @PathParam("startIndex") int startIndex,  
			                     @PathParam("maxResults") int maxResults) throws ServiceException {
		return userService.findUsersByName(sessionId, name, startIndex, maxResults);
	}
	
	@Path("/byEvent/{eventId}/{state}/{statrtIndex}/{maxResults}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getByEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("state") String state,  
			                     @PathParam("startIndex") int startIndex,  @PathParam("maxResults") int maxResults) throws ServiceException {
		RegistrationState st;
		if(state==null) throw new ServiceException(ServiceException.MISSING_FIELD,"state");
    	if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    	else if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    	else if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
    	else if(state.toLowerCase().contentEquals("all")) st=null;
    	else throw new ServiceException(ServiceException.INCORRECT_FIELD,"state");
		return userService.getUsersByEvent(sessionId, eventId, st, startIndex, maxResults);
	}
	

}

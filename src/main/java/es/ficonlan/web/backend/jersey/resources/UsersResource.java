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

}

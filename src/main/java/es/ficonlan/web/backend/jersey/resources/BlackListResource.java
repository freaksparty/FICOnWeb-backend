package es.ficonlan.web.backend.jersey.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.services.userservice.UserService;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @author Daniel Gómez Silva
 */
@Path("blacklist")
public class BlackListResource {
	
	@Autowired
    private UserService userService;
    
	public BlackListResource(){
		userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
	}
	
	@Path("/user/{startIndex}/{maxResults}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getBlackList(@HeaderParam("sessionId") String sessionId, @PathParam("startIndex") int startIndex,  @PathParam("maxResults") int maxResults) throws ServiceException {
		return userService.getBlacklistedUsers(sessionId, startIndex, maxResults);
	}
	
	@Path("/user/{userId}")
	@PUT
	public void addToBlackList(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId) throws ServiceException {
		userService.addUserToBlackList(sessionId, userId);
	}
	
	@Path("/user/{userId}")
	@DELETE
	public void removeFromBlackList(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId) throws ServiceException {
		userService.removeUserFromBlackList(sessionId, userId);
	}
}

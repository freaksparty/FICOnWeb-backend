package es.ficonlan.web.backend.jersey.resources;

import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @author Daniel Gómez Silva
 */
@Path("userrole")
public class UserRolesResource {
	
	@Autowired
    private UserService userService;
    
	public UserRolesResource(){
		userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
	}
	
	@Path("/{userId}/{roleId}")
	@POST
	public void addRole(@HeaderParam("sessionId") String sessionId, @PathParam("roleId") int roleId, @PathParam("userId") int userId) throws ServiceException{
		userService.addRole(sessionId, roleId, userId);
	}
	
	@Path("/{userId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Set<Role> getUserRoles(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId) throws ServiceException {
		return userService.getUserRoles(sessionId, userId);
	}
	
	@Path("/{userId}/{roleId}")
	@DELETE
	public void removeRole(@HeaderParam("sessionId") String sessionId, @PathParam("roleId") int roleId, @PathParam("userId") int userId) throws ServiceException{
		userService.removeRole(sessionId, roleId, userId);
	} 

}

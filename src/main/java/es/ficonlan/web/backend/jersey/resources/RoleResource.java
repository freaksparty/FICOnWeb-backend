package es.ficonlan.web.backend.jersey.resources;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.entities.Role;
import es.ficonlan.web.backend.entities.UseCase;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.services.userservice.UserService;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @author Daniel Gómez Silva
 */
@Path("role")
public class RoleResource {

	@Autowired
    private UserService userService;
    
	public RoleResource(){
		userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
	}
	
	@Path("/{roleName}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Role createRole(@HeaderParam("sessionId") String sessionId, @PathParam("roleId") String roleName) throws ServiceException {
		return userService.createRole(sessionId, roleName);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Role> getAllRoles(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return userService.getAllRoles(sessionId);
	}
	
	@Path("/{roleId}")
	@DELETE
	public void removeRole(@HeaderParam("sessionId") String sessionId, @PathParam("roleId") int roleId) throws ServiceException {
		userService.removeRole(sessionId, roleId);
	}
	
	@Path("/permissions")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<UseCase> getAll(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return userService.getAllUseCases(sessionId);
	}
	
	@Path("/permissions/{roleId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Set<UseCase> getRolePermissions(@HeaderParam("sessionId") String sessionId, @PathParam("roleId") int roleId) throws ServiceException {
		return userService.getRolePermissions(sessionId, roleId);
	}

	@Path("/permissions/{roleId}/{useCaseId}")
	@PUT
	public void addPermissionToRole(@HeaderParam("sessionId") String sessionId, @PathParam("roleId") int roleId, @PathParam("useCaseId") int useCaseId) throws ServiceException{
		userService.addPermission(sessionId, roleId, useCaseId);
	}

	@Path("/permissions/{roleId}/{useCaseId}")
	@DELETE
	public void removePermissionFromRole(@HeaderParam("sessionId") String sessionId, @PathParam("roleId") int roleId, @PathParam("useCaseId") int useCaseId) throws ServiceException{
		userService.removePermission(sessionId, roleId, useCaseId);
	} 
}

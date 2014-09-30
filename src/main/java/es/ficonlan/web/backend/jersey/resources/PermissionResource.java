package es.ficonlan.web.backend.jersey.resources;

import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.usecase.UseCase;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 */
@Path("permissions")
public class PermissionResource {
	
	@Autowired
    private UserService userService;
    
	public PermissionResource(){
		userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<UseCase> getAll(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return userService.getAllUseCases(sessionId);

	}
	
	@Path("/role/{roleName}")
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	public Role createRole(@HeaderParam("sessionId") String sessionId, @PathParam("roleName") String roleName) throws ServiceException {
		return userService.createRole(sessionId, roleName);
	}
	
	@Path("/add/{roleId}/{useCaseId}")
	@POST
	public void addPermissionToRole(@HeaderParam("sessionId") String sessionId, @PathParam("roleId") int roleId, @PathParam("useCaseId") int useCaseId) throws ServiceException{
		userService.addPermission(sessionId, roleId, useCaseId);
	}
	
	@Path("/remove/{roleId}/{useCaseId}")
	@POST
	public void removePermissionFromRole(@HeaderParam("sessionId") String sessionId, @PathParam("roleId") int roleId, @PathParam("useCaseId") int useCaseId) throws ServiceException{
		userService.removePermission(sessionId, roleId, useCaseId);
	} 
	
	@Path("/role/{roleId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Set<UseCase> getRolePermissions(@HeaderParam("sessionId") String sessionId, @PathParam("roleId") int roleId) throws ServiceException {
		return userService.getRolePermissions(sessionId, roleId);
	}
		
	@Path("/roles")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Role> getAllRoles(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return userService.getAllRoles(sessionId);
	}	
}

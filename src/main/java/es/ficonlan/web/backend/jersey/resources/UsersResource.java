package es.ficonlan.web.backend.jersey.resources;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	
	@Path("/all/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getAll(@HeaderParam("sessionId") String sessionId,
			@DefaultValue("1") @QueryParam("page") int page, 
			@DefaultValue("0") @QueryParam("pageTam") int pageTam,
			@DefaultValue("login") @QueryParam("orderBy") String orderBy,
			@DefaultValue("1") @QueryParam("desc") int desc
			) throws ServiceException {
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		return userService.getAllUsers(sessionId, startIndex, cont, orderBy, b);
	}
	
	@Path("/all/size")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public long getAllTAM(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return userService.getAllUsersTAM(sessionId);

	}
	
	@Path("/findByName/{name}/{statrtIndex}/{maxResults}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> findByName(@HeaderParam("sessionId") String sessionId, @PathParam("name") String name, @PathParam("startIndex") int startIndex,  
			                     @PathParam("maxResults") int maxResults) throws ServiceException {
		return userService.findUsersByName(sessionId, name, startIndex, maxResults);
	}

}

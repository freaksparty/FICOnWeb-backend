package es.ficonlan.web.backend.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
@Path("user")
public class UserResource {

    @Autowired
    private UserService userService;
    
	public UserResource(){
		userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
	}
    
	@Path("/add")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public User adduser(@HeaderParam("sessionId") long sessionId, User user) throws ServiceException {
		try{
			return userService.addUser(sessionId, user);
		}catch(RuntimeException e){
			throw new ServiceException(99,"addUser","Error inesperado en el sistema");
		}
	}
}

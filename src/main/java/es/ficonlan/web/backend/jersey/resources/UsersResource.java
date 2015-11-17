package es.ficonlan.web.backend.jersey.resources;

import java.util.ArrayList;
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

import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.services.userservice.UserService;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @author Daniel Gómez Silva
 */
@Path("users")
public class UsersResource {
	
	private String[] s = {"userId","name","login","dni","email","phoneNumber","shirtSize","dob"};
	private ArrayList<String> l;
	
	@Autowired
    private UserService userService;
    
	public UsersResource(){
		userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
		l = new ArrayList<String>();
		l.add(s[0]);l.add(s[1]);l.add(s[2]);l.add(s[3]);l.add(s[4]);l.add(s[5]);l.add(s[6]);l.add(s[7]);
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
		if(l.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
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

package es.ficonlan.web.backend.jersey.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
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
 * @author Daniel Gómez Silva
 */
@Path("user")
public class UserResource {
	
	static class ChangePasswordData {
		private String oldPassword;
		private String newPassword;

		public ChangePasswordData(){}
		public ChangePasswordData(String oldPassword, String newPassword) {
			super();
			this.oldPassword = oldPassword;
			this.newPassword = newPassword;
		}
		public String getOldPassword() {
			return oldPassword;
		}
		public void setOldPassword(String oldPassword) {
			this.oldPassword = oldPassword;
		}
		public String getNewPassword() {
			return newPassword;
		}
		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}	
	}


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
		return userService.addUser(sessionId, user);
	}
	
	@Path("/changeData")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void changeData(@HeaderParam("sessionId") long sessionId, User user) throws ServiceException {
		userService.changeUserData(sessionId, user);
	}
	
	@Path("/changePassword/{userId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void changePassword(@HeaderParam("sessionId") long sessionId, @PathParam("userId") int userId, ChangePasswordData data) throws ServiceException {
		userService.changeUserPassword(sessionId, userId, data.getOldPassword(), data.getNewPassword());
	}
	
	@Path("/all")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getAll(@HeaderParam("sessionId") long sessionId) throws ServiceException {
		return userService.getAllUsers(sessionId);

	}
	
	@Path("/byEvent/{eventId}/{state}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getByEvent(@HeaderParam("sessionId") long sessionId, @PathParam("eventId") int eventId, @PathParam("state") String state) throws ServiceException {
		RegistrationState st;
		if(state==null) throw new ServiceException(05,"getUsersByEvent","state");
    	if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    	else if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    	else if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
    	else if(state.toLowerCase().contentEquals("all")) st=null;
    	else throw new ServiceException(04,"getUsersByEvent","state");
		return userService.getUsersByEvent(sessionId, eventId, st);
	}
}

package es.ficonlan.web.backend.jersey.resources;

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

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.eventservice.EventService;
import es.ficonlan.web.backend.model.registration.Registration;
import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("registration")
public class RegistrationResource {
	
	@Autowired
	private EventService eventService;
	
	public RegistrationResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
	}
	
	@Path("/registration/{eventId}/{userId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Registration addParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		return eventService.addParticipantToEvent(sessionId, userId, eventId);
	}
	
	@Path("/registration/{eventId}/{userId}")
	@DELETE
	public void removeParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		eventService.removeParticipantFromEvent(sessionId, userId, eventId);
	}
	
	@Path("/registration/{eventId}/{userId}/{state}")
	@PUT
	public void changeRegistrationState(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId, @PathParam("state") String state) throws ServiceException {
		RegistrationState st=null;
		if(state!=null){
    		if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    		if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    		if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
		}
		eventService.changeRegistrationState(sessionId, userId, eventId, st);
	}
	
	@Path("/setPaid/{eventId}/{userId}")
	@PUT
	public void setPaid(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		eventService.setPaid(sessionId, userId, eventId);
	}
	
	@Path("/{eventId}/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Registration getRegistration(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		return eventService.getRegistration(sessionId, userId, eventId);
	}

}

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

import es.ficonlan.web.backend.entities.Registration;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.services.eventservice.EventService;
import es.ficonlan.web.backend.util.EventRegistrationState;

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
	
	@Path("/deleteAll/{eventID}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void deleteAllFormEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		
	}
	
	@Path("/{eventId}/{userId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Registration addParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		return eventService.addParticipantToEvent(sessionId, userId, eventId);
	}
	
	@Path("/{eventId}/{userId}")
	@DELETE
	public void removeParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		eventService.removeParticipantFromEvent(sessionId, userId, eventId);
	}
	
	@Path("/{eventId}/{userId}/{state}")
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
	
	@Path("/state/{eventId}/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public EventRegistrationState getEventRegistrationState(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		return eventService.getEventRegistrationState(sessionId, eventId, userId);
	}
	
	@Path("/sendmail/{eventId}/{userId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void sendRegistrationMail(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		eventService.sendRegistrationMail(sessionId, userId, eventId);
	}
	

}

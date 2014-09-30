package es.ficonlan.web.backend.jersey.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.eventservice.EventService;
import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 */
@Path("event")
public class EventResource {
	
	private EventService eventService;
	
	public EventResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Event createEvent(@HeaderParam("sessionId") String sessionId, Event event) throws ServiceException {
		return eventService.createEvent(sessionId, event);
	}
	
	@Path("/changeData")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void changeData(@HeaderParam("sessionId") String sessionId, Event eventData) throws ServiceException {
		eventService.changeEventData(sessionId, eventData);
	}
	
	@Path("/addParticipant/{eventId}/{userId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void addParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		eventService.addParticipantToEvent(sessionId, userId, eventId);
	}
	
	@Path("/removeParticipant/{eventId}/{userId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void removeParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		eventService.removeParticipantFromEvent(sessionId, userId, eventId);
	}
	
	@Path("/changeRegistrationState/{eventId}/{userId}/{state}")
	@POST
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
	@POST
	public void setPaid(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		eventService.setPaid(sessionId, userId, eventId);
	}
	
	@Path("/byName/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> findByName(@HeaderParam("sessionId") String sessionId, @PathParam("name") String name) throws ServiceException {
		return eventService.findEventByName(sessionId, name);
	}    
}

package es.ficonlan.web.backend.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
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
	
	@Path("/create")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Event createEvent(@HeaderParam("sessionId") long sessionId, Event event) throws ServiceException {
		try{
			return eventService.createEvent(sessionId, event);
    	}catch(RuntimeException e){
    		throw new ServiceException(99,"createEvent");
    	}
	}
	
	@Path("/changeData")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void changeData(@HeaderParam("sessionId") long sessionId, Event eventData) throws ServiceException {
		try{
			eventService.changeEventData(sessionId, eventData);
		}catch(RuntimeException e){
			throw new ServiceException(99,"changeEventData");
		}
	}
	
	@Path("/addParticipant/{eventId}/{userId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void addParticipant(@HeaderParam("sessionId") long sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		try{
			eventService.addParticipantToEvent(sessionId, userId, eventId);
		}catch(RuntimeException e){
			throw new ServiceException(99,"addParticipantToEvent");
		}
	}
	
	@Path("/removeParticipant/{eventId}/{userId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void removeParticipant(@HeaderParam("sessionId") long sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		try{
			eventService.removeParticipantFromEvent(sessionId, userId, eventId);
		}catch(RuntimeException e){
			throw new ServiceException(99,"removeParticipantFromEvent");
		}
	}
	
	@Path("/changeRegistrationState/{eventId}/{userId}/{state}")
	@POST
	public void changeRegistrationState(@HeaderParam("sessionId") long sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId, @PathParam("state") String state) throws ServiceException {
		try{
			RegistrationState st=null;
			if(state!=null){
    			if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    			if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    			if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
			}
			eventService.changeRegistrationState(sessionId, userId, eventId, st);
		}catch(RuntimeException e){
			throw new ServiceException(99,"changeRegistrationState");
		}
	}
	
	@Path("/byName/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Event findByName(@HeaderParam("sessionId") long sessionId, @PathParam("name") String name) throws ServiceException {
		try{
			return eventService.findEventByName(sessionId, name);
    	}catch(RuntimeException e){
    		throw new ServiceException(99,"findEventByName");
    	}
	}
	
}

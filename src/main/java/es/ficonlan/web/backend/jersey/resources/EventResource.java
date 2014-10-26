package es.ficonlan.web.backend.jersey.resources;

import java.util.List;

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

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.activity.Activity;
import es.ficonlan.web.backend.model.activity.Activity.ActivityType;
import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.eventservice.EventService;
import es.ficonlan.web.backend.model.sponsor.Sponsor;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("event")
public class EventResource {
	
	private EventService eventService;
	
	public EventResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Event createEvent(@HeaderParam("sessionId") String sessionId, Event event) throws ServiceException {
		return eventService.createEvent(sessionId, event);
	}
	
	@Path("/{eventId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Event changeData(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, Event eventData) throws ServiceException {
		return eventService.changeEventData(sessionId, eventId, eventData);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getAllEvents(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return eventService.getAllEvents(sessionId);
	}
	
	@Path("/{eventId}")
	@GET
	public Event getEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getEvent(sessionId, eventId);
	}

	@Path("/byName/{name}") 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> findByName(@HeaderParam("sessionId") String sessionId, @PathParam("name") String name) throws ServiceException {
		return eventService.findEventByName(sessionId, name);
	}    
	
	@Path("/{eventId}")
	@DELETE
	public void removeEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		eventService.removeEvent(sessionId, eventId);
	}
	
	@Path("/activity/{eventId}/{type}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Activity> getByEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("type") String type) throws ServiceException {
		ActivityType t = null;
		if(type!=null){
    		if(type.toLowerCase().contentEquals("tournament")) t=ActivityType.Tournament;
    		if(type.toLowerCase().contentEquals("production")) t=ActivityType.Production;
    		if(type.toLowerCase().contentEquals("conference")) t=ActivityType.Conference;
		}
		return eventService.getActivitiesByEvent(sessionId, eventId, t);
	}
	
	@Path("/sponsor/{sponsorId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Sponsor> getSponsorsByEvent(@HeaderParam("sessionId") String sessionId, @PathParam("sponsorId") int sponsorId) throws ServiceException {
		return eventService.getSponsorsByEvent(sessionId,sponsorId);
	}
}

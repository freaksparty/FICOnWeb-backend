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
import es.ficonlan.web.backend.model.eventservice.EventService;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;


/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo
 */
@Path("activity")
public class ActivityResource {
	
	private EventService eventService;
	
	public ActivityResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
	}
	
	//EL evento alque va referido viene en el cuerpo de Activity
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Activity addActivity(@HeaderParam("sessionId") String sessionId, Activity activity) throws ServiceException {
		return eventService.addActivity(sessionId, activity.getEvent().getEventId(), activity);
	}
	
	@Path("/{activityId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Activity changeData(@HeaderParam("sessionId") String sessionId, @PathParam("activityId") int activityId, Activity activityData) throws ServiceException {
		return eventService.changeActivityData(sessionId, activityId, activityData);
	}
	
	@Path("/{activityId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Activity getActivity(@HeaderParam("sessionId") String sessionId, @PathParam("activityId") int activityId) throws ServiceException {
		return eventService.getActivity(sessionId, activityId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Activity> getAllActivities(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return eventService.getAllActivities(sessionId);
	}
	
	@Path("/{activityId}")
	@DELETE
	public void removeActivity(@HeaderParam("sessionId") String sessionId, @PathParam("activityId") int activityId) throws ServiceException {
			eventService.removeActivity(sessionId, activityId);
	}
	
	@Path("/user/{activityId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getActivityParticipants(@HeaderParam("sessionId") String sessionId,  @PathParam("activityId") int activityId) throws ServiceException {
			return eventService.getActivityParticipants(sessionId, activityId);
	}
	
	@Path("/user/{activityId}/{userId}")
	@PUT
	public void addParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, @PathParam("activityId") int activityId) throws ServiceException {
		eventService.addParticipantToActivity(sessionId, userId, activityId);
	}
	    
	@Path("/user/{activityId}/{userId}")
	@DELETE
	public void removeParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, @PathParam("activityId") int activityId) throws ServiceException {
		eventService.removeParticipantFromActivity(sessionId, userId, activityId);
	}
}

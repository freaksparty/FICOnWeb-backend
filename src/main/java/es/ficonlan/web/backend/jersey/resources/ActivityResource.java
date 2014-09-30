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
import es.ficonlan.web.backend.model.eventservice.EventService;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;


/**
 * @author Daniel Gómez Silva
 */
@Path("activity")
public class ActivityResource {
	
	private EventService eventService;
	
	public ActivityResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
	}
	
	
	@Path("/{eventId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Activity addActivity(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, Activity activity) throws ServiceException {
		return eventService.addActivity(sessionId, eventId, activity);
	}
	
	@Path("/{activityId}")
	@DELETE
	public void removeActivity(@HeaderParam("sessionId") String sessionId, @PathParam("activityId") int activityId) throws ServiceException {
			eventService.removeActivity(sessionId, activityId);
	}
	
	@Path("/changeData")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void changeData(@HeaderParam("sessionId") String sessionId, Activity activityData) throws ServiceException {
		eventService.changeActivityData(sessionId, activityData);
	}
	
	@Path("/setOrganizer/{activityId}/{userId}")
	@POST
	public void setOrganizer(@HeaderParam("sessionId") String sessionId, @PathParam("activityId") int activityId,  @PathParam("userId") int userId) throws ServiceException {
		eventService.setActivityOrganizer(sessionId, activityId, userId);
	}
	
	@Path("/getByEvent/{eventId}/{type}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Activity> getByEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("type") String type) throws ServiceException {
		ActivityType t = null;
		if(type!=null){
    		if(type.toLowerCase().contentEquals("tournament"))  t=ActivityType.Tournament;
    		if(type.toLowerCase().contentEquals("production")) t=ActivityType.Production;
    		if(type.toLowerCase().contentEquals("conference")) t=ActivityType.Conference;
		}
		return eventService.getActivitiesByEvent(sessionId, eventId, t);
	}
	
	@Path("/addParticipant/{activityId}/{userId}")
	@POST
	public void addParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, @PathParam("activityId") int activityId) throws ServiceException {
		eventService.addParticipantToActivity(sessionId, userId, activityId);
	}
	    
	@Path("/removeParticipant/{activityId}/{userId}")
	@POST
	public void removeParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, @PathParam("activityId") int activityId) throws ServiceException {
		eventService.removeParticipantFromActivity(sessionId, userId, activityId);
	}
	
	@Path("/getParticipants/{activityId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getActivityParticipants(@HeaderParam("sessionId") String sessionId,  @PathParam("activityId") int activityId) throws ServiceException {
			return eventService.getActivityParticipants(sessionId, activityId);
	}

}

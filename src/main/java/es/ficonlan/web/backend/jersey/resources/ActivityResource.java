package es.ficonlan.web.backend.jersey.resources;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.annotations.UseCasePermission;
import es.ficonlan.web.backend.entities.Activity;
import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.services.eventservice.EventService;
import es.ficonlan.web.backend.util.cache.SimpleMemCache;


/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo
 * @author Siro González <xiromoreira>
 */
@Path("activity")
@Singleton
public class ActivityResource {
	
	CacheControl sharedCacheControl;
	
	@Autowired
	private EventService eventService;
	
	public ActivityResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
	}
	
	@PUT
	@Path("/{activityId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	@UseCasePermission("changeActivityData")
	public Activity editActivity(@PathParam("activityId") int activityId, Activity activityData) throws ServiceException {
		return eventService.changeActivityData(activityId, activityData);
	}
	
	@GET
	@Path("/{activityId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UseCasePermission("getEvent")
	public Activity getActivity(@PathParam("activityId") int activityId) throws ServiceException {
		Activity a = eventService.getActivity(activityId);
		return a;
	}
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Activity> getAllActivities(@HeaderParam("sessionId") String sessionId) throws ServiceException {
//		return eventService.getAllActivities(sessionId);
//	}
	
	@DELETE
	@Path("/{activityId}")
	public void removeActivity(@HeaderParam("sessionId") String sessionId, @PathParam("activityId") int activityId) throws ServiceException {
		eventService.removeActivity(sessionId, activityId);
	}
	
	@GET
	@Path("/user/{activityId}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getActivityParticipants(@HeaderParam("sessionId") String sessionId,  @PathParam("activityId") int activityId) throws ServiceException {
			return eventService.getActivityParticipants(sessionId, activityId);
	}
	
	@PUT
	@Path("/user/{activityId}/{userId}")
	public void addParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, @PathParam("activityId") int activityId) throws ServiceException {
		eventService.addParticipantToActivity(sessionId, userId, activityId);
	}
	    
	@DELETE
	@Path("/user/{activityId}/{userId}")
	public void removeParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, @PathParam("activityId") int activityId) throws ServiceException {
		eventService.removeParticipantFromActivity(sessionId, userId, activityId);
	}
}

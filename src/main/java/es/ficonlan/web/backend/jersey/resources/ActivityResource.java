package es.ficonlan.web.backend.jersey.resources;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
//	private CacheControl getCacheControl(Cacheable obj) {
//		if(obj.timeToExpire() > 0 && obj.timeToExpire() < sharedCacheControl.getMaxAge()) {
//			CacheControl cc = new CacheControl();
//			cc.setMaxAge((int)obj.timeToExpire());
//			return cc;
//		} else {
//			return sharedCacheControl;
//		}
//	}
	SimpleMemCache<Integer, Activity> activityCache;
	
	@Autowired
	private EventService eventService;
	
	public ActivityResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
		this.activityCache = new SimpleMemCache<Integer, Activity>(20);
	}
	
	@Path("/{activityId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Activity changeData(@HeaderParam("sessionId") String sessionId, @PathParam("activityId") int activityId, Activity activityData) throws ServiceException {
		activityCache.remove(activityId);
		return eventService.changeActivityData(sessionId, activityId, activityData);
	}

	@Path("/{activityId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@UseCasePermission("getEvent")
	public Activity getActivity(@PathParam("activityId") int activityId) throws ServiceException {
		Activity a = activityCache.get(activityId);
		if(a == null) {
			a = eventService.getActivity(activityId);
			if(a!=null)
				activityCache.insert(activityId, a);
		}
		return a;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Activity> getAllActivities(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return eventService.getAllActivities(sessionId);
	}
	
	@Path("/{activityId}")
	@DELETE
	public void removeActivity(@HeaderParam("sessionId") String sessionId, @PathParam("activityId") int activityId) throws ServiceException {
		activityCache.remove(activityId);
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

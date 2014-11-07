package es.ficonlan.web.backend.jersey.resources;

import java.util.Calendar;
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

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.activity.Activity;
import es.ficonlan.web.backend.model.activity.Activity.ActivityType;
import es.ficonlan.web.backend.model.emailservice.EmailService;
import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.eventservice.EventService;
import es.ficonlan.web.backend.model.newsitem.NewsItem;
import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.sponsor.Sponsor;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("event")
public class EventResource {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	public EventResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
		this.emailService = ApplicationContextProvider.getApplicationContext().getBean(EmailService.class);
		this.userService  = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
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
	
	@Path("/activity/{eventId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Activity addActivity(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, Activity activity) throws ServiceException {
		return eventService.addActivity(sessionId, eventId, activity);
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
	
	@Path("/sponsor/{eventId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Sponsor addSponsor(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, Sponsor sponsor) throws ServiceException {
		return eventService.addSponsor(sessionId,eventId,sponsor);
	}
	
	@Path("/sponsor/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Sponsor> getSponsorsByEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getSponsorsByEvent(sessionId,eventId);
	}

	@Path("/users/{eventId}/{state}/{statrtIndex}/{maxResults}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getByEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("state") String state,  
			                     @PathParam("startIndex") int startIndex,  @PathParam("maxResults") int maxResults) throws ServiceException {
		RegistrationState st;
		if(state==null) throw new ServiceException(ServiceException.MISSING_FIELD,"state");
    	if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    	else if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    	else if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
    	else if(state.toLowerCase().contentEquals("all")) st=null;
    	else throw new ServiceException(ServiceException.INCORRECT_FIELD,"state");
		return userService.getUsersByEvent(sessionId, eventId, st, startIndex, maxResults);
	}
	
	@Path("/news/{eventId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public NewsItem add(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, NewsItem newsItem) throws ServiceException{
		return eventService.addNews(sessionId, eventId, newsItem);
	}

	
	@Path("/news/{eventId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<NewsItem> getAllNewsItem(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getAllNewsItemFormEvent(sessionId,  eventId);
	}
	
	@Path("/news/{eventId}/last/{days}/{outstandingOnly}")
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public List<NewsItem> lastNews(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("days") int days, @PathParam("outstandingOnly") boolean outstandingOnly) throws ServiceException{
		Calendar limitDate = Calendar.getInstance();
		limitDate.add(Calendar.DAY_OF_YEAR, -1*days);
		return eventService.getLastNewsFromEvent(sessionId, eventId, limitDate, outstandingOnly);
	}
	
	@Path("/emailTemplates/setPaidTemplate/{eventId}/{emailTemplateId}")
	@PUT
	public void setPaidTemplate (@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("emailTemplateId") int emailTemplateId) throws ServiceException {
		eventService.setPaidTemplate(sessionId, eventId, emailTemplateId);
	}
	
	@Path("/emailTemplates/onQueueTemplate/{eventId}/{emailTemplateId}")
	@PUT
	public void onQueueTemplate (@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("emailTemplateId") int emailTemplateId) throws ServiceException {
		eventService.onQueueTemplate(sessionId, eventId, emailTemplateId);
	}
	
	@Path("/emailTemplates/outstandingTemplate/{eventId}/{emailTemplateId}")
	@PUT
	public void outstandingTemplate (@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("emailTemplateId") int emailTemplateId) throws ServiceException {
		eventService.outstandingTemplate(sessionId, eventId, emailTemplateId);
	}
	
	@Path("/emailTemplates/outOfDateTemplate/{eventId}/{emailTemplateId}")
	@PUT
	public void outOfDateTemplate (@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("emailTemplateId") int emailTemplateId) throws ServiceException {
		eventService.outOfDateTemplate(sessionId, eventId, emailTemplateId);
	}
	
	@Path("/emailTemplates/fromQueueToOutstanding/{eventId}/{emailTemplateId}")
	@PUT
	public void fromQueueToOutstanding (@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("emailTemplateId") int emailTemplateId) throws ServiceException {
		eventService.fromQueueToOutstanding(sessionId, eventId, emailTemplateId);
	}
}

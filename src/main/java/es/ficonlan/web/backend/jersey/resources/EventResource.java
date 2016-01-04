package es.ficonlan.web.backend.jersey.resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.annotations.UseCasePermission;
import es.ficonlan.web.backend.entities.Activity;
import es.ficonlan.web.backend.entities.Event;
import es.ficonlan.web.backend.entities.NewsItem;
import es.ficonlan.web.backend.entities.Sponsor;
import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.entities.Activity.ActivityType;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.output.EventData;
import es.ficonlan.web.backend.output.NewsList;
import es.ficonlan.web.backend.services.emailservice.EmailService;
import es.ficonlan.web.backend.services.eventservice.EventService;
import es.ficonlan.web.backend.services.userservice.UserService;
import es.ficonlan.web.backend.util.ActivityHeader;
import es.ficonlan.web.backend.util.RegistrationData;
import es.ficonlan.web.backend.util.ShirtData;
import es.ficonlan.web.backend.util.cache.Cacheable;
import es.ficonlan.web.backend.util.cache.SimpleMemCache;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 * @author Siro González <xiromoreira>
 */
@Path("event")
@Singleton
public class EventResource {
	
	private String[] s1 = {"newsItemId","title","imageurl","creationDate","publishDate","publisher.login","event"};
	private ArrayList<String> l1;
	
	private String[] s2 = {"userId","name","login","dni","email","phoneNumber","shirtSize","dob"};
	private ArrayList<String> l2;
	
	private String[] s3 = {"login","dni","registrationId","userID","eventID","state","registrationDate","paidDate","paid","place","placeOnQueue"};
	private ArrayList<String> l3;
	
	private String[] s4 = {"activityId","event","name","type","startDate","endDate"};
	private ArrayList<String> l4;
	
	ArrayList<EventData> eventCache;
	SimpleMemCache<String, NewsList> newsCache;
	
	CacheControl sharedCacheControl;
	private CacheControl getCacheControl(Cacheable obj) {
		if(obj.timeToExpire() > 0 && obj.timeToExpire() < sharedCacheControl.getMaxAge()) {
			CacheControl cc = new CacheControl();
			cc.setMaxAge((int)obj.timeToExpire());
			return cc;
		} else {
			return sharedCacheControl;
		}
	}
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	public EventResource(){
		eventCache = new ArrayList<EventData>(3);
		newsCache = new SimpleMemCache<String, NewsList>(5);
		
		sharedCacheControl = new CacheControl();
	    sharedCacheControl.setMaxAge(300);
		
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
		this.emailService = ApplicationContextProvider.getApplicationContext().getBean(EmailService.class);
		this.userService  = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
		
		l1 = new ArrayList<String>();
		l1.add(s1[0]);l1.add(s1[1]);l1.add(s1[2]);l1.add(s1[3]);l1.add(s1[4]);l1.add(s1[5]);l1.add(s1[6]);
		
		l2 = new ArrayList<String>();
		l2.add(s2[0]);l2.add(s2[1]);l2.add(s2[2]);l2.add(s2[3]);l2.add(s2[4]);l2.add(s2[5]);l2.add(s2[6]);l2.add(s2[7]);
		
		l3 = new ArrayList<String>();
		l3.add(s3[0]);l3.add(s3[1]);l3.add(s3[2]);l3.add(s3[3]);l3.add(s3[4]);l3.add(s3[5]);l3.add(s3[6]);l3.add(s3[7]);l3.add(s3[8]);l3.add(s3[9]);l3.add(s3[10]);
		
		l4 = new ArrayList<String>();
		l4.add(s4[0]);l4.add(s4[1]);l4.add(s4[2]);l4.add(s4[3]);l4.add(s4[4]);l4.add(s4[5]);
	}

	@GET
	@Path("/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UseCasePermission("getEvent")
	public Response getEvent(@PathParam("eventId") int eventId, @Context Request request) {
		
		EventData result = null;
		
		for(EventData cache : eventCache) {
			if(cache.eventId == eventId) {
				cache.updateIsOpen();
				ResponseBuilder builder = request.evaluatePreconditions(cache.getTag());
				if(builder != null) {
					CacheControl cc;
					builder.cacheControl(getCacheControl(cache));
					return builder.build();
				} else {
					result = cache;
					break;
				}
			}
		}
		
		try {
			//Cache miss
			if(result == null) {
				Event event = eventService.getEvent(eventId);
				result = new EventData(event);
				result.addActivities(eventService.getActivitiesByEvent(eventId, null));
				result.addSponsors(eventService.getSponsorsByEvent(eventId));
				eventCache.add(result);
			}
			CacheControl cc;
			if(result.timeToOpen < sharedCacheControl.getMaxAge() && result.timeToOpen > 0){
				cc = new CacheControl();
				cc.setMaxAge((int)result.timeToOpen);
			} else {
				cc = sharedCacheControl;
			}
			return Response.status(200).entity(result)
					.cacheControl(cc).tag(result.getTag())
					.build();
		} catch (ServiceException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	@UseCasePermission("createEvent")
	public Event createEvent(@HeaderParam("sessionId") String sessionId, Event event) throws ServiceException {
		return eventService.createEvent(event);
	}
	
	@Path("/{eventId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	@UseCasePermission("changeEventData")
	public Event changeData(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, Event eventData) throws ServiceException {
		//Delete cache
		eventCache = new ArrayList<EventData>(3);
		newsCache.clear();
		return eventService.changeEventData(eventId, eventData);
	}

	@Path("/{eventId}/news/published/{page}/{pageTam}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@UseCasePermission("getEvent")
	public Response getNewsPublished(
			@PathParam("eventId") int eventId, 
			@PathParam("page") int page, 
			@PathParam("pageTam") int pageTam, 
			@Context Request request, @Context UriInfo uriInfo) throws ServiceException {
		
		if(pageTam > 0) {
			NewsList result = newsCache.get(uriInfo.getPath());

			if(result != null) {
				ResponseBuilder builder = request.evaluatePreconditions(result.getTag());
				if(builder != null) {
					builder.cacheControl(getCacheControl(result));
					return builder.build();
				}
			} else {			
				//cache miss
				int startIndex = page * pageTam - pageTam;
				int cont = pageTam;
				
				long size = eventService.countPublishedNewsFromEvent(eventId);
				result = new NewsList(eventService.getPublishedNewsForEvent(eventId, startIndex, cont), size);
				Calendar next = eventService.nextNewsUpdate(eventId);
				result.setExpiration(next);
				
			}
			
			return Response.status(200).entity(result)
					.cacheControl(getCacheControl(result))
					.tag(result.getTag()).build();
		}
		else return Response.status(200).build();
		
	}
	
	/*@Path("/{eventId}/{eventData}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEventData(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("eventData") String eventData) {
		try {
			Event e = eventService.getEvent(sessionId, eventId);
			Object o = null;
			
			switch (eventData) {
				case "name" 		: o = e.getName(); break;
				case "minimunAge" 	: o = e.getMinimunAge(); break; 
				case "description"	: o = e.getDescription(); break;
				case "startDate" 	: o = e.getStartDate(); break;
				case "endDate" 		: o = e.getEndDate(); break;
				case "isopen" 		: Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
									o = (now.after(e.getStartDate()) && now.before(e.getEndDate()));
									break;
				case "rules" 		: o = e.getNormas(); break;
			}
			
			return Response.status(200).entity(o).build();
			
		} catch (ServiceException e) {
			return Response.status(400).entity(e.toString()).build();
		}
	}*/
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getAllEvents(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return eventService.getAllEvents(sessionId);
	}
	
	/*@Path("/{eventId}")
	@GET
	public Event getEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getEvent(sessionId, eventId);
	}*/

	/*@Path("/byName/{name}") 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> findByName(@HeaderParam("sessionId") String sessionId, @PathParam("name") String name) throws ServiceException {
		return eventService.findEventByName(sessionId, name);
	}*/  
	
	@Path("/{eventId}")
	@DELETE
	public void removeEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		//Delete cache
		eventCache = new ArrayList<EventData>(3);
		newsCache.clear();
		eventService.removeEvent(sessionId, eventId);
	}
	
	/*@Path("/rules/{eventId}")
	@GET
	public String getEventRules(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getEventRules(sessionId, eventId);
	}*/
	
	@Path("/resgistrationIsOpen/{eventId}")
	@GET
	@UseCasePermission("getEvent")
	public boolean eventIsOpen(@PathParam("eventId") int eventId) throws ServiceException {
		return eventService.eventIsOpen(eventId);
	}
	
	@Path("/{eventId}/activity")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Activity addActivity(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, Activity activity) throws ServiceException {
		//Delete cache
		eventCache = new ArrayList<EventData>(3);
		return eventService.addActivity(sessionId, eventId, activity);
	}
	
	/*@Path("/activity{eventId}/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Activity> getByEvent(@HeaderParam("sessionId") String sessionId, 
			@PathParam("eventId") int eventId,
			@DefaultValue("1") @QueryParam("page") int page, 
			@DefaultValue("0") @QueryParam("pageTam") int pageTam,
			@DefaultValue("activityId") @QueryParam("orderBy") String orderBy,
			@DefaultValue("1") @QueryParam("desc") int desc,
			@DefaultValue(" ") @QueryParam("type") String type
			) throws ServiceException {
		if(l4.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		ActivityType t = null;
		if(type!=null){
    		if(type.toLowerCase().contentEquals("tournament")) t=ActivityType.Tournament;
    		if(type.toLowerCase().contentEquals("production")) t=ActivityType.Production;
    		if(type.toLowerCase().contentEquals("conference")) t=ActivityType.Conference;
		}
		return eventService.getActivitiesByEvent(sessionId, eventId, startIndex, cont, orderBy, b, t);
	}*/
	
	@Path("/{eventId}/activityHeaders/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<ActivityHeader> getActivityHeaderByEvent(@HeaderParam("sessionId") String sessionId, 
			@PathParam("eventId") int eventId,
			@DefaultValue("1") @QueryParam("page") int page, 
			@DefaultValue("0") @QueryParam("pageTam") int pageTam,
			@DefaultValue("activityId") @QueryParam("orderBy") String orderBy,
			@DefaultValue("1") @QueryParam("desc") int desc,
			@DefaultValue(" ") @QueryParam("type") String type
			) throws ServiceException {
		if(l4.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		ActivityType t = null;
		if(type!=null){
    		if(type.toLowerCase().contentEquals("tournament")) t=ActivityType.Tournament;
    		if(type.toLowerCase().contentEquals("production")) t=ActivityType.Production;
    		if(type.toLowerCase().contentEquals("conference")) t=ActivityType.Conference;
		}
		return (List<ActivityHeader>) eventService.getActivitiesByEvent(sessionId, eventId, startIndex, cont, orderBy, b, t).stream().map(Activity::generateActivityHeader).collect(Collectors.toList());
	}
	
	@Path("/{eventId}/activityTAM/{type}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public long getActivityByEventTAM(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("type") String type) throws ServiceException {
		ActivityType t = null;
		if(type!=null){
    		if(type.toLowerCase().contentEquals("tournament")) t=ActivityType.Tournament;
    		if(type.toLowerCase().contentEquals("production")) t=ActivityType.Production;
    		if(type.toLowerCase().contentEquals("conference")) t=ActivityType.Conference;
		}
		return eventService.getActivitiesByEventTAM(sessionId, eventId, t);
	}
	
	@Path("/sponsor/{eventId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Sponsor addSponsor(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, Sponsor sponsor) throws ServiceException {
		eventCache.clear();
		return eventService.addSponsor(sessionId,eventId,sponsor);
	}
	
	@Path("/{eventId}/sponsors")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@UseCasePermission("getEvent")
	public List<Sponsor> getSponsorsByEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getSponsorsByEvent(eventId);
	}

	@Path("/getShirtSizes/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<ShirtData> getShirtSizes(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getShirtSizes(sessionId, eventId);
	}
	
	@Path("/users/{eventId}/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getUsersByEvent(@HeaderParam("sessionId") String sessionId,  
			@PathParam("eventId") int eventId,
			@DefaultValue("1") @QueryParam("page") int page, 
			@DefaultValue("0") @QueryParam("pageTam") int pageTam,
			@DefaultValue("userId") @QueryParam("orderBy") String orderBy,
			@DefaultValue("1") @QueryParam("desc") int desc,
			@DefaultValue("all") @QueryParam("state") String state
			) throws ServiceException {
		if(l2.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
		RegistrationState st;
		if(state==null) throw new ServiceException(ServiceException.MISSING_FIELD,"state");
    	if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    	else if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    	else if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
    	else if(state.toLowerCase().contentEquals("all")) st=null;
    	else throw new ServiceException(ServiceException.INCORRECT_FIELD,"state");
    	int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		return userService.getUsersByEvent(sessionId, eventId, st, startIndex,cont,orderBy,b);
	}
	
	@Path("/registrations/{eventId}/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<RegistrationData> getRegistrationByEvent(@HeaderParam("sessionId") String sessionId,  
			@PathParam("eventId") int eventId,
			@DefaultValue("1") @QueryParam("page") int page, 
			@DefaultValue("0") @QueryParam("pageTam") int pageTam,
			@DefaultValue("registrationDate") @QueryParam("orderBy") String orderBy,
			@DefaultValue("1") @QueryParam("desc") int desc,
			@DefaultValue("all") @QueryParam("state") String state
			) throws ServiceException {
		if(l3.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
		RegistrationState st;
		if(state==null) throw new ServiceException(ServiceException.MISSING_FIELD,"state");
    	if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    	else if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    	else if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
    	else if(state.toLowerCase().contentEquals("all")) st=null;
    	else throw new ServiceException(ServiceException.INCORRECT_FIELD,"state");
    	int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		String ord = orderBy;
		switch(orderBy) {
			case "login" 			: ord = "user.login";	break;
			case "dni"				: ord = "user.dni";		break;
		}
		
		return eventService.getRegistrationByEvent(sessionId, eventId, st, startIndex, cont, ord, b);
	}
	
	@Path("/registrations/size/{eventId}/{state}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public long getRegistrationByEventTAM(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("state") String state) throws ServiceException {
		
		RegistrationState st = null;
		if(state==null) throw new ServiceException(ServiceException.MISSING_FIELD,"state");
    	if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    	else if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    	else if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
    	else if(state.toLowerCase().contentEquals("all")) st=null;
    	
		return eventService.getRegistrationByEventTAM(sessionId, eventId, st);
	}
	
	@Path("/users/size/{eventId}/{state}/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public long getUsersByEventTAM(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("state") String state) throws ServiceException {
		RegistrationState st;
		if(state==null) throw new ServiceException(ServiceException.MISSING_FIELD,"state");
    	if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    	else if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    	else if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
    	else if(state.toLowerCase().contentEquals("all")) st=null;
    	else throw new ServiceException(ServiceException.INCORRECT_FIELD,"state");
		return userService.getUsersByEventTAM(sessionId, eventId, st);
	}
	
			
	@Path("/news/{eventId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public NewsItem add(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, NewsItem newsItem) throws ServiceException{
		return eventService.addNews(sessionId, eventId, newsItem);
	}

	
	@Path("/{eventId}/news/query")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<NewsItem> getAllNewsItem(@HeaderParam("sessionId") String sessionId, 
			@PathParam("eventId") int eventId,
			@DefaultValue("1") @QueryParam("page") int page, 
			@DefaultValue("0") @QueryParam("pageTam") int pageTam,
			@DefaultValue("publishDate") @QueryParam("orderBy") String orderBy,
			@DefaultValue("1") @QueryParam("desc") int desc
			) throws ServiceException {
		if(l1.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		return eventService.getAllNewsItemFormEvent(sessionId,eventId,startIndex,cont,orderBy,b);
	}
	
	/*@Path("/news/published/{eventId}/{page}/{pageTam}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllNewsPublishedItem(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("page") int page, @PathParam("pageTam") int pageTam) throws ServiceException {
		if(pageTam>0) {
			int startIndex = page*pageTam - pageTam;
			int cont = pageTam;
			return Response.status(200).entity(eventService.getAllPublishedNewsItemFormEvent(sessionId,eventId,startIndex,cont)).build();
		}
		else return Response.status(200).entity(eventService.getAllPublishedNewsItemFormEvent(sessionId,eventId,0,(int) eventService.getAllPublishedNewsItemFromEventTam(sessionId, eventId))).build();
		
	}
	
	@Path("/news/published/size/{eventId}/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public long getAllPublishedNewsItemFromEventTam(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getAllPublishedNewsItemFromEventTam(sessionId,eventId);
	}*/
	
	@Path("/news/all/size/{eventId}/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public long getAllNewsItemFromEventTam(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getAllNewsItemFromEventTam(sessionId,eventId);
	}
	
	@Path("/news/{eventId}/last/{days}")
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public List<NewsItem> lastNews(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("days") int days) throws ServiceException{
		Calendar limitDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		limitDate.add(Calendar.DAY_OF_YEAR, -1*days);
		return eventService.getLastNewsFromEvent(sessionId, eventId, limitDate, false);
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

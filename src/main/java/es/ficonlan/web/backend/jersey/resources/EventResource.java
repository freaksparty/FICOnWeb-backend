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
import es.ficonlan.web.backend.dao.EmailTemplateDao.TypeEmail;
import es.ficonlan.web.backend.entities.Activity;
import es.ficonlan.web.backend.entities.Activity.ActivityType;
import es.ficonlan.web.backend.entities.EmailTemplate;
import es.ficonlan.web.backend.entities.Event;
import es.ficonlan.web.backend.entities.NewsItem;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;
import es.ficonlan.web.backend.entities.Sponsor;
import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.output.EmailTemplatesForEvent;
import es.ficonlan.web.backend.output.EventData;
import es.ficonlan.web.backend.output.NewsList;
import es.ficonlan.web.backend.output.ShirtData;
import es.ficonlan.web.backend.services.emailservice.EmailService;
import es.ficonlan.web.backend.services.eventservice.EventService;
import es.ficonlan.web.backend.services.userservice.UserService;
import es.ficonlan.web.backend.util.ActivityHeader;
import es.ficonlan.web.backend.util.RegistrationData;
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
	
	private final String[] s1 = {"newsItemId","title","imageurl","creationDate","publishDate","publisher.login","event"};
	private final ArrayList<String> l1;
	
	private final String[] s2 = {"userId","name","login","dni","email","phoneNumber","shirtSize","dob"};
	private final ArrayList<String> l2;
	
	private final String[] s3 = {"login","dni","registrationId","userID","eventID","state","registrationDate","paidDate","paid","place","placeOnQueue"};
	private final ArrayList<String> l3;
	
	private final String[] s4 = {"activityId","event","name","type","startDate","endDate"};
	private final ArrayList<String> l4;
	
	//TODO: implement this in a proper way
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
	private final EventService eventService;
	
	@Autowired
	private final EmailService emailService;
	
	@Autowired
	private final UserService userService;
	
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
	@UseCasePermission("getEvent")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEvent(@PathParam("eventId") int eventId, @Context Request request) {
		
		EventData result = null;
		
		for(EventData cache : eventCache) {
			if(cache.eventId == eventId) {
				cache.updateIsOpen();
				ResponseBuilder builder = request.evaluatePreconditions(cache.getTag());
				if(builder != null) {
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
	@UseCasePermission("createEvent")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Event createEvent(@HeaderParam("sessionId") String sessionId, Event event) throws ServiceException {
		return eventService.createEvent(event);
	}
	
	@PUT
	@Path("/{eventId}")
	@UseCasePermission("changeEventData")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Event changeData(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, Event eventData) throws ServiceException {
		//Delete cache
		eventCache = new ArrayList<EventData>(3);
		newsCache.clear();
		return eventService.changeEventData(eventId, eventData);
	}

	@GET
	@Path("/{eventId}/news/published/{page}/{pageTam}")
	@UseCasePermission("getEvent")
	@Produces(MediaType.APPLICATION_JSON)
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
				newsCache.insert(uriInfo.getPath(), result);
			}
			
			return Response.status(200).entity(result)
					.cacheControl(getCacheControl(result))
					.tag(result.getTag()).build();
		}
		else return Response.status(200).build();
		
	}
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getAllEvents(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return eventService.getAllEvents(sessionId);
	}
		
	@DELETE
	@Path("/{eventId}")
	public void removeEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		//Delete cache
		eventCache = new ArrayList<EventData>(3);
		newsCache.clear();
		eventService.removeEvent(sessionId, eventId);
	}
	
	@POST
	@Path("/{eventId}/activity")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	@UseCasePermission("addActivity")
	public Activity addActivity(@PathParam("eventId") int eventId, Activity activity) throws ServiceException {
		//Delete cache
		eventCache = new ArrayList<EventData>(3);
		return eventService.addActivity(eventId, activity);
	}
	
	@GET
	@Path("/{eventId}/activityHeaders/query")
	@UseCasePermission("getAllActivities")
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
		return eventService.getActivitiesByEvent(eventId, startIndex, cont, orderBy, b, t).stream().map(Activity::generateActivityHeader).collect(Collectors.toList());
	}
	
	@GET
	@Path("/{eventId}/activityTAM/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	@UseCasePermission("getAllActivities")
	public long getActivityByEventTAM(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("type") String type) throws ServiceException {
		ActivityType t = null;
		if(type!=null){
    		if(type.toLowerCase().contentEquals("tournament")) t=ActivityType.Tournament;
    		if(type.toLowerCase().contentEquals("production")) t=ActivityType.Production;
    		if(type.toLowerCase().contentEquals("conference")) t=ActivityType.Conference;
		}
		return eventService.getActivitiesByEventTAM(eventId, t);
	}
	
	@POST
	@Path("/sponsor/{eventId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Sponsor addSponsor(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, Sponsor sponsor) throws ServiceException {
		eventCache.clear();
		return eventService.addSponsor(sessionId,eventId,sponsor);
	}
	
	@GET
	@Path("/{eventId}/sponsors")
	@Produces({MediaType.APPLICATION_JSON})
	@UseCasePermission("getEvent")
	public List<Sponsor> getSponsorsByEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getSponsorsByEvent(eventId);
	}

	@GET
	@Path("/{eventId}/getShirtSizes")
	@UseCasePermission("getRegistrationByEvent")
	@Produces({MediaType.APPLICATION_JSON})
	public List<ShirtData> getShirtSizes(@PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getShirtSizes(eventId);
	}
	
	@GET
	@Path("/users/{eventId}/query")
	@UseCasePermission("getAllUsers")
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
		return userService.getUsersByEvent(eventId, st, startIndex,cont,orderBy,b);
	}
	
	@GET
	@Path("/registrations/{eventId}/query")
	@UseCasePermission("getRegistrationByEvent")
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
		
		return eventService.getRegistrationByEvent(eventId, st, startIndex, cont, ord, b);
	}
	
	@GET
	@Path("/{eventId}/timeToOpen")
	@UseCasePermission("getEvent")
	@Produces({MediaType.APPLICATION_JSON})
	public long timeToOpen(@PathParam("eventId") int eventId) {
		for(EventData cache : eventCache) {
			if(cache.eventId == eventId) {
				return cache.getTimeToOpen();
			}
		}
		
		Event event;
		try {
			event = eventService.getEvent(eventId);
		} catch (ServiceException e) {
			return -1;
		}
		
		if(event == null || event.getRegistrationOpenDate() == null) {
			return -1;
		} else {
			Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			return (now.getTimeInMillis() - event.getRegistrationOpenDate().getTimeInMillis()) / 1000;
		}
		
	}
	
	@GET
	@Path("/registrations/size/{eventId}/{state}")
	@UseCasePermission("getRegistrationByEvent")
	@Produces({MediaType.APPLICATION_JSON})
	public long getRegistrationByEventTAM(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("state") String state) throws ServiceException {
		
		RegistrationState st = null;
		if(state==null) throw new ServiceException(ServiceException.MISSING_FIELD,"state");
    	if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    	else if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    	else if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
    	else if(state.toLowerCase().contentEquals("all")) st=null;
    	
		return eventService.getRegistrationByEventTAM(eventId, st);
	}
	
	@GET
	@Path("/users/size/{eventId}/{state}/")
	@UseCasePermission("getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public long getUsersByEventTAM(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("state") String state) throws ServiceException {
		RegistrationState st;
		if(state==null) throw new ServiceException(ServiceException.MISSING_FIELD,"state");
    	if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    	else if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    	else if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
    	else if(state.toLowerCase().contentEquals("all")) st=null;
    	else throw new ServiceException(ServiceException.INCORRECT_FIELD,"state");
		return userService.getUsersByEventTAM(eventId, st);
	}
		
	@POST
	@Path("/news/{eventId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public NewsItem add(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, NewsItem newsItem) throws ServiceException{
		newsCache.clear();
		return eventService.addNews(sessionId, eventId, newsItem);
	}

	
	@GET
	@Path("/{eventId}/news/query")
	@UseCasePermission("getAllNewsItem")
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
		return eventService.getAllNewsItemFormEvent(eventId, startIndex, cont, orderBy, b);
	}
		
	@GET
	@Path("/news/all/size/{eventId}/")
	@UseCasePermission("getAllNewsItem")
	@Produces(MediaType.APPLICATION_JSON)
	public long getAllNewsItemFromEventTam(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return eventService.getAllNewsItemFromEventTam(eventId);
	}
	
//	@GET
//	@Path("/news/{eventId}/last/{days}")
//	@Consumes({MediaType.APPLICATION_JSON})
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<NewsItem> lastNews(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("days") int days) throws ServiceException{
//		Calendar limitDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//		limitDate.add(Calendar.DAY_OF_YEAR, -1*days);
//		return eventService.getLastNewsFromEvent(sessionId, eventId, limitDate, false);
//	}
	
	@GET
	@Path("/{eventId}/getEmailTemplates")
	@UseCasePermission("changeEventData")
	@Produces(MediaType.APPLICATION_JSON)
	public EmailTemplatesForEvent getEmailTemplates(@PathParam("eventId") int eventId) throws ServiceException {
		EmailTemplatesForEvent result = new EmailTemplatesForEvent();
		//Event ev = eventService.getEvent(eventId);
		
		EmailTemplate template;
		try {
			template = emailService.findEmailTemplateForEvent(eventId, TypeEmail.ON_QUEUE);
			result.contentOnQueue = template.getContenido();
			result.subjectOnQueue = template.getAsunto();
			result.idOnQueue = template.getEmailtemplateid();
		
			template = emailService.findEmailTemplateForEvent(eventId, TypeEmail.PENDING_CONFIRMATION_FROM_QUEUE);
			result.contentPendingConfirmationFromQueue = template.getContenido();
			result.subjectPendingConfirmationFromQueue = template.getAsunto();
			result.idPendingConfirmationFromQueue = template.getEmailtemplateid();
	
			template = emailService.findEmailTemplateForEvent(eventId, TypeEmail.PENDING_CONFIRMATION_DIRECT);
			result.contentPendingConfirmationDirect = template.getContenido();
			result.subjectPendingConfirmationDirect = template.getAsunto();
			result.idPendingConfirmationDirect = template.getEmailtemplateid();
	
			template = emailService.findEmailTemplateForEvent(eventId, TypeEmail.CONFIRMATION_TIME_EXPIRED);
			result.contentConfirmationPeriodExpired = template.getContenido();
			result.subjectConfirmationPeriodExpired = template.getAsunto();
			result.idConfirmationPeriodExpired = template.getEmailtemplateid();
	
			template = emailService.findEmailTemplateForEvent(eventId, TypeEmail.SPOT_IS_CONFIRMED);
			result.contentSpotConfirmed = template.getContenido();
			result.subjectSpotConfirmed = template.getAsunto();
			result.idSpotConfirmed = template.getEmailtemplateid();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	
}

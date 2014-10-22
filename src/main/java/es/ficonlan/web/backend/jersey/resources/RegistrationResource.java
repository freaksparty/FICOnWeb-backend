package es.ficonlan.web.backend.jersey.resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.eventservice.EventService;
import es.ficonlan.web.backend.model.registration.Registration;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("registration")
public class RegistrationResource {
	
	private EventService eventService;
	
	public RegistrationResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
	}
	
	@Path("/{eventId}/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Registration getRegistration(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		return eventService.getRegistration(sessionId, userId, eventId);
	}

}

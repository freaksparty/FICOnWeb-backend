package es.ficonlan.web.backend.jersey.resources;


import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.eventservice.EventService;
import es.ficonlan.web.backend.model.sponsor.Sponsor;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("sponsor")
public class SponsorResource {
	
	@Autowired
	private EventService eventService;
	
	public SponsorResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Sponsor> getSponsors(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return eventService.getSponsors(sessionId);
	}

	@Path("/{sponsorId}")
	@DELETE
	public void removeSponsor(@HeaderParam("sessionId") String sessionId, @PathParam("sponsorId") int sponsorId) throws ServiceException {
		eventService.removeSponsor(sessionId, sponsorId);
	}
}

package es.ficonlan.web.backend.jersey.resources;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.entities.Sponsor;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.services.eventservice.EventService;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("sponsor")
public class SponsorResource {
	
	private String[] s = {"sponsorId","name","url","imageurl","event"};
	private ArrayList<String> l;
	
	@Autowired
	private EventService eventService;
	
	public SponsorResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
		l = new ArrayList<String>();
		l.add(s[0]);l.add(s[1]);l.add(s[2]);l.add(s[3]);
	}

	@Path("/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Sponsor> getSponsors(@HeaderParam("sessionId") String sessionId,
			@DefaultValue("1") @QueryParam("page") int page, 
			@DefaultValue("0") @QueryParam("pageTam") int pageTam,
			@DefaultValue("sponsorId") @QueryParam("orderBy") String orderBy,
			@DefaultValue("1") @QueryParam("desc") int desc
			) throws ServiceException {
		if(l.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		return eventService.getSponsors(sessionId, startIndex, cont, orderBy, b);
	}
	
	@Path("/size")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public long getAllTAM(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return eventService.getSponsorsTAM(sessionId);

	}

	@Path("/{sponsorId}")
	@DELETE
	public void removeSponsor(@HeaderParam("sessionId") String sessionId, @PathParam("sponsorId") int sponsorId) throws ServiceException {
		eventService.removeSponsor(sessionId, sponsorId);
	}
}

package es.ficonlan.web.backend.jersey.resources;

import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.eventservice.EventService;
import es.ficonlan.web.backend.model.newsitem.NewsItem;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 */
@Path("news")
public class NewsResource {
	
		private EventService eventService;
		
		public NewsResource() {
			this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
		}
		
		@Path("/{eventId}/addNews")
		@POST
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces(MediaType.APPLICATION_JSON)
	    public NewsItem add(@HeaderParam("sessionId") long sessionId, @PathParam("eventId") int eventId, NewsItem newsItem) throws ServiceException{
	    	return eventService.addNews(sessionId, eventId, newsItem);
	    }
		
		@Path("/changeData")
		@POST
		@Consumes({MediaType.APPLICATION_JSON})
	    public void changeData(@HeaderParam("sessionId")long sessionId, NewsItem newsData) throws ServiceException{
			eventService.changeNewsData(sessionId, newsData);
	    }
	    
		@Path("/last/{days}/{outstandingOnly}")
		@GET
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces(MediaType.APPLICATION_JSON)
	    public List<NewsItem> lastNews(@HeaderParam("sessionId") long sessionId, @PathParam("days") int days, @PathParam("outstandingOnly") boolean outstandingOnly) throws ServiceException{
			Calendar limitDate = Calendar.getInstance();
			limitDate.add(Calendar.DAY_OF_YEAR, -1*days);
			return eventService.getLastNews(sessionId, limitDate, outstandingOnly);
	    }
	    
		@Path("/remove/{newsItemId}")
		@POST
	    public void remove(@HeaderParam("sessionId") long sessionId, @PathParam("newsItemId") int newsItemId) throws ServiceException{
			eventService.removeNews(sessionId, newsItemId);
	    }

}

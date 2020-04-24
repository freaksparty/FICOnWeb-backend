/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.ficonlan.web.backend.jersey.resources;

import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.entities.NewsItem;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.services.eventservice.EventService;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("news")
public class NewsResource {
	
	@Autowired
	private EventService eventService;
		
	public NewsResource() {
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
	}
	    
	@Path("/{newsItemId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	public void changeNewsData(@HeaderParam("sessionId")String sessionId, @PathParam("newsItemId") int newsItemId,  @PathParam("eventId") int eventId, NewsItem newsData) throws ServiceException{
		eventService.changeNewsData(sessionId, newsItemId, newsData);
	}
	
	@Path("/{newsItemId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public NewsItem getNewsItem(@HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId) throws ServiceException {
		return eventService.getNewsItem(sessionId, newsItemId);
	}
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<NewsItem> getAllNewsItem(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return eventService.getAllNewsItem(sessionId);
	}
		
	@Path("/last/{days}/{outstandingOnly}")
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public List<NewsItem> lastNews(@HeaderParam("sessionId") String sessionId, @PathParam("days") int days, @PathParam("outstandingOnly") boolean outstandingOnly) throws ServiceException{
		Calendar limitDate = Calendar.getInstance();
		limitDate.add(Calendar.DAY_OF_YEAR, -1*days);
		return eventService.getLastNews(sessionId, limitDate, outstandingOnly);
	}
		
	@Path("/{newsItemId}")
	@DELETE
	public void remove(@HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId) throws ServiceException{
		eventService.removeNews(sessionId, newsItemId);
	}
}

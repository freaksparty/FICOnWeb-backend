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

import es.ficonlan.web.backend.entities.Registration;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.output.EventRegistrationData;
import es.ficonlan.web.backend.services.eventservice.EventService;
import es.ficonlan.web.backend.util.EventRegistrationState;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("registration")
public class RegistrationResource {
	
	@Autowired
	private EventService eventService;
	
	public RegistrationResource(){
		this.eventService = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
	}
	
	@Path("/deleteAll/{eventId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void deleteAllFormEvent(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		
	}
	
	@POST
	@Path("/{eventId}/{userId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public EventRegistrationData addParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		return new EventRegistrationData(eventService.addParticipantToEvent(sessionId, userId, eventId));
	}
	
	@DELETE
	@Path("/{eventId}/{userId}")
	public void removeParticipant(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		eventService.removeParticipantFromEvent(sessionId, userId, eventId);
	}
	
	@PUT
	@Path("/{eventId}/{userId}/{state}")
	public void changeRegistrationState(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId, @PathParam("state") String state) throws ServiceException {
		RegistrationState st=null;
		if(state!=null){
    		if(state.toLowerCase().contentEquals("registered"))  st=RegistrationState.registered;
    		if(state.toLowerCase().contentEquals("inqueue")) st=RegistrationState.inQueue;
    		if(state.toLowerCase().contentEquals("paid")) st=RegistrationState.paid;
		}
		eventService.changeRegistrationState(sessionId, userId, eventId, st);
	}
	
	@PUT
	@Path("/setPaid/{eventId}/{userId}")
	public void setPaid(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		eventService.setPaid(sessionId, userId, eventId);
	}
	
	@GET
	@Path("/{eventId}/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventRegistrationData getRegistration(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		return new EventRegistrationData(eventService.getRegistration(sessionId, userId, eventId));
	}
	
	@GET
	@Path("/state/{eventId}/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventRegistrationState getEventRegistrationState(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		return eventService.getEventRegistrationState(sessionId, eventId, userId);
	}
	
	@POST
	@Path("/sendmail/{eventId}/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void sendRegistrationMail(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, @PathParam("userId") int userId) throws ServiceException {
		eventService.sendRegistrationMail(sessionId, userId, eventId);
	}
	

}

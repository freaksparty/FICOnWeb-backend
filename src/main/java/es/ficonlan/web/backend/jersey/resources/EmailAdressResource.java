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

import es.ficonlan.web.backend.entities.Address;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.services.emailservice.EmailService;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("adress")
public class EmailAdressResource {
	
	@Autowired
	private EmailService emailService;
	
	public EmailAdressResource(){
		this.emailService = ApplicationContextProvider.getApplicationContext().getBean(EmailService.class);
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Address addAdress(@HeaderParam("sessionId") String sessionId, Address adress) throws ServiceException {
		return emailService.addAdress(sessionId, adress);
	}
	
	@Path("/{adressId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Address modifyAdress(@HeaderParam("sessionId") String sessionId, @PathParam("adressId") int adressId, Address adress) throws ServiceException {
		return emailService.modifyAdress(sessionId, adressId, adress);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Address> getAllAdress(@HeaderParam("sessionId") String sessionId) throws ServiceException {
			return emailService.getAllAdress(sessionId);
	}
	
	@Path("/{adressId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Address getAdress(@HeaderParam("sessionId") String sessionId, @PathParam("adressId") int adressId) throws ServiceException {
			return emailService.getAdress(sessionId,adressId);
	}
	
	@Path("/{adressId}")
	@DELETE
	public void deleteAdress(@HeaderParam("sessionId") String sessionId, @PathParam("adressId") int adressId) throws ServiceException {
		emailService.deleteAdress(sessionId, adressId);
	}
	
}

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

import es.ficonlan.web.backend.annotations.UseCasePermission;
import es.ficonlan.web.backend.entities.EmailTemplate;
import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.email.EmailFIFO;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.services.emailservice.EmailService;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("emailTemplate")
public class EmailTemplateResource {

	@Autowired
	private EmailService emailService;
	
	public EmailTemplateResource(){
		this.emailService = ApplicationContextProvider.getApplicationContext().getBean(EmailService.class);
	}
	
	@Path("/size")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public int getEmailQueueSize() {
		return EmailFIFO.mailQueueSize();
	}
	
	@Path("/{adressId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public EmailTemplate createEmailTemplate(@HeaderParam("sessionId") String sessionId, @PathParam("adressId") int adressId, EmailTemplate emailTemplate) throws ServiceException {
		return emailService.createEmailTemplate(sessionId, adressId, emailTemplate);
	}
	
//	@Path("/{adressId}/{emailTemplateId}")
//	@PUT
//	@Consumes({MediaType.APPLICATION_JSON})
//	@Produces(MediaType.APPLICATION_JSON)
//	public EmailTemplate changeEmailTemplate(@HeaderParam("sessionId") String sessionId, @PathParam("adressId") int adressId, @PathParam("emailTemplateId") int emailTemplateId, EmailTemplate emailTemplate) throws ServiceException {
//		return emailService.changeEmailTemplate(sessionId, adressId, emailTemplateId, emailTemplate);
//	}
	
	@PUT
	@Path("/{emailTemplateId}")
	@UseCasePermission("changeEventData")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public EmailTemplate changeEmailTemplate(@PathParam("emailTemplateId") int emailTemplateId, EmailTemplate emailTemplate) throws ServiceException {
		return emailService.changeEmailTemplate(emailTemplateId, emailTemplate);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<EmailTemplate> getAllEmailTemplate(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return emailService.getAllEmailTemplate(sessionId);
	}
	
	@Path("/{emailTemplateId}")
	@DELETE
	public void removeEmailTemplate(@HeaderParam("sessionId") String sessionId, @PathParam("emailTemplateId") int emailTemplateId) throws ServiceException {
		emailService.removeEmailTemplate(sessionId, emailTemplateId);
	}
}

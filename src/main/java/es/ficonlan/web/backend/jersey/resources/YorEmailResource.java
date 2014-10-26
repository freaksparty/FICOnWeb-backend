package es.ficonlan.web.backend.jersey.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.email.Email;
import es.ficonlan.web.backend.model.emailservice.EmailService;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("youremail")
public class YorEmailResource {
	
	private EmailService emailService;
	
	public YorEmailResource(){
		this.emailService = ApplicationContextProvider.getApplicationContext().getBean(EmailService.class);
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Email addYorMail(@HeaderParam("sessionId") String sessionId, Email email) throws ServiceException {
		return emailService.addYorMail(sessionId, email);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Email> getAllYorEmails(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return emailService.getAllYorEmails(sessionId);
	}
	
	@Path("/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Email getYorLasEventEmail(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return emailService.getYorLasEventEmail(sessionId, eventId);
	}
	
	@Path("/{emailId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Email sendYourMail(@HeaderParam("sessionId") String sessionId,  @PathParam("emailId") int emailId) throws ServiceException {
		return emailService.sendYourMail(sessionId, emailId);
	}
}

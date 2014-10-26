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

import es.ficonlan.web.backend.jersey.util.ApplicationContextProvider;
import es.ficonlan.web.backend.model.email.Email;
import es.ficonlan.web.backend.model.emailservice.EmailService;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("email")
public class EmailResource {
	
	private EmailService emailService;
	
	public EmailResource(){
		this.emailService = ApplicationContextProvider.getApplicationContext().getBean(EmailService.class);
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Email addEmail(@HeaderParam("sessionId") String sessionId, Email email) throws ServiceException {
		return emailService.addEmail(sessionId, email);
	}
	
	@Path("/{emailId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Email modifyEmail(@HeaderParam("sessionId") String sessionId, @PathParam("emailId") int emailId, Email email) throws ServiceException {
		return emailService.modifyEmail(sessionId, emailId, email);
	}
	
	@Path("/{emailId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Email getEmail(@HeaderParam("sessionId") String sessionId, @PathParam("emailId") int emailId) throws ServiceException {
		return emailService.getEmail(sessionId, emailId);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Email> getAllMails(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return emailService.getAllMails(sessionId);
	}
	
	@Path("/confirmed")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Email> getConfirmedMails(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return emailService.getConfirmedMails(sessionId);
	}
	
	@Path("/unconfirmed")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Email> getNoConfirmedMails(@HeaderParam("sessionId") String sessionId) throws ServiceException {
		return emailService.getNoConfirmedMails(sessionId);
	}
	
	@Path("/{emailId}")
	@DELETE
	public void deleteEmail(@HeaderParam("sessionId") String sessionId, @PathParam("emailId") int emailId) throws ServiceException {
		emailService.deleteEmail(sessionId, emailId);
	}
	
	@Path("/send/{emailId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Email sendEmail(@HeaderParam("sessionId") String sessionId,  @PathParam("emailId") int emailId) throws ServiceException {
		return emailService.sendEmail(sessionId, emailId);
	}
}

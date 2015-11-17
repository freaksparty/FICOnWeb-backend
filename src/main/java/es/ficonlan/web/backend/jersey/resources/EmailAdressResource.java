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

import es.ficonlan.web.backend.entities.Adress;
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
	public Adress addAdress(@HeaderParam("sessionId") String sessionId, Adress adress) throws ServiceException {
		return emailService.addAdress(sessionId, adress);
	}
	
	@Path("/{adressId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Adress modifyAdress(@HeaderParam("sessionId") String sessionId, @PathParam("adressId") int adressId, Adress adress) throws ServiceException {
		return emailService.modifyAdress(sessionId, adressId, adress);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Adress> getAllAdress(@HeaderParam("sessionId") String sessionId) throws ServiceException {
			return emailService.getAllAdress(sessionId);
	}
	
	@Path("/{adressId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Adress getAdress(@HeaderParam("sessionId") String sessionId, @PathParam("adressId") int adressId) throws ServiceException {
			return emailService.getAdress(sessionId,adressId);
	}
	
	@Path("/{adressId}")
	@DELETE
	public void deleteAdress(@HeaderParam("sessionId") String sessionId, @PathParam("adressId") int adressId) throws ServiceException {
		emailService.deleteAdress(sessionId, adressId);
	}
	
}

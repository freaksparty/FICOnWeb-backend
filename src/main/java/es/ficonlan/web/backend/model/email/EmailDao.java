package es.ficonlan.web.backend.model.email;

import java.util.List;

import es.ficonlan.web.backend.model.util.dao.GenericDao;


/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
public interface EmailDao extends GenericDao<Email, Integer>{
	
	public List<Email> getAllEmails();
	
	public List<Email> getConfirmedEmails();
	
	public List<Email> getNoConfirmedEmails();
	
	public List<Email> getEmailByDestination(int userId);
	
	public List<Email> getEmailByDireccionEnvio(int direccionId);
	
	public List<Email> getEmailByRegistration(int registrationId);
	
	public Email getLasEmailByRegistration(int registrationId);
	
	

}

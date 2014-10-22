package es.ficonlan.web.backend.model.emailservice;

import java.util.List;

import es.ficonlan.web.backend.model.email.Email;
import es.ficonlan.web.backend.model.emailadress.Adress;

public interface EmailService {
	
	public List<Adress> getAllAdress(String sessionId);
	
	public Adress addAdress(String sessionId, Adress adress);
	
	public Adress modifyAdress(String serssionId, int adressId, Adress newAdress);
	
	public void deleteAdress(String serssionId, int adressId);
	
	
	public List<Email> getAllMails(String sessionId);
	
	public List<Email> getConfirmedMails(String sessionId);
	
	public List<Email> getNoConfirmedMails(String sessionId);
	
	public Email getEmail(String sessionId, int emailId);
	
	
	public Email addEmail(String sessionId, Email email);
	
	public Email modifyEmail(String serssionId, int emailId, Email newEmail);
	
	public void deleteEmail(String serssionId, int emailId);
	
	
	public Email sendEmail(String sessionId, int emailId);
	


}

package es.ficonlan.web.backend.model.emailservice;

import java.util.List;

import es.ficonlan.web.backend.model.email.Email;
import es.ficonlan.web.backend.model.emailadress.Adress;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

public interface EmailService {
	
	public List<Adress> getAllAdress(String sessionId) throws ServiceException;
	
	public Adress addAdress(String sessionId, Adress adress) throws ServiceException;
	
	public Adress getAdress(String sessionId, int adressId) throws ServiceException;
	
	public Adress modifyAdress(String sessionId, int adressId, Adress newAdress) throws ServiceException;
	
	public void deleteAdress(String sessionId, int adressId) throws ServiceException;
	
	
	public List<Email> getAllMails(String sessionId) throws ServiceException;
	
	public List<Email> getConfirmedMails(String sessionId) throws ServiceException;
	
	public List<Email> getNoConfirmedMails(String sessionId) throws ServiceException;
	
	public Email getEmail(String sessionId, int emailId) throws ServiceException;
	
	
	public Email addEmail(String sessionId, Email email) throws ServiceException;
	
	public Email modifyEmail(String sessionId, int emailId, Email newEmail) throws ServiceException;
	
	public void deleteEmail(String sessionId, int emailId) throws ServiceException;
	
	
	public Email sendEmail(String sessionId, int emailId) throws ServiceException;
	
	
	public List<Email> getAllYorEmails(String sessionId) throws ServiceException;
	
	public Email getYorLasEventEmail(String sessionId, int eventId) throws ServiceException;
	
	public Email addYorMail(String sessionId, Email email) throws ServiceException;
	
	public Email sendYourMail(String sessionId, int emailId) throws ServiceException;
	

}

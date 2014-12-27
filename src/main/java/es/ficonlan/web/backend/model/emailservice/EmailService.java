package es.ficonlan.web.backend.model.emailservice;

import java.util.List;

import es.ficonlan.web.backend.model.emailadress.Adress;
import es.ficonlan.web.backend.model.emailtemplate.EmailTemplate;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

public interface EmailService {
	
	public List<Adress> getAllAdress(String sessionId) throws ServiceException;
	
	public Adress addAdress(String sessionId, Adress adress) throws ServiceException;
	
	public Adress getAdress(String sessionId, int adressId) throws ServiceException;
	
	public Adress modifyAdress(String sessionId, int adressId, Adress newAdress) throws ServiceException;
	
	public void deleteAdress(String sessionId, int adressId) throws ServiceException;
	
    public EmailTemplate createEmailTemplate(String sessionId, int adressId, EmailTemplate emailTemplate) throws ServiceException;
    
	
	public void removeEmailTemplate(String sessionId, int emailTemplateId) throws ServiceException;
	
	public EmailTemplate changeEmailTemplate(String sessionId, int adressId, int emailTemplateId, EmailTemplate emailTemplateData) throws ServiceException;
    
    public List<EmailTemplate> getAllEmailTemplate(String sessionId) throws ServiceException;
    
    
    public List<EmailTemplate> searchEmailTemplatesByEvent(String sessionId, int eventId) throws ServiceException;
    
    public EmailTemplate findEmailTemplateByName(String sessionId, String name) throws ServiceException;
	

}
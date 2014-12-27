package es.ficonlan.web.backend.model.emailservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficonlan.web.backend.model.emailadress.Adress;
import es.ficonlan.web.backend.model.emailadress.AdressDao;
import es.ficonlan.web.backend.model.emailtemplate.EmailTemplate;
import es.ficonlan.web.backend.model.emailtemplate.EmailTemplateDao;
import es.ficonlan.web.backend.model.event.EventDao;
import es.ficonlan.web.backend.model.registration.RegistrationDao;
import es.ficonlan.web.backend.model.user.UserDao;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.SessionManager;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Service("EmailService")
@Transactional
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private AdressDao adressDao;
	
	@Autowired
	private EventDao eventDao;
	
	@Autowired
	private RegistrationDao registrationDao;
	
	@Autowired
	private EmailTemplateDao emailTemplateDao;
	
	@Autowired
	private UserDao userDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Adress> getAllAdress(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAllAdress")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		return adressDao.getAllAdress();
	}

	@Transactional
	@Override
	public Adress addAdress(String sessionId, Adress adress) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addAdress")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		adressDao.save(adress);
		return adress;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Adress getAdress(String sessionId, int adressId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAdress")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try 
		{
			return adressDao.find(adressId);
		} 
		catch (InstanceException e) 
		{
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Adress");
		}
	}

	@Transactional
	@Override
	public Adress modifyAdress(String sessionId, int adressId, Adress newAdress) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "modifyAdress")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try 
		{
			Adress a = adressDao.find(adressId);
			if(newAdress.getUsuarioCorreo()!=null) a.setUsuarioCorreo(newAdress.getUsuarioCorreo());
			if(newAdress.getPassword()!=null) a.setPassword(newAdress.getPassword());
			adressDao.save(a);
			return a;
		} 
		catch (InstanceException e) 
		{
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Adress");
		}
	}

	@Transactional
	@Override
	public void deleteAdress(String sessionId, int adressId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "deleteAdress")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try 
		{ 
			adressDao.remove(adressId);
		} 
		catch (InstanceException e) 
		{
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Adress");
		}
	}

	
	@Transactional
	@Override
	public EmailTemplate createEmailTemplate(String sessionId, int adressId, EmailTemplate emailTemplate) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "createEmailTemplate")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try {
			emailTemplate.setAdress(adressDao.find(adressId));
		
			if(emailTemplate.getAsunto()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"asunto");
			if(emailTemplate.getContenido()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"contenido");
			if(emailTemplate.getName()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"name");
		
			emailTemplateDao.save(emailTemplate);
		
			return emailTemplate;
		
		}
		catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Adress");
		}
	}

	@Transactional
	@Override
	public void removeEmailTemplate(String sessionId, int emailTemplateId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removeEmailTemplate")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try 
		{ 
			emailTemplateDao.remove(emailTemplateId);
		} 
		catch (InstanceException e) 
		{
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

	@Transactional
	@Override
	public EmailTemplate changeEmailTemplate(String sessionId, int adressId, int emailTemplateId, EmailTemplate emailTemplateData) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "changeEmailTemplate")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try 
		{
			EmailTemplate e = emailTemplateDao.find(emailTemplateId);
			
			e.setAdress(adressDao.find(adressId));

			if(emailTemplateData.getName()!=null) e.setName(emailTemplateData.getName());
			if(emailTemplateData.getFilepath()!=null) e.setFilepath(emailTemplateData.getFilepath());
			if(emailTemplateData.getFilename()!=null) e.setFilename(emailTemplateData.getFilename());

			if(emailTemplateData.getContenido()!=null) e.setContenido(emailTemplateData.getContenido());
			if(emailTemplateData.getAsunto()!=null) e.setAsunto(emailTemplateData.getAsunto());
			else throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Adress");
				
			emailTemplateDao.save(e);
			return e;
		}
		catch (InstanceException e) 
		{
			if (e.getClassName().contentEquals("EmailTemplate")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Adress");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<EmailTemplate> getAllEmailTemplate(String sessionId)  throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAllEmailTemplate")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		return emailTemplateDao.getAllEmailTemplate();
	}
	

	@Transactional(readOnly = true)
	@Override
	public List<EmailTemplate> searchEmailTemplatesByEvent(String sessionId, int eventId)  throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "searchEmailTemplatesByEvent")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		return emailTemplateDao.getAllEmailTemplate();
	}
	
	@Transactional(readOnly = true)
	@Override
	public EmailTemplate findEmailTemplateByName(String sessionId, String name)  throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "findEmailTemplateByName")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		return emailTemplateDao.findByName(name);
	}
}
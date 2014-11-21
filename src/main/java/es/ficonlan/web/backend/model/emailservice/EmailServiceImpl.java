package es.ficonlan.web.backend.model.emailservice;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficonlan.web.backend.model.email.Email;
import es.ficonlan.web.backend.model.email.EmailDao;
import es.ficonlan.web.backend.model.emailadress.Adress;
import es.ficonlan.web.backend.model.emailadress.AdressDao;
import es.ficonlan.web.backend.model.emailtemplate.EmailTemplate;
import es.ficonlan.web.backend.model.emailtemplate.EmailTemplateDao;
import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.event.EventDao;
import es.ficonlan.web.backend.model.registration.Registration;
import es.ficonlan.web.backend.model.registration.RegistrationDao;
import es.ficonlan.web.backend.model.user.User;
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
	private EmailDao emailDao;
	
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
	
	@Transactional(readOnly = true)
	@Override
	public List<Email> getAllMails(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAllMails")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		return emailDao.getAllEmails();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Email> getConfirmedMails(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getConfirmedMails")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		return emailDao.getConfirmedEmails();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Email> getNoConfirmedMails(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getNoConfirmedMails")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		return emailDao.getNoConfirmedEmails();
	}

	@Transactional(readOnly = true)
	@Override
	public Email getEmail(String sessionId, int emailId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getEmail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		try 
		{
			return emailDao.find(emailId);
		} catch (InstanceException e) 
		{
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Email");
		}
	}

	@Transactional
	@Override
	public Email addEmail(String sessionId, Email email) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addEmail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		emailDao.save(email);
		return email;
	}

	@Transactional
	@Override
	public Email modifyEmail(String sessionId, int emailId, Email newEmail) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "modifyEmail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try 
		{
			Email e = emailDao.find(emailId);
			if(e.getConfirmation()==false)
			{
				if(newEmail.getAsunto()!=null) e.setAsunto(newEmail.getAsunto());
				if(newEmail.getDate()!=null) e.setDate(newEmail.getDate());
				if(newEmail.getDestinatario()!=null) e.setDestinatario(newEmail.getDestinatario());
				if(newEmail.getDireccionEnvio()!=null) e.setDireccionEnvio(newEmail.getDireccionEnvio());
				if(newEmail.getMensaje()!=null) e.setMensaje(newEmail.getMensaje());
				if(newEmail.getNombreArchivo()!=null) e.setNombreArchivo(newEmail.getNombreArchivo());
				if(newEmail.getRutaArchivo()!=null) e.setRutaArchivo(newEmail.getRutaArchivo());
				
				emailDao.save(e);
				return e;
			}
			else throw new ServiceException(ServiceException.CANT_BE_MODIFIED,"Email");
			
		} 
		catch (InstanceException e) 
		{
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Adress");
		}
	}
	
	@Transactional
	@Override
	public Email setSendStatusEmail(int emailId, boolean confirmation) throws ServiceException {
		Email email;
		try {
			email = emailDao.find(emailId);
		}
		catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Email");
		}
		email.setConfirmation(confirmation);
		if(confirmation) email.setSendDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
		emailDao.save(email);
		return email;
	}
	

	@Transactional
	@Override
	public void deleteEmail(String sessionId, int emailId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "deleteEmail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try 
		{
			emailDao.remove(emailId);
		} 
		catch (InstanceException e) 
		{
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Email");
		}
	}

	@Transactional
	@Override
	public Email sendEmail(String sessionId, int emailId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "sendEmail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		try 
		{
			Email e = emailDao.find(emailId);
			e.sendMail();
			emailDao.save(e);
			return e;
		} 
		catch (InstanceException e) 
		{
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Email");
		}
		
	}

	@Transactional(readOnly = true)
	@Override
	public List<Email> getAllUserEmails(String sessionId, int userId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId,"getAllUserEmails")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		User user;
		
		try 
		{
			user = userDao.find(userId);
		} 
		catch (InstanceException e) 
		{
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		return emailDao.getEmailByDestination(user.getUserId());
	}

	@Transactional(readOnly = true)
	@Override
	public Email getUserLastEventEmail(String sessionId, int userId, int eventId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "getUserLastEventEmail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
	
		try 
		{
			@SuppressWarnings("unused")
			User user = userDao.find(userId);
			@SuppressWarnings("unused")
			Event event = eventDao.find(eventId);
			
			Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
			
			return registration.getLastemail();
		} 
		catch (InstanceException e) 
		{
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
	}

	@Transactional
	@Override
	public Email sendUserMail(String sessionId, int userId, int emailId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);

		try 
		{
			@SuppressWarnings("unused")
			User user = userDao.find(userId);
			Email email = emailDao.find(emailId);
			if(email.getDestinatario().getUserId()!=userId) if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "getUserLastEventEmail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
			if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), email.getDestinatario().getUserId(), "sendUserMail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
			Calendar time = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			time.add(Calendar.MINUTE, 1);
			if(!email.getSendDate().before(time)) throw new ServiceException(ServiceException.WAIT_FOR_SEND);
			email.sendMail();
			emailDao.save(email);
			return email;
		} 
		catch (InstanceException e) 
		{
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Email");
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

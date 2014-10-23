package es.ficonlan.web.backend.model.emailservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficonlan.web.backend.model.email.Email;
import es.ficonlan.web.backend.model.email.EmailDao;
import es.ficonlan.web.backend.model.emailadress.Adress;
import es.ficonlan.web.backend.model.emailadress.AdressDao;
import es.ficonlan.web.backend.model.event.EventDao;
import es.ficonlan.web.backend.model.registration.Registration;
import es.ficonlan.web.backend.model.registration.RegistrationDao;
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
	
	@Transactional
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
	public List<Email> getAllMails(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAllMails")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		return emailDao.getAllEmails();
	}

	@Transactional
	@Override
	public List<Email> getConfirmedMails(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getConfirmedMails")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		return emailDao.getConfirmedEmails();
	}
	
	@Transactional
	@Override
	public List<Email> getNoConfirmedMails(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getNoConfirmedMails")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		return emailDao.getNoConfirmedEmails();
	}

	@Transactional
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

	@Override
	public List<Email> getAllYorEmails(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAllYorEmails")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		return emailDao.getEmailByDestination(SessionManager.getSession(sessionId).getUser().getEmail());
	}

	@Override
	public Email getYorLasEventEmail(String sessionId, int eventId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getYorLasEventEmail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		Registration r = registrationDao.findByUserAndEvent(SessionManager.getSession(sessionId).getUser().getUserId(), eventId);
		return emailDao.getLasEmailByRegistration(r.getRegistrationId());

	}
	
	public Email addYorMail(String sessionId, Email email) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), email.getDestinatario().getUserId(), "addYorMail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		emailDao.save(email);
		return email;
		
	}

	@Override
	public Email sendYourMail(String sessionId, int emailId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);

		try 
		{
			Email e = emailDao.find(emailId);
			if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), e.getDestinatario().getUserId(), "sendYourMail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
			e.sendMail();
			emailDao.save(e);
			return e;
		} 
		catch (InstanceException e) 
		{
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Email");
		}
	}
}

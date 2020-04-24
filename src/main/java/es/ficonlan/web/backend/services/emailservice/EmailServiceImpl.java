/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.ficonlan.web.backend.services.emailservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficonlan.web.backend.dao.AdressDao;
import es.ficonlan.web.backend.dao.EmailTemplateDao;
import es.ficonlan.web.backend.dao.EmailTemplateDao.TypeEmail;
import es.ficonlan.web.backend.entities.Address;
import es.ficonlan.web.backend.entities.EmailTemplate;
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
	
//	@Autowired
//	private EventDao eventDao;
	
//	@Autowired
//	private RegistrationDao registrationDao;
	
	@Autowired
	private EmailTemplateDao emailTemplateDao;
	
//	@Autowired
//	private UserDao userDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Address> getAllAdress(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAllAdress")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		return adressDao.getAllAdress();
	}

	@Transactional
	@Override
	public Address addAdress(String sessionId, Address adress) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addAdress")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		adressDao.save(adress);
		return adress;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Address getAdress(String sessionId, int adressId) throws ServiceException {
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
	public Address modifyAdress(String sessionId, int adressId, Address newAdress) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "modifyAdress")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try
		{
			Address a = adressDao.find(adressId);
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
			emailTemplate.setAddress(adressDao.find(adressId));
		
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
	public EmailTemplate changeEmailTemplate(int emailTemplateId, EmailTemplate emailTemplateData) throws ServiceException {
		try
		{
			EmailTemplate e = emailTemplateDao.find(emailTemplateId);

			if(emailTemplateData.getName()!=null) e.setName(emailTemplateData.getName());
			if(emailTemplateData.getFilepath()!=null) e.setFilepath(emailTemplateData.getFilepath());
			if(emailTemplateData.getFilename()!=null) e.setFilename(emailTemplateData.getFilename());

			if(emailTemplateData.getContenido()!=null) e.setContenido(emailTemplateData.getContenido());
			if(emailTemplateData.getAsunto()!=null) e.setAsunto(emailTemplateData.getAsunto());
				
			emailTemplateDao.save(e);
			return e;
		}
		catch (InstanceException e)
		{
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

//	@Transactional
//	@Override
//	public EmailTemplate changeEmailTemplate(String sessionId, int adressId, int emailTemplateId, EmailTemplate emailTemplateData) throws ServiceException {
//		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
//		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "changeEmailTemplate")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
//		
//		try
//		{
//			EmailTemplate e = emailTemplateDao.find(emailTemplateId);
//			
//			e.setAdress(adressDao.find(adressId));
//
//			if(emailTemplateData.getName()!=null) e.setName(emailTemplateData.getName());
//			if(emailTemplateData.getFilepath()!=null) e.setFilepath(emailTemplateData.getFilepath());
//			if(emailTemplateData.getFilename()!=null) e.setFilename(emailTemplateData.getFilename());
//
//			if(emailTemplateData.getContenido()!=null) e.setContenido(emailTemplateData.getContenido());
//			if(emailTemplateData.getAsunto()!=null) e.setAsunto(emailTemplateData.getAsunto());
//			else throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Adress");
//				
//			emailTemplateDao.save(e);
//			return e;
//		}
//		catch (InstanceException e)
//		{
//			if (e.getClassName().contentEquals("EmailTemplate")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
//			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Adress");
//		}
//	}

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

	@Override
	public EmailTemplate findEmailTemplateForEvent(final int eventId, final TypeEmail type) throws NoSuchFieldException {
		return emailTemplateDao.findByEvent(eventId, type);
	}
}
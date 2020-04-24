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

import es.ficonlan.web.backend.dao.EmailTemplateDao.TypeEmail;
import es.ficonlan.web.backend.entities.Address;
import es.ficonlan.web.backend.entities.EmailTemplate;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

public interface EmailService {
	
	public List<Address> getAllAdress(String sessionId) throws ServiceException;
	
	public Address addAdress(String sessionId, Address adress) throws ServiceException;
	
	public Address getAdress(String sessionId, int adressId) throws ServiceException;
	
	public Address modifyAdress(String sessionId, int adressId, Address newAdress) throws ServiceException;
	
	public void deleteAdress(String sessionId, int adressId) throws ServiceException;
	
    public EmailTemplate createEmailTemplate(String sessionId, int adressId, EmailTemplate emailTemplate) throws ServiceException;
    
	
	public void removeEmailTemplate(String sessionId, int emailTemplateId) throws ServiceException;
	
//	public EmailTemplate changeEmailTemplate(String sessionId, int adressId, int emailTemplateId, EmailTemplate emailTemplateData) throws ServiceException;
    
    public List<EmailTemplate> getAllEmailTemplate(String sessionId) throws ServiceException;
    
    
    public List<EmailTemplate> searchEmailTemplatesByEvent(String sessionId, int eventId) throws ServiceException;
    
    public EmailTemplate findEmailTemplateByName(String sessionId, String name) throws ServiceException;
    
    public EmailTemplate findEmailTemplateForEvent(final int eventId, final TypeEmail type) throws NoSuchFieldException;

	public EmailTemplate changeEmailTemplate(final int emailTemplateId, final EmailTemplate emailTemplateData) throws ServiceException;
	

}
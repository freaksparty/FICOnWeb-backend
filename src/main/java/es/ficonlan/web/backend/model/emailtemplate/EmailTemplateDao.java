package es.ficonlan.web.backend.model.emailtemplate;

import java.util.List;

import es.ficonlan.web.backend.model.util.dao.GenericDao;

/**
 * @author Miguel Ángel Castillo Bellagona

 */
public interface EmailTemplateDao extends GenericDao<EmailTemplate, Integer> {

	public List<EmailTemplate> getAllEmailTemplate();
	
	public List<EmailTemplate> searchEmailTemplatesByEvent(int eventId);
    
    public EmailTemplate findByName(String name);
}

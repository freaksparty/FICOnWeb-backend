package es.ficonlan.web.backend.dao;

import java.util.List;

import es.ficonlan.web.backend.entities.EmailTemplate;

/**
 * @author Miguel Ángel Castillo Bellagona

 */
public interface EmailTemplateDao extends GenericDao<EmailTemplate, Integer> {

	public List<EmailTemplate> getAllEmailTemplate();
    
    public EmailTemplate findByName(String name);
}

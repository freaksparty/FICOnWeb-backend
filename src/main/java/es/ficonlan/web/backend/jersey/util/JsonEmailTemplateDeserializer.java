package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.model.emailtemplate.EmailTemplate;
import es.ficonlan.web.backend.model.emailtemplate.EmailTemplateDao;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class JsonEmailTemplateDeserializer extends JsonDeserializer<EmailTemplate> {
	
	@Autowired
	private EmailTemplateDao emailTemplateDao;

	@Override
	public EmailTemplate deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
		int id = jsonparser.getIntValue();
		try
		{
			return emailTemplateDao.find(id);
		} 
		catch (InstanceException e)
		{
			return null;
		}
	}

}

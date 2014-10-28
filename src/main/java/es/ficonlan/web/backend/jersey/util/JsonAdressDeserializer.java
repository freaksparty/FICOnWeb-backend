package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.model.emailadress.Adress;
import es.ficonlan.web.backend.model.emailadress.AdressDao;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class JsonAdressDeserializer extends JsonDeserializer<Adress> {
	
	@Autowired
	private AdressDao adressDao;

	@Override
	public Adress deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
		int id = jsonparser.getIntValue();
		try
		{
			return adressDao.find(id);
		} 
		catch (InstanceException e)
		{
			return null;
		}
	}


}

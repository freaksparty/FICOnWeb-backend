package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.user.UserDao;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class JsonUserDeserializer extends JsonDeserializer<User> {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
		int id = jsonparser.getIntValue();
		try
		{
			return userDao.find(id);
		} 
		catch (InstanceException e)
		{
			return null;
		}
	}

}
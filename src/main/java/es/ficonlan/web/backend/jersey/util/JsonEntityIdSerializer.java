package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.user.User;

/**
 * @author Daniel Gómez Silva
 */
public class JsonEntityIdSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object entity, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
		if(entity==null) gen.writeNull();
		else if(entity instanceof Event) gen.writeString(String.valueOf(((Event) entity).getEventId()));	
		else if(entity instanceof User)  gen.writeString(String.valueOf(((User) entity).getUserId()));		
	}

}

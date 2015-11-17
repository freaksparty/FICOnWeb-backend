package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import es.ficonlan.web.backend.entities.Adress;
import es.ficonlan.web.backend.entities.Event;
import es.ficonlan.web.backend.entities.Registration;
import es.ficonlan.web.backend.entities.User;;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
public class JsonEntityIdSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object entity, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
		if(entity==null) gen.writeNull();
		else if(entity instanceof Event) gen.writeString(String.valueOf(((Event) entity).getEventId()));	
		else if(entity instanceof User)  gen.writeString(String.valueOf(((User) entity).getUserId()));
		else if(entity instanceof Adress)  gen.writeString(String.valueOf(((Adress) entity).getAdresslId()));
		else if(entity instanceof Registration)  gen.writeString(String.valueOf(((Registration) entity).getRegistrationId()));
	}

}

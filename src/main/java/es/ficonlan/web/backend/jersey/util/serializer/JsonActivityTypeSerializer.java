package es.ficonlan.web.backend.jersey.util.serializer;

import java.io.IOException;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import es.ficonlan.web.backend.entities.Activity.ActivityType;


/**
 * @author Siro Gonz&aacute;lez <xiromoreira>
 */
@Component
public class JsonActivityTypeSerializer extends JsonSerializer<ActivityType> {

	@Override
	public void serialize(ActivityType at, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		gen.writeString(at.name());
	}
}
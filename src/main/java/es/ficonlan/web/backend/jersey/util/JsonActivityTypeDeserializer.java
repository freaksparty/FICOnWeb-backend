package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import es.ficonlan.web.backend.model.activity.Activity.ActivityType;

/**
 * @author Daniel Gómez Silva
 */
public class JsonActivityTypeDeserializer extends JsonDeserializer<ActivityType> {
	
	@Override
	public ActivityType deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
		String type = jsonparser.getText();
		if(type.toLowerCase().contentEquals("tournament")) return ActivityType.Tournament;
		if(type.toLowerCase().contentEquals("production")) return ActivityType.Production;
		if(type.toLowerCase().contentEquals("conference")) return ActivityType.Conference;
		return null;
	}

}

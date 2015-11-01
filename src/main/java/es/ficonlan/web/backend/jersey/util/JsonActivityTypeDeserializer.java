package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import es.ficonlan.web.backend.model.activity.Activity.ActivityType;

/**
 * @author Daniel Gómez Silva
 */
public class JsonActivityTypeDeserializer extends JsonDeserializer<ActivityType> {
	
	@Override
	public ActivityType deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) 
			throws IOException, JsonProcessingException {
		String type = jsonparser.getText();
		if(type.toLowerCase().contentEquals("tournament")) return ActivityType.Tournament;
		if(type.toLowerCase().contentEquals("production")) return ActivityType.Production;
		if(type.toLowerCase().contentEquals("conference")) return ActivityType.Conference;
		return null;
	}

}

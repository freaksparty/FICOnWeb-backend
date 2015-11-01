package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import es.ficonlan.web.backend.model.activity.Activity.ActivityType;


/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Component
public class JsonActivityTypeSerializer extends JsonSerializer<ActivityType> {

	@Override
	public void serialize(ActivityType at, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		String activitytype = "";
		if(at == ActivityType.Conference) activitytype = "Conference";
		if(at == ActivityType.Production) activitytype = "Production";
		if(at == ActivityType.Tournament) activitytype = "Tournament";
		gen.writeString(activitytype);		
	}
}
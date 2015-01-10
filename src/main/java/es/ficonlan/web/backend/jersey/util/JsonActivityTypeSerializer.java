package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

import es.ficonlan.web.backend.model.activity.Activity.ActivityType;


/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Component
public class JsonActivityTypeSerializer extends JsonSerializer<ActivityType> {

	@Override
	public void serialize(ActivityType at, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
		String activitytype = "";
		if(at == ActivityType.Conference) activitytype = "Conference";
		if(at == ActivityType.Production) activitytype = "Production";
		if(at == ActivityType.Tournament) activitytype = "Production";
		gen.writeString(activitytype);		
	}
}
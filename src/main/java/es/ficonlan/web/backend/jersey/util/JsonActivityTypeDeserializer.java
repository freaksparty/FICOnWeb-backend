package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import es.ficonlan.web.backend.entities.Activity.ActivityType;

/**
 * @author Siro Gonz&aacute;lez <xiromoreira>
 */
public class JsonActivityTypeDeserializer extends JsonDeserializer<ActivityType> {	
	
	@Override
	public ActivityType deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) 
			throws IOException, JsonProcessingException {
		String typeName = StringUtils.capitalize(jsonparser.getText());
		for(ActivityType type : ActivityType.values()) {
			if(typeName.contentEquals(type.name())) return type;
		}
		return null;
	}

}

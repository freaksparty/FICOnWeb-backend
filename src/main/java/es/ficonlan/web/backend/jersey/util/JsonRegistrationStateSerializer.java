package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;


/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Component
public class JsonRegistrationStateSerializer extends JsonSerializer<RegistrationState> {

	@Override
	public void serialize(RegistrationState re, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
		String registrationstate = "";
		if(re == RegistrationState.inQueue) registrationstate = "inQueue";
		if(re == RegistrationState.paid) registrationstate = "paid";
		if(re == RegistrationState.registered) registrationstate = "registered";
		gen.writeString(registrationstate);		
	}

}

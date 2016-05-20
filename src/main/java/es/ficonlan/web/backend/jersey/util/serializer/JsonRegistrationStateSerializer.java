package es.ficonlan.web.backend.jersey.util.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import es.ficonlan.web.backend.entities.Registration.RegistrationState;


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

/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.ficonlan.web.backend.jersey.util.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import es.ficonlan.web.backend.entities.Registration.RegistrationState;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class JsonRegistrationStateDeserializer extends JsonDeserializer<RegistrationState> {

	@Override
	public RegistrationState deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
		String type = jsonparser.getText();
		if(type.toLowerCase().contentEquals("inQueue")) 	return RegistrationState.inQueue;
		if(type.toLowerCase().contentEquals("paid")) 		return RegistrationState.paid;
		if(type.toLowerCase().contentEquals("registered")) 	return RegistrationState.registered;
		return null;
	}

}

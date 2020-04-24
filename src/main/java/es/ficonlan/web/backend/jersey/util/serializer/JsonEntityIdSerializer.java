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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import es.ficonlan.web.backend.entities.Address;
import es.ficonlan.web.backend.entities.EmailTemplate;
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
		else if(entity instanceof Address)  gen.writeString(String.valueOf(((Address) entity).getAddresslId()));
		else if(entity instanceof Registration)  gen.writeString(String.valueOf(((Registration) entity).getRegistrationId()));
		else if(entity instanceof EmailTemplate) gen.writeNumber(((EmailTemplate)entity).getEmailtemplateid());
	}

}

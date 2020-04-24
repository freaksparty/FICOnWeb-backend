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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Component
public class JsonDateDeserializer extends JsonDeserializer<Calendar> {
	private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss");

	@Override
	public Calendar deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) 
			throws IOException, JsonProcessingException {
		
		//format.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		String date = jsonparser.getText();
		Calendar c = Calendar.getInstance();
		try {
			c.setTimeInMillis(format.parse(date).getTime());
		} catch (ParseException e) {
			//throw new RuntimeException();
			return null; //Ahora si nos envia una fecha en mal formato la ponemos a null
		}
		return c;
	}
}

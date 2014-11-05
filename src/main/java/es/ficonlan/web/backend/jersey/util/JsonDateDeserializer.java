package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.stereotype.Component;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Component
public class JsonDateDeserializer extends JsonDeserializer<Calendar> {

	@Override
	public Calendar deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss");
		String date = jsonparser.getText();
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		try {
			c.setTimeInMillis(format.parse(date).getTime());
		} catch (ParseException e) {
			throw new RuntimeException();
		}
		return c;
	}

}

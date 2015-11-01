package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Calendar>{
 
    //private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss");
	
    @Override
    public void serialize(Calendar date, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
    	//dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = dateFormat.format(date.getTime());
        gen.writeString(formattedDate);
    }
}

package es.ficonlan.web.backend.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

public class EmailPaied extends Email {
	
	public EmailPaied(String destinatario, String eventName) throws ServiceException {
		super();
		
		String mailConfig  = "mail/mail.properties";
		String mailContent = "mail/PaiedMail.properties";
		Properties propConfig = new Properties();
		Properties propContetnt = new Properties();

		InputStream inputStreamConfig = Email.class.getClassLoader().getResourceAsStream(mailConfig);
		InputStream inputStreamContent = Email.class.getClassLoader().getResourceAsStream(mailContent);

		try {
			propConfig.load(inputStreamConfig);
		} catch (IOException e1) {
			throw new ServiceException(12, "createEmail", mailConfig);
		}
		try {
			propContetnt.load(inputStreamContent);
		} catch (IOException e1) {
			throw new ServiceException(12, "createEmail", mailContent);
		}
		
		this.usuarioCorreo = propConfig.getProperty("direccion");
		this.password = propConfig.getProperty("clave");

		this.asunto = propContetnt.getProperty("asunto") + " " + eventName;;
		this.mensaje = propContetnt.getProperty("mensaje1") + " " + eventName + "\n" + propContetnt.getProperty("mensaje2");
		this.rutaArchivo = propContetnt.getProperty("rutaArchivo");
		this.nombreArchivo = propContetnt.getProperty("nombreArchivo");

		this.destinatario = destinatario;
			
	}


}

package es.ficonlan.web.backend.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 2.0
 */
public class Email {

	protected String usuarioCorreo;
	protected String password;

	protected String rutaArchivo;
	protected String nombreArchivo;

	protected String destinatario;
	protected String asunto;
	protected String mensaje;

	public Email(String usuarioCorreo, String password, String rutaArchivo, String nombreArchivo, String destinatario, String asunto, String mensaje) {
		this.usuarioCorreo = usuarioCorreo;
		this.password = password;
		this.rutaArchivo = rutaArchivo;
		this.nombreArchivo = nombreArchivo;
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.mensaje = mensaje;
	}

	public Email(String usuarioCorreo, String password, String destinatario, String mensaje) {
		this(usuarioCorreo, password, "", "", destinatario, "", mensaje);
	}

	public Email(String usuarioCorreo, String password, String destinatario, String asunto, String mensaje) {
		this(usuarioCorreo, password, "", "", destinatario, asunto, mensaje);
	}

	public Email(String mailConfig, String mailContent, String destinatario) throws ServiceException {

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

		this.asunto = propContetnt.getProperty("asunto");
		this.mensaje = propContetnt.getProperty("mensaje");
		this.rutaArchivo = propContetnt.getProperty("rutaArchivo");
		this.nombreArchivo = propContetnt.getProperty("nombreArchivo");

		this.destinatario = destinatario;
	}

	public Email() { }

	public boolean sendMail() {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user", usuarioCorreo);
			props.setProperty("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props, null);
			BodyPart texto = new MimeBodyPart();
			texto.setText(mensaje);

			BodyPart adjunto = new MimeBodyPart();
			if (!rutaArchivo.equals("")) {
				adjunto.setDataHandler(new DataHandler(new FileDataSource(rutaArchivo)));
				adjunto.setFileName(nombreArchivo);
			}

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			if (!rutaArchivo.equals("")) {
				multiParte.addBodyPart(adjunto);
			}

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(usuarioCorreo));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			message.setSubject(asunto);
			message.setContent(multiParte);

			Transport t = session.getTransport("smtp");
			t.connect(usuarioCorreo, password);
			t.sendMessage(message, message.getAllRecipients());
			t.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		
		try {
			Email e1 = new EmailInQueue("surah.harus@gmail.com", "FICONLAN", 2);
			Email e2 = new EmailOutstanding("surah.harus@gmail.com", "FICONLAN", 2);
			Email e3 = new EmailOutstandingFromInQueue("surah.harus@gmail.com", "FICONLAN");
			Email e4 = new EmailPaied("surah.harus@gmail.com", "FICONLAN");
			Email e5 = new EmailTimeToPayExceeded("surah.harus@gmail.com", "FICONLAN");
			if (e1.sendMail())  System.out.println("Correo mandado 1");
			else System.out.println("Correo no mandado 1");
			if (e2.sendMail())  System.out.println("Correo mandado 2");
			else System.out.println("Correo no mandado 2");
			if (e3.sendMail())  System.out.println("Correo mandado 3");
			else System.out.println("Correo no mandado 3");
			if (e4.sendMail())  System.out.println("Correo mandado 4");
			else System.out.println("Correo no mandado 4");
			if (e5.sendMail())  System.out.println("Correo mandado 5");
			else System.out.println("Correo no mandado 5");
			} 
		catch (ServiceException e) { e.printStackTrace();}
		
		}

	

}

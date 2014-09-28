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

	private String usuarioCorreo;
	private String password;

	private String rutaArchivo;
	private String nombreArchivo;

	private String destinatario;
	private String asunto;
	private String mensaje;

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
		int i = 0;
		while(true)
		{
		try {
			Email e = new Email("mail/mail.properties", "mail/InQueue.properties", "surah.harus@gmail.com");

			if (e.sendMail()) {
			//	JOptionPane.showMessageDialog(null, "El email se mandó correctamente");
				System.out.println("Correo mandado " + i++);
			} else {
			//	JOptionPane.showMessageDialog(null, "El email no se mandó correctamente");
			}
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		Thread.sleep(1000);
		}

	}

}
package es.ficonlan.web.backend.model.email;

import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/*
* @author Miguel Ángel Castillo Bellagona
* @version 2.1
*/
public class SendMailThread extends Thread {
	
	private Email email;
	
	public SendMailThread(Email email) {
		this.email = email;
	}
	
	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public boolean sendMail() {

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user",email.getDireccionEnvio().getUsuarioCorreo());
			props.setProperty("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props, null);
			BodyPart texto = new MimeBodyPart();
			texto.setText(email.getMensaje());

			BodyPart adjunto = new MimeBodyPart();
			if (!email.getRutaArchivo().equals("")) {
				adjunto.setDataHandler(new DataHandler(new FileDataSource(email.getRutaArchivo())));
				adjunto.setFileName(email.getNombreArchivo());
			}

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			if (!email.getRutaArchivo().equals("")) {
				multiParte.addBodyPart(adjunto);
			}

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email.getDireccionEnvio().getUsuarioCorreo()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getDestinatario().getEmail()));
			message.setSubject(email.getAsunto());
			message.setContent(multiParte);

			Transport t = session.getTransport("smtp");
			t.connect(email.getDireccionEnvio().getUsuarioCorreo(),email.getDireccionEnvio().getPassword());
			t.sendMessage(message, message.getAllRecipients());
			t.close();

			email.setSendDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
			email.setConfirmation(true);

			return true;
		} 
		catch (MessagingException e) 
		{
			e.printStackTrace();

			email.setSendDate(null);
			email.setConfirmation(false);

			return false;
		}

	}

	@Override
	public void run()
	{
		this.sendMail();
	}
}
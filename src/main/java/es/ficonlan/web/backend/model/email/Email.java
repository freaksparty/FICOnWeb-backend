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
import javax.persistence.Entity;
import javax.persistence.Table;

import es.ficonlan.web.backend.model.emailadress.Adress;
import es.ficonlan.web.backend.model.registration.Registration;
import es.ficonlan.web.backend.model.user.User;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 2.1
 */
@Entity
@Table(name = "Email")
public class Email {

	protected int emailId;

	protected Adress direccionEnvio;

	protected boolean confirmation;

	protected String rutaArchivo;
	protected String nombreArchivo;

	protected User destinatario;
	protected String asunto;
	protected String mensaje;

	protected Calendar sendDate;
	protected Calendar date;
	
	protected Registration registration;

	public Email() {
		
	}

	public Email( Adress direccionEnvio, String rutaArchivo,String nombreArchivo, User destinatario, String asunto,String mensaje) {
		this.direccionEnvio = direccionEnvio;
		this.rutaArchivo = rutaArchivo;
		this.nombreArchivo = nombreArchivo;
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.mensaje = mensaje;
		this.date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		this.sendDate = null;
		this.confirmation = false;
	}

	public Email(Adress direccionEnvio, User destinatario, String mensaje) {
		this( direccionEnvio, "", "", destinatario, "", mensaje);
	}

	public Email(Adress direccionEnvio, User destinatario, String asunto,
			String mensaje) {
		this( direccionEnvio, "", "", destinatario, asunto, mensaje);
	}

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}

	public boolean getConfirmation() {
		return this.confirmation;
	}

	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}

	public Adress getDireccionEnvio() {
		return this.direccionEnvio;
	}

	public void setDireccionEnvio(Adress direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public User getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(User destinatario) {
		this.destinatario = destinatario;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getSendDate() {
		return sendDate;
	}

	public void setSendDate(Calendar sendDate) {
		this.sendDate = sendDate;
	}
	
	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public boolean sendMailThread() {

		SendMailThread thread = new SendMailThread(this);
		thread.start();
		return true;
	}	
	
	public boolean sendMail() {

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user",this.getDireccionEnvio().getUsuarioCorreo());
			props.setProperty("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props, null);
			BodyPart texto = new MimeBodyPart();
			texto.setText(this.getMensaje());

			BodyPart adjunto = new MimeBodyPart();
			if (!this.getRutaArchivo().equals("")) {
				adjunto.setDataHandler(new DataHandler(new FileDataSource(this.getRutaArchivo())));
				adjunto.setFileName(this.getNombreArchivo());
			}

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			if (!this.getRutaArchivo().equals("")) {
				multiParte.addBodyPart(adjunto);
			}

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.getDireccionEnvio().getUsuarioCorreo()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.getDestinatario().getEmail()));
			message.setSubject(this.getAsunto());
			message.setContent(multiParte);

			Transport t = session.getTransport("smtp");
			t.connect(this.getDireccionEnvio().getUsuarioCorreo(),this.getDireccionEnvio().getPassword());
			t.sendMessage(message, message.getAllRecipients());
			t.close();

			this.setSendDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
			this.setConfirmation(true);

			return true;
		} 
		catch (MessagingException e) 
		{
			e.printStackTrace();

			this.setSendDate(null);
			this.setConfirmation(false);

			return false;
		}

	}


}

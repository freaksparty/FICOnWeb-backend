package es.ficonlan.web.backend.entities;

import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 2.1
 */
@Entity
@Table(name = "Email")
public class Email {

	protected int		emailId;
	
	private int sendingErrors = 0;

	//TODO: make private userSend, passSend. Maybe those should not be here?
	public String	userSend;
	public String	passSend;
	
	protected String	rutaArchivo;
	protected String	nombreArchivo;

	protected String	destinatario;
	protected String	asunto;
	protected String	mensaje;

	protected Calendar	sendDate;
	protected Calendar	date;

	public Email() {

	}

	public Email(String userSend, String passSend, String rutaArchivo, String nombreArchivo, String destinatario, String asunto, String mensaje) {
		this.userSend = userSend;
		this.passSend = passSend;
		this.rutaArchivo = rutaArchivo;
		this.nombreArchivo = nombreArchivo;
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.mensaje = mensaje;
		this.date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		this.sendDate = null;
	}

	public Email(String userSend, String passSend, String destinatario, String mensaje) {
		this(userSend, passSend, "", "", destinatario, "", mensaje);
	}

	public Email(String userSend, String passSend, String destinatario, String asunto, String mensaje) {
		this(userSend, passSend, "", "", destinatario, asunto, mensaje);
	}

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
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

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
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
	
	public int getSendingErrors() {
		return sendingErrors;
	}
	
	public void onError() {
		sendingErrors++;
	}

//	public boolean sendMailThread() {
//
//		SendMailThread thread = new SendMailThread(this);
//		thread.start();
//		return true;
//	}

	public boolean sendMail() throws MessagingException {

		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.user", this.userSend);
		props.setProperty("mail.smtp.auth", "true");
		//TLS
//		props.setProperty("mail.smtp.starttls.enable", "true");
//		props.setProperty("mail.smtp.port", "587");
		//SSL
		props.setProperty("mail.smtp.ssl.enable", "true");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Authenticator auth = new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userSend, passSend);
			}
		};
		
		Session session = Session.getDefaultInstance(props, auth);
		BodyPart texto = new MimeBodyPart();
		texto.setText(this.getMensaje());

		BodyPart adjunto = new MimeBodyPart();
		if (this.getRutaArchivo() != null) if (!this.getRutaArchivo().equals("")) {
			adjunto.setDataHandler(new DataHandler(new FileDataSource(this.getRutaArchivo())));
			adjunto.setFileName(this.getNombreArchivo());
		}

		MimeMultipart multiParte = new MimeMultipart();
		multiParte.addBodyPart(texto);
		if (this.getRutaArchivo() != null) if (!this.getRutaArchivo().equals("")) {
			multiParte.addBodyPart(adjunto);
		}

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(this.userSend));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.destinatario));
		message.setSubject(this.getAsunto());
		message.setContent(multiParte);

		Transport.send(message);

		this.setSendDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")));

		return true;
	}
	
	public String toShortString() {
		return "destinatario: " + this.destinatario + ", origen:" + this.userSend;
	}

}

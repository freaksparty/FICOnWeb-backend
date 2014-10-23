package es.ficonlan.web.backend.model.email;

import java.util.Calendar;
import java.util.Properties;

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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.ficonlan.web.backend.model.emailadress.Adress;
import es.ficonlan.web.backend.model.registration.Registration;
import es.ficonlan.web.backend.model.user.User;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 2.0
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
		this.date = Calendar.getInstance();
	}

	public Email(boolean isTemplate, Adress direccionEnvio, String rutaArchivo,String nombreArchivo, User destinatario, String asunto,String mensaje) {
		this.direccionEnvio = direccionEnvio;
		this.rutaArchivo = rutaArchivo;
		this.nombreArchivo = nombreArchivo;
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.mensaje = mensaje;
		this.date = Calendar.getInstance();
		this.sendDate = null;
		this.confirmation = false;
	}

	public Email(Adress direccionEnvio, User destinatario, String mensaje) {
		this(false, direccionEnvio, "", "", destinatario, "", mensaje);
	}

	public Email(Adress direccionEnvio, User destinatario, String asunto,
			String mensaje) {
		this(false, direccionEnvio, "", "", destinatario, asunto, mensaje);
	}

	@Column(name = "Email_id")
	@SequenceGenerator(name = "emailIdGenerator", sequenceName = "emailSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "emailIdGenerator")
	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}

	@Column(name = "Email_confirmation")
	public boolean getConfirmation() {
		return this.confirmation;
	}

	private void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Email_Adress_id")
	public Adress getDireccionEnvio() {
		return this.direccionEnvio;
	}

	public void setDireccionEnvio(Adress direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}

	@Column(name = "Email_file")
	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	@Column(name = "Email_fileName")
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Email_User_id")
	public User getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(User destinatario) {
		this.destinatario = destinatario;
	}

	@Column(name = "case")
	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	@Column(name = "Email_body")
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Column(name = "Email_date")
	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}

	@Column(name = "Email_senddate")
	public Calendar getSendDate() {
		return sendDate;
	}

	private void setSendDate(Calendar sendDate) {
		this.sendDate = sendDate;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Email_registration_id")
	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}


	public boolean sendMail() {

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user",this.direccionEnvio.getUsuarioCorreo());
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
			message.setFrom(new InternetAddress(this.direccionEnvio.getUsuarioCorreo()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario.getEmail()));
			message.setSubject(asunto);
			message.setContent(multiParte);

			Transport t = session.getTransport("smtp");
			t.connect(this.direccionEnvio.getUsuarioCorreo(),this.direccionEnvio.getPassword());
			t.sendMessage(message, message.getAllRecipients());
			t.close();

			this.setSendDate(Calendar.getInstance());
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

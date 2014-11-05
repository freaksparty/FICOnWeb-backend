package es.ficonlan.web.backend.model.email;

import java.util.Calendar;
import java.util.TimeZone;

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

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.JsonAdressDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonDateDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonDateSerializer;
import es.ficonlan.web.backend.jersey.util.JsonEntityIdSerializer;
import es.ficonlan.web.backend.jersey.util.JsonRegistrationDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonUserDeserializer;
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

	void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}

	@JsonDeserialize(using = JsonAdressDeserializer.class)
	@JsonSerialize(using = JsonEntityIdSerializer.class) 
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

	@JsonDeserialize(using = JsonUserDeserializer.class)
	@JsonSerialize(using = JsonEntityIdSerializer.class)
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

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "Email_date")
	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "Email_senddate")
	public Calendar getSendDate() {
		return sendDate;
	}

	void setSendDate(Calendar sendDate) {
		this.sendDate = sendDate;
	}
	
	@JsonDeserialize(using = JsonRegistrationDeserializer.class)
	@JsonSerialize(using = JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Email_registration_id")
	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}


	public boolean sendMail() {

		SendMailThread trhead = new SendMailThread(this);
		trhead.start();
		return true;
	}

}

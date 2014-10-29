package es.ficonlan.web.backend.model.emailtemplate;

import java.util.Enumeration;
import java.util.Hashtable;

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
import es.ficonlan.web.backend.jersey.util.JsonEntityIdSerializer;
import es.ficonlan.web.backend.model.email.Email;
import es.ficonlan.web.backend.model.emailadress.Adress;
import es.ficonlan.web.backend.model.user.User;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
@Table(name="EmailTemplate")
public class EmailTemplate {
	
	private int emailtemplateid;
	private String name;
	private Adress adress;
	private String filepath;
	private String filename; 
	private String asunto;
	private String contenido;
	
	public EmailTemplate() {
		
	}

	@Column(name = "EmailTemplate_id ")
	@SequenceGenerator(name = "EmailTemplateIdGenerator", sequenceName = "EmailTemplateSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "EmailTemplateIdGenerator")
	public int getEmailtemplateid() {
		return emailtemplateid;
	}

	public void setEmailtemplateid(int emailtemplateid) {
		this.emailtemplateid = emailtemplateid;
	}

	@Column(name = "EmailTemplate_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonDeserialize(using = JsonAdressDeserializer.class)
	@JsonSerialize(using = JsonEntityIdSerializer.class) 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EmailTemplate_adress_id")
	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	@Column(name = "EmailTemplate_file")
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Column(name = "EmailTemplate_fileName")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "EmailTemplate_case")
	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	@Column(name = "EmailTemplate_body")
	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Email generateEmail(User destinatario, Hashtable<String,String> datos) {
		
		String contenido = this.getContenido();
		
		Enumeration<String> e = datos.keys();
		String clave;
		String valor;
		while( e.hasMoreElements() ){
		  clave = (String) e.nextElement();
		  valor = datos.get(clave);
		  contenido = contenido.replace(clave, valor);
		}
		
		return new Email(this.getAdress(),this.getFilepath(),this.getFilename(),destinatario,this.getAsunto(),contenido);
	}
	
	public static void main(String[] args) {
		String uno = "Es usuario #username tiene la plaza #placenumber.";
		String dos = uno;
		
		Hashtable<String,String> tabla = new Hashtable<String,String>();
		tabla.put("#username", "Surah");
		tabla.put("#placenumber","12");
		tabla.put("#valornousado","valornousado");
		
		Enumeration<String> e = tabla.keys();
		String clave;
		String valor;
		while( e.hasMoreElements() ){
		  clave = (String) e.nextElement();
		  valor = tabla.get(clave);
		  dos = dos.replace(clave, valor);
		}
		
		System.out.println(uno);
		System.out.println(dos);
	}

}

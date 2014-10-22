package es.ficonlan.web.backend.model.emailadress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 2.0
 */
@Entity
@Table(name="Adress")
public class Adress {
	
	protected int adresslId;
	
	protected String usuarioCorreo;
	protected String password;
	
	@Column(name = "Adress_Id")
	@SequenceGenerator(name = "adressIdGenerator", sequenceName = "adressSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "adressIdGenerator")
	public int getAdresslId() {
		return adresslId;
	}
	
	public void setAdresslId(int adresslId) {
		this.adresslId = adresslId;
	}
	
	@Column(name = "Adress_User")
	public String getUsuarioCorreo() {
		return usuarioCorreo;
	}
	
	public void setUsuarioCorreo(String usuarioCorreo) {
		this.usuarioCorreo = usuarioCorreo;
	}
	
	@Column(name = "Adress_Password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}

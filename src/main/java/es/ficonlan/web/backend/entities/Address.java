package es.ficonlan.web.backend.entities;

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
@Table(name="Address")
public class Address {
	
	protected int addresslId;
	
	protected String usuarioCorreo;
	protected String password;
	
	@Column(name = "Address_Id")
	@SequenceGenerator(name = "addressIdGenerator", sequenceName = "addressSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "addressIdGenerator")
	public int getAddresslId() {
		return addresslId;
	}
	
	public void setAddresslId(int adresslId) {
		this.addresslId = adresslId;
	}
	
	@Column(name = "Address_User")
	public String getUsuarioCorreo() {
		return usuarioCorreo;
	}
	
	public void setUsuarioCorreo(String usuarioCorreo) {
		this.usuarioCorreo = usuarioCorreo;
	}
	
	@Column(name = "Address_Password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}

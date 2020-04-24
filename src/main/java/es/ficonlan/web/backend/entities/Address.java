/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

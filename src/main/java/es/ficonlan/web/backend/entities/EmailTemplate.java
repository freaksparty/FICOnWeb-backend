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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.serializer.JsonEntityIdSerializer;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
@Table(name="EmailTemplate")
public class EmailTemplate {
	
	private int emailtemplateid;
	private String name;
	private Address address;
	private String filepath;
	private String filename;
	private String asunto;
	private String contenido;
	
	public EmailTemplate() {}

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

	@JsonSerialize(using = JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EmailTemplate_address_id")
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address adress) {
		this.address = adress;
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
		  clave = e.nextElement();
		  valor = datos.get(clave);
		  contenido = contenido.replace(clave, valor);
		}
		
		return new Email(this.getAddress().getUsuarioCorreo(), this.getAddress().getPassword(),
				this.getFilepath(), this.getFilename(),destinatario.getEmail(), this.getAsunto(), contenido);

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
		  clave = e.nextElement();
		  valor = tabla.get(clave);
		  dos = dos.replace(clave, valor);
		}
		
		System.out.println(uno);
		System.out.println(dos);
	}

}

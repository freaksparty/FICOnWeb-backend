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

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.serializer.JsonDateDeserializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonDateSerializer;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Entity
public class User {

    private int userId;
    private String name;
    private String login;
    private String password;
    private String secondPassword;
    private Calendar secondPasswordExpDate;
    private String dni;
    private String email;
    private String phoneNumber;
    private boolean deleted;
    private Set<Role> roles = new HashSet<Role>();
    private String shirtSize;
    private boolean inBlackList;
    private SupportedLanguage defaultLanguage;
    private Calendar dob;
    
	public User() {}

	public User(int userId, String name, String dni, String email, String phoneNumber, String shirtSize) {
        this(name, null, null, dni, email, phoneNumber, shirtSize);
        this.userId = userId;
    }
	
	public User(String name,  String login, String password, String dni, String email, String phoneNumber, String shirtSize) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.secondPassword = password;
        this.dni = dni;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.deleted = false;
        this.shirtSize = shirtSize;
        this.dob = Calendar.getInstance();
    }

    @Column(name = "User_id")
    @SequenceGenerator(name = "userIdGenerator", sequenceName = "userSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "userIdGenerator")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "User_name")
    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "User_login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @JsonIgnore
    @Column(name = "User_password")
    public String getPassword() {
        return password;
    }

    @JsonProperty(value = "password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @Column(name = "User_secondPassword")
    public String getSecondPassword() {
		return secondPassword;
	}

    @JsonProperty(value = "secondpassword")
	public void setSecondPassword(String secondPassword) {
		this.secondPassword = secondPassword;
	}
    
    @JsonIgnore
    @Column(name = "User_secondPasswordExpDate")
	public Calendar getSecondPasswordExpDate() {
		return secondPasswordExpDate;
	}

	public void setSecondPasswordExpDate(Calendar secondPasswordExpDate) {
		this.secondPasswordExpDate = secondPasswordExpDate;
	}

	@Column(name = "User_dni")
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Column(name = "User_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "User_telf")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    @Column(name = "User_checked")
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Role_User", joinColumns = {
        @JoinColumn(name = "Role_User_User_id")}, inverseJoinColumns = {
        @JoinColumn(name = "Role_User_Role_id")})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Column(name = "User_shirtSize")
    public String getShirtSize() {
        return this.shirtSize;
    }
    
    public void setShirtSize(String sirtSize) {
        this.shirtSize = sirtSize;
    }
    
    @JsonIgnore
    @Column(name = "User_inBlackList")
	public boolean isInBlackList() {
		return inBlackList;
	}

	public void setInBlackList(boolean inBlackList) {
		this.inBlackList = inBlackList;
	}

	@OneToOne
	@JoinColumn(name="User_defaultLanguage")
	public SupportedLanguage getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(SupportedLanguage defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
	@Column(name = "User_borndate")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Calendar getDob() {
		return dob;
	}

	public void setDob(Calendar fechaNacimiento) {
		this.dob = fechaNacimiento;
	}

	
}

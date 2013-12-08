package es.ficonlan.web.backend.model.user;

import es.ficonlan.web.backend.model.role.Role;

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
import javax.persistence.SequenceGenerator;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
@Entity
public class User {

    private int userId;
    private String name;
    private String login;
    private String password;
    private String dni;
    private String email;
    private String phoneNumber;
    private boolean deleted;
    private Set<Role> roles;
    //private Set<Activity> participation;
    private int shirtSize;

    public User() {
    }

    ;

	public User(String name, String login, String password, String dni, String email, String phoneNumber, int shirtSize) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.dni = dni;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.deleted = false;
        this.shirtSize = shirtSize;
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

    @Column(name = "User_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Column(name = "User_checked")
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Role_User", joinColumns = {
        @JoinColumn(name = "Role_User_User_id")}, inverseJoinColumns = {
        @JoinColumn(name = "Role_User_Role_id")})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setSirtSize(int sirtSize) {
        this.shirtSize = sirtSize;
    }

    @Column(name = "User_shirtSize")
    public int getSirtSize() {
        return this.shirtSize;
    }

    //@ManyToMany
    //@JoinTable(name = "User_Activity", joinColumns = {
    //    @JoinColumn(name = "User_Activity_User_id")}, inverseJoinColumns = {
    //    @JoinColumn(name = "User_Activity_Activity_id")})
    /*public Set<Activity> getParticipation() {
     return participation;
     }

     public void setParticipation(Set<Activity> participation) {
     this.participation = participation;
     }*/
}

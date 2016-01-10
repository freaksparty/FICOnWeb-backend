package es.ficonlan.web.backend.entities;

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
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
public class Role {
	
	private int roleId;
	private String roleName;
	private Set<UseCase> useCases = new HashSet<UseCase>(); 
	
	public Role() {};
	
	public Role(String name)
	{
		super();
		this.roleName = name;
	}
	
	@Column(name = "Role_id")
	@SequenceGenerator(name = "roleIdGenerator", sequenceName = "RoleSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "roleIdGenerator")
	public int getRoleId() {
		return this.roleId;
	}
	
	public void setRoleId(int newId)
	{
		this.roleId = newId;
	}

	@Column(name = "role_name")
	public String getRoleName()
	{
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Role_UseCase", joinColumns = {
			@JoinColumn(name = "Role_UseCase_Role_id")}, inverseJoinColumns = {
	        @JoinColumn(name = "Role_UseCase_UseCase_id")})
	public Set<UseCase> getUseCases() {
		return useCases;
	}

	public void setUseCases(Set<UseCase> useCases) {
		this.useCases = useCases;
	}

}

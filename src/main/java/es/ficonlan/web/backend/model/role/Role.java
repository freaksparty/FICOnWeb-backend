package es.ficonlan.web.backend.model.role;


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

import es.ficonlan.web.backend.model.useCase.UseCase;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
public class Role {
	
	private long roleId;
	private String roleName;
	private Set<UseCase> useCases; 
	
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
	public long getRoleId() {
		return this.roleId;
	}
	
	public void setRoleId(long newId)
	{
		this.roleId = newId;
	}
	
	@Column(name = "role_Name")
	public String getRoleName()
	{
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Role_UserCase", joinColumns = {
			@JoinColumn(name = "Role_UserCase_Role_Id")}, inverseJoinColumns = {
	        @JoinColumn(name = "Role_UserCase_User_Case_Id")})
	public Set<UseCase> getUseCases() {
		return useCases;
	}

	public void setUseCases(Set<UseCase> useCases) {
		this.useCases = useCases;
	}

}

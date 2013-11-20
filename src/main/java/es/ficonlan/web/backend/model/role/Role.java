package es.ficonlan.web.backend.model.role;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
public class Role {
	
	private long roleId;
	private String roleName;
	
	public Role() {};
	
	public Role(String name)
	{
		super();
		this.roleName = name;
	}
	
	@Column(name = "roleId")
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
	
	@Column(name = "roleName")
	public String getRoleName()
	{
		return this.roleName;
	}

}

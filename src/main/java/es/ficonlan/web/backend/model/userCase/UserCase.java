package es.ficonlan.web.backend.model.userCase;


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
public class UserCase {
	
	private long userCaseId;
	private String userCaseName;
	
	public UserCase() {};
	
	public UserCase(String name)
	{
		super();
		this.userCaseName = name;
	}
	
	@Column(name = "userCase_id")
	@SequenceGenerator(name = "userCaseIdGenerator", sequenceName = "userCaseSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "userCaseIdGenerator")
	public long getUserCaseId() {
		return this.userCaseId;
	}
	
	public void setUserCaseId(long newId)
	{
		this.userCaseId = newId;
	}
	
	@Column(name = "userCase_name")
	public String getUserCaseName()
	{
		return this.userCaseName;
	}

	public void setUserCaseName(String newName) {
		this.userCaseName = newName;
	}
}

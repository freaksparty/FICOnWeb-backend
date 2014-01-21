package es.ficonlan.web.backend.model.usecase;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
@Table(name="UserCase")
public class UseCase {
	
	private long useCaseId;
	private String useCaseName;
	
	public UseCase() {};
	
	public UseCase(String name)
	{
		super();
		this.useCaseName = name;
	}
	
	@Column(name = "userCase_id")
	@SequenceGenerator(name = "userCaseIdGenerator", sequenceName = "userCaseSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "userCaseIdGenerator")
	public long getUserCaseId() {
		return this.useCaseId;
	}
	
	public void setUserCaseId(long newId)
	{
		this.useCaseId = newId;
	}
	
	@Column(name = "userCase_name")
	public String getUseCaseName()
	{
		return this.useCaseName;
	}
	
	public void setUseCaseName(String newName) {
		this.useCaseName = newName;
	}
}

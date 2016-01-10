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
 * @version 1.0
 */
@Entity
@Table(name="UseCase")
public class UseCase {
	
	private int useCaseId;
	private String useCaseName;
	
	public UseCase() {};
	
	public UseCase(String name)
	{
		super();
		this.useCaseName = name;
	}
	
	@Column(name = "useCase_id")
	@SequenceGenerator(name = "useCaseIdGenerator", sequenceName = "useCaseSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "useCaseIdGenerator")
	public int getUserCaseId() {
		return this.useCaseId;
	}
	
	public void setUserCaseId(int newId)
	{
		this.useCaseId = newId;
	}
	
	@Column(name = "useCase_name")
	public String getUseCaseName()
	{
		return this.useCaseName;
	}
	
	public void setUseCaseName(String newName) {
		this.useCaseName = newName;
	}
}

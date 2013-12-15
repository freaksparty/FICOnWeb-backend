package es.ficonlan.web.backend.model.supportedlanguage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
@Entity
@Table(name="Language")
public class SupportedLanguage {
	
	private int languageId;
	private String languageName;
	
	@Column(name = "Language_id")
	@SequenceGenerator(name = "languageIdGenerator", sequenceName = "languageSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "languageIdGenerator")
	public int getLanguageId() {
		return languageId;
	}
	
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	
	@Column(name = "Language_name")
	public String getLanguageName() {
		return languageName;
	}
	
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	
	

}

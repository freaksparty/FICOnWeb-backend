package es.ficonlan.web.backend.model.email;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Repository("EmailDao")
public class EmailDaoHibernate extends GenericDaoHibernate<Email,Integer> implements EmailDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getAllEmails() {
		return getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"ORDER BY e.Email_Date").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getConfirmedEmails() {
		return getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.Email_Confirmation = true" +
	        	"ORDER BY e.Email_SendDate").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getNoConfirmedEmails() {
		return getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.Email_Confirmation = false" +
	        	"ORDER BY e.Email_Date").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getEmailByDestination(String destino) {
		return getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.Email_destination =  :destino" +
	        	"ORDER BY e.Email_Date").setParameter("destino", destino).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getEmailByDireccionEnvio(int direccionId) {
		return getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.Adress_Id = :direccionId" +
	        	"ORDER BY e.Email_Date").setParameter("direccionId", direccionId).list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getEmailByRegistration(int registrationId) {
		return getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.Email_registration_id = :registrationId" +
	        	"ORDER BY e.Email_Date").setParameter("registrationId", registrationId).list();
	}
	
	@Override
	public Email getLasEmailByRegistration(int registrationId){
		return (Email) getSession()
				.createQuery("SELECT e FROM Email e e.Email_registration_id = :registrationId ORDER BY e.Email_date DESC")
				.setParameter("registrationId", registrationId).setFirstResult(0).setMaxResults(1).uniqueResult();
	}

}

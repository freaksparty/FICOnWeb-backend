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
	        	"ORDER BY e.date").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getConfirmedEmails() {
		return getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.confirmation = true" +
	        	"ORDER BY e.sendDate").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getNoConfirmedEmails() {
		return getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.confirmation = false" +
	        	"ORDER BY e.date").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getEmailByDestination(int destinatario) {
		return getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.destinatario.userId =  :destino" +
	        	"ORDER BY e.date").setParameter("destino", destinatario).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getEmailByDireccionEnvio(int direccionId) {
		return getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.adresslId = :direccionId" +
	        	"ORDER BY e.date").setParameter("direccionId", direccionId).list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getEmailByRegistration(int registrationId) {
		return getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.registration.registrationId = :registrationId" +
	        	"ORDER BY e.date").setParameter("registrationId", registrationId).list();
	}
	
	@Override
	public Email getLasEmailByRegistration(int registrationId){
		return (Email) getSession()
				.createQuery("SELECT e FROM Email e e.registration.registrationId = :registrationId ORDER BY e.date DESC")
				.setParameter("registrationId", registrationId).setFirstResult(0).setMaxResults(1).uniqueResult();
	}

}

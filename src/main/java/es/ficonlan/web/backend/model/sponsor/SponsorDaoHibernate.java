package es.ficonlan.web.backend.model.sponsor;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Repository("sponsorDao")
public class SponsorDaoHibernate extends GenericDaoHibernate<Sponsor,Integer> implements SponsorDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Sponsor> getAll(int startIndex, int cont, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT s " +
		        "FROM Sponsor s ORDER BY n." + orderBy +  aux);
		
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}
	
	@Override
	public long getAllTAM() {
		return (long) getSession().createQuery(
				"SELECT s FROM Sponsor s"
		        ).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sponsor> getByEvent(int eventId) {
		return getSession().createQuery(
	        	"SELECT s " +
		        "FROM Sponsor s " + 
	        	"WHERE s.event.eventId = :eventId").setParameter("eventId", eventId).list();
	}

}

package es.ficonlan.web.backend.model.emailadress;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Repository("AdressDao")
public class AdressDaoHibernate extends GenericDaoHibernate<Adress,Integer> implements AdressDao {

	@Override
	public Adress findAdressByName(String adressName) {
		return (Adress) getSession()
				.createQuery("SELECT a FROM Adress a WHERE a.usuarioCorreo = :adressName")
				.setParameter("adressName", adressName).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Adress> getAllAdress() {
		return getSession().createQuery(
	        	"SELECT a " +
		        "FROM Adress a " +
	        	"ORDER BY a.usuarioCorreo").list();
	}


}

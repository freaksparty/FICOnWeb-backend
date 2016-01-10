package es.ficonlan.web.backend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.entities.Address;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Repository("AdressDao")
public class AdressDaoHibernate extends GenericDaoHibernate<Address,Integer> implements AdressDao {

	@Override
	public Address findAdressByName(String adressName) {
		return (Address) getSession()
				.createQuery("SELECT a FROM Adress a WHERE a.usuarioCorreo = :adressName")
				.setParameter("adressName", adressName).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Address> getAllAdress() {
		return getSession().createQuery(
	        	"SELECT a " +
		        "FROM Adress a " +
	        	"ORDER BY a.usuarioCorreo").list();
	}


}

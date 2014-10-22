package es.ficonlan.web.backend.model.emailadress;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Repository("AdressDaoDao")
public class AdressDaoHibernate extends GenericDaoHibernate<Adress,Integer> implements AdressDao {

	@Override
	public Adress findAdressByName(String adressName) {
		return (Adress) getSession()
				.createQuery("SELECT a FROM Adress a WHERE a.Adress_Name = :adressName")
				.setParameter("adressName", adressName).uniqueResult();
	}


}

package es.ficonlan.web.backend.dao;

import java.util.List;

import es.ficonlan.web.backend.entities.Adress;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
public interface AdressDao extends GenericDao<Adress, Integer> {
	
	public List<Adress> getAllAdress();
	
	public Adress findAdressByName(String adressName);

}

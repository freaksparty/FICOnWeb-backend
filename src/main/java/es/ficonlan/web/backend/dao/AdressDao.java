package es.ficonlan.web.backend.dao;

import java.util.List;

import es.ficonlan.web.backend.entities.Address;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
public interface AdressDao extends GenericDao<Address, Integer> {
	
	public List<Address> getAllAdress();
	
	public Address findAdressByName(String adressName);

}

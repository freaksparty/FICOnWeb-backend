package es.ficonlan.web.backend.dao;

import java.util.List;

import es.ficonlan.web.backend.entities.UseCase;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
public interface UseCaseDao extends GenericDao<UseCase,Integer>{
	
	public List<UseCase> getAll();
	
	public UseCase findByName(String name);

}

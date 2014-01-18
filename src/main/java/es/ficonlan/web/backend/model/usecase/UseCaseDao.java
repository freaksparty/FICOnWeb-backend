package es.ficonlan.web.backend.model.usecase;

import java.util.List;

import es.ficonlan.web.backend.model.util.dao.GenericDao;

public interface UseCaseDao extends GenericDao<UseCase,Integer>{
	
	public List<UseCase> getAll();
	
	public UseCase findByName(String name);

}

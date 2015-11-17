package es.ficonlan.web.backend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.entities.UseCase;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Repository("useCaseDao")
public class UseCaseDaoHibernate extends GenericDaoHibernate<UseCase,Integer> implements UseCaseDao {

	@SuppressWarnings("unchecked")
	public List<UseCase> getAll(){
		return getSession().createQuery(
	        	"SELECT uc " +
		        "FROM UseCase uc " +
	        	"ORDER BY uc.useCaseName").list();
	}
	
	public UseCase findByName(String name) {
		return (UseCase) getSession()
				.createQuery("SELECT uc FROM UseCase uc WHERE uc.useCaseName = :name")
				.setParameter("name", name).uniqueResult();
	}
	
}

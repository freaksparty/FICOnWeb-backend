package es.ficonlan.web.backend.model.usecase;

import org.springframework.stereotype.Repository;
import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

@Repository("useCaseDao")
public class UseCaseDaoHibernate extends GenericDaoHibernate<UseCase,Integer> implements UseCaseDao{

}

package es.ficonlan.web.backend.model.role;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.user.UserDao;
import es.ficonlan.web.backend.util.dao.GenericDaoHibernate;

/**
 * @author Ramón José Casares Porto
 * @version 1.0
 */
@Repository("roleDao")
public class RoleDaoHibernate extends GenericDaoHibernate<Role,Integer> implements RoleDao {
	
	@SuppressWarnings("unchecked")
	public List<Role> getAllRoles(){
		return getSession().createQuery(
	        	"SELECT r " +
		        "FROM Role r " +
	        	"ORDER BY r.role_id").list();
	}

}
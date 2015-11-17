package es.ficonlan.web.backend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.entities.Role;

/**
 * @author Ramón José Casares Porto
 * @version 1.0
 */
@Repository("roleDao")
public class RoleDaoHibernate extends GenericDaoHibernate<Role,Integer> implements RoleDao {
		
	public Role findByName(String name) {
		return (Role) getSession()
				.createQuery("SELECT r FROM Role r WHERE r.roleName = :name")
				.setParameter("name", name).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> getAllRoles(){
		return getSession().createQuery(
	        	"SELECT r " +
		        "FROM Role r " +
	        	"ORDER BY r.roleName").list();
	}

}
package es.ficonlan.web.backend.model.role;

import java.util.List;

import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.util.dao.GenericDao;

/**
 * @author Ramón José Casares Porto
 * @version 1.0
 */
public interface RoleDao extends GenericDao<Role,Integer> {
	
	public List<Role> getAllRoles();

}


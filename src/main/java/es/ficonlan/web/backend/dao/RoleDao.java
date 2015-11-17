package es.ficonlan.web.backend.dao;

import java.util.List;

import es.ficonlan.web.backend.entities.Role;

/**
 * @author Ramón José Casares Porto
 * @version 1.0
 */
public interface RoleDao extends GenericDao<Role,Integer> {
	
	public Role findByName(String name);
	
	public List<Role> getAllRoles();

}


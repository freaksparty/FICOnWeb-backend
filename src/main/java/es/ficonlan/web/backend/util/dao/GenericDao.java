package es.ficonlan.web.backend.util.dao;

import java.io.Serializable;

import es.ficonlan.web.backend.util.exceptions.InstanceException;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
public interface GenericDao<E, PK extends Serializable> {

	void save(E entity);

	E find(PK id) throws InstanceException;

	boolean exists(PK id);

	void remove(PK id) throws InstanceException;

}

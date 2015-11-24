package es.ficonlan.web.backend.util.cache;

import javax.ws.rs.core.EntityTag;

public interface Cacheable {

	//In seconds
	long timeToExpire();
	EntityTag getTag();
	
}

package es.ficonlan.web.backend.jersey.util;

import java.util.ArrayList;
import java.util.List;

import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;

import es.ficonlan.web.backend.annotations.UseCasePermission;

public final class SessionControlFilterFactory implements ResourceFilterFactory {

    @Override
    public List<ResourceFilter> create(AbstractMethod abstractMethod) {

    	List<ResourceFilter> filters = new ArrayList<ResourceFilter>();
        
    	UseCasePermission permission = abstractMethod.getMethod().getAnnotation(UseCasePermission.class);
    	if(permission != null) {
    		filters.add((ResourceFilter) new SessionControlFilter(permission.allowed()));
    	} else {
    		//TODO: When all the old useCase check is removed, this should throw an Exception
    	}
    	
        return filters;
    }
}
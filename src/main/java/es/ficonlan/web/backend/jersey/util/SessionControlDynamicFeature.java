package es.ficonlan.web.backend.jersey.util;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import es.ficonlan.web.backend.annotations.UseCasePermission;

public final class SessionControlDynamicFeature implements DynamicFeature {

    /*@Override Jersey 1.x way
    public List<ResourceFilter> create(AbstractMethod abstractMethod) {
    	
    	List<ResourceFilter> filters = new ArrayList<ResourceFilter>();
        
    	UseCasePermission permission = abstractMethod.getMethod().getAnnotation(UseCasePermission.class);
    	if(permission != null) {
    		filters.add((ResourceFilter) new SessionControlFilter(permission.allowed()));
    	} else {
    		//When all the old useCase check is removed, this should throw an Exception
    	}
    	
        return filters;
    }*/

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		
		UseCasePermission permission = resourceInfo.getResourceMethod().getAnnotation(UseCasePermission.class);
		if(permission != null) {
			context.register(new SessionControlFilter(permission.value()));
    	} else {
    		//TODO: When all the old useCase check is removed, this should throw an Exception
    	}
		
	}
}
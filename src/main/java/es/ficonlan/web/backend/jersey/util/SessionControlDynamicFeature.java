/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
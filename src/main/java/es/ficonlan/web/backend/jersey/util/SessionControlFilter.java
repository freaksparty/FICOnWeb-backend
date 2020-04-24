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

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import es.ficonlan.web.backend.model.util.session.Session;
import es.ficonlan.web.backend.model.util.session.SessionManager;

public class SessionControlFilter implements ContainerRequestFilter {

	private String allowed;
	
	public SessionControlFilter(String _allowed) {
		this.allowed = _allowed;
	}

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
        String sessionId = requestContext.getHeaderString("sessionId");
        Session s = null;
        if(sessionId != null) {
        	s = SessionManager.getSession(sessionId);
        	if(s == null)
        		s = SessionManager.getDefaultSession();
        } else {
        	s = SessionManager.getDefaultSession();
        }
        
    	requestContext.setProperty("session", s);
		
        if(!SessionManager.checkPermissions(s, allowed)) {
        	requestContext.abortWith(Response.status(403).build());
        }
	}
	
}

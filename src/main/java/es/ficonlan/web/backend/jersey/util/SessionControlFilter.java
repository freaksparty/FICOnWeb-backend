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

package es.ficonlan.web.backend.jersey.util;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;

import es.ficonlan.web.backend.model.util.session.Session;
import es.ficonlan.web.backend.model.util.session.SessionManager;

public class SessionControlFilter implements ContainerResponseFilter {

	private String allowed;
	
	public SessionControlFilter(String _allowed) {
		this.allowed = _allowed;
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		// TODO Apéndice de método generado automáticamente
        String sessionId = requestContext.getHeaderString("sessionId");
        Session s = null;
        if(sessionId != null) {
        	s = SessionManager.getSession(sessionId);
        	if(s != null)
        		requestContext.setProperty("session", s);
        	else
        		requestContext.abortWith(Response.status(403).build()); //TODO: assign anonymous session
        } else {
        	requestContext.abortWith(Response.status(403).build()); //TODO: assign anonymous session
        }
		
        if(!SessionManager.checkPermissions(s, allowed)) {
        	requestContext.abortWith(Response.status(403).build());
        }
	}

}

package es.ficonlan.web.backend.jersey.util;

 
import java.io.IOException;
 
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class CORSResponseFilter implements ContainerResponseFilter {
 
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
 
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
 
        headers.add("Access-Control-Allow-Origin", "*");
        //headers.add("Access-Control-Allow-Origin", "http://ficonlan.es");        
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");    
        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, sessionId");
        //headers.add("Access-Control-Allow-Headers", "sessionId");
    }
 
}
package es.ficonlan.web.backend.jersey;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.ficonlan.web.backend.jersey.resources.ActivityResource;
import es.ficonlan.web.backend.jersey.resources.EventResource;
import es.ficonlan.web.backend.jersey.resources.SessionResource;
import es.ficonlan.web.backend.jersey.resources.UserResource;
import es.ficonlan.web.backend.jersey.util.ServiceExceptionMapper;
import es.ficonlan.web.backend.model.userservice.UserService;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "https://0.0.0.0:1194/ficonlan/";
    private static final String KEYSTORE_SERVER_FILE = "./certs/Keystore";
    private static final String KEYSTORE_SERVER_PWD = "1q2w3e";
    private static final String TRUSTORE_SERVER_FILE = "./certs/Truststore";
    private static final String TRUSTORE_SERVER_PWD = "1q2w3e";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in es.ficonlan.web.prueba package
        final ResourceConfig rc = new ResourceConfig();
        //rc.packages("es.ficonlan.web.backend.jersey");
        rc.register(ActivityResource.class);
        rc.register(EventResource.class);
        rc.register(UserResource.class);
        rc.register(SessionResource.class);
        rc.register(JacksonFeature.class);
        rc.register(ServiceExceptionMapper.class);
       
        SSLContextConfigurator sslContext = new SSLContextConfigurator(); 
        sslContext.setKeyStoreFile(KEYSTORE_SERVER_FILE);
        sslContext.setKeyStorePass(KEYSTORE_SERVER_PWD);
        sslContext.setTrustStoreFile(TRUSTORE_SERVER_FILE);
        sslContext.setTrustStorePass(TRUSTORE_SERVER_PWD);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc, true, new SSLEngineConfigurator(sslContext).setClientMode(false).setNeedClientAuth(true));
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException { 
    	
    	//Spring context initialization.
    	@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
    	
    	//UserService Initialization
    	UserService userService = ctx.getBean(UserService.class);
    	userService.initialize();
    	
        final HttpServer server = startServer();
        
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}


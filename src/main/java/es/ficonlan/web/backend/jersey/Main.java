package es.ficonlan.web.backend.jersey;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.ficonlan.web.backend.jersey.resources.ActivityResource;
import es.ficonlan.web.backend.jersey.resources.BlackListResource;
import es.ficonlan.web.backend.jersey.resources.EmailAdressResource;
import es.ficonlan.web.backend.jersey.resources.EmailTemplateResource;
import es.ficonlan.web.backend.jersey.resources.EventResource;
import es.ficonlan.web.backend.jersey.resources.LoginResource;
import es.ficonlan.web.backend.jersey.resources.NewsResource;
import es.ficonlan.web.backend.jersey.resources.RegistrationResource;
import es.ficonlan.web.backend.jersey.resources.RoleResource;
import es.ficonlan.web.backend.jersey.resources.SessionResource;
import es.ficonlan.web.backend.jersey.resources.SponsorResource;
import es.ficonlan.web.backend.jersey.resources.UserResource;
import es.ficonlan.web.backend.jersey.resources.UsersResource;
import es.ficonlan.web.backend.jersey.util.CORSResponseFilter;
import es.ficonlan.web.backend.jersey.util.ServiceExceptionMapper;
import es.ficonlan.web.backend.model.email.EmailFIFO;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.session.SessionManager;

/**
 * Main class.
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo
 */
public class Main {

	private static Properties properties;

	static {
		try {
			properties = new Properties();
			InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("backend.properties");
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Could not read config file: " + e.getMessage());
		}
	}
	
	private static final int SESSION_TIMEOUT = Math.abs(Integer.parseInt(properties.getProperty("session.timeout")));
	private static final int SESSION_CLEAN_FREQUENCY = Math.abs(Integer.parseInt(properties.getProperty("session.cleanFrequency")));
	private static final URI SERVER_URI = URI.create(properties.getProperty("server.baseUri"));
	private static final String KEYSTORE_FILE = properties.getProperty("server.keystoreFile");
	private static final String KEYSTORE_PASS = properties.getProperty("server.keystorePass");
	private static final String TRUSTSTORE_FILE = properties.getProperty("server.truststoreFile");
	private static final String TRUSTSTORE_PASS = (properties.getProperty("server.truststorePass"));
	private static final boolean IS_SECURE = Boolean.parseBoolean(properties.getProperty("server.isSecure"));

	// Base URI the Grizzly HTTP server will listen on

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 * 
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer() {
		// create a resource config that scans for JAX-RS resources and
		// providers
		// in es.ficonlan.web.prueba package
		final ResourceConfig rc = new ResourceConfig();
		//rc.packages("es.ficonlan.web.backend.jersey.resources");
		
		rc.register(ActivityResource.class);
		rc.register(EventResource.class);
		rc.register(UserResource.class);
		rc.register(UsersResource.class);
		rc.register(SessionResource.class);
		rc.register(LoginResource.class);
		rc.register(NewsResource.class);
		rc.register(JacksonFeature.class);
		rc.register(ServiceExceptionMapper.class);
		rc.register(EmailAdressResource.class);
		rc.register(RoleResource.class);
		rc.register(BlackListResource.class); 
		rc.register(SponsorResource.class);
		rc.register(EmailTemplateResource.class);
		rc.register(RegistrationResource.class);
		
		rc.register(CORSResponseFilter.class);
		
		SSLContextConfigurator sslContext = new SSLContextConfigurator();
		sslContext.setKeyStoreFile(KEYSTORE_FILE);
		sslContext.setKeyStorePass(KEYSTORE_PASS);
		sslContext.setTrustStoreFile(TRUSTSTORE_FILE);
		sslContext.setTrustStorePass(TRUSTSTORE_PASS);

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		return GrizzlyHttpServerFactory.createHttpServer(SERVER_URI, rc, IS_SECURE, new SSLEngineConfigurator(sslContext).setClientMode(false).setNeedClientAuth(true));
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// Spring context initialization.
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");

		// UserService Initialization
		UserService userService = ctx.getBean(UserService.class);
		userService.initialize();

		// Session clean thread initialization
		Thread scth = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					while (true) {
						Thread.sleep(SESSION_CLEAN_FREQUENCY);
						SessionManager.cleanOldSessions(SESSION_TIMEOUT);
					}
				} catch (Exception e) {
					throw new RuntimeException("Session clean thread error: " + e.getMessage());
				}
			}
		};
		scth.start();

		EmailFIFO.startEmailQueueThread();
		
		// Server Start
		final HttpServer server = startServer();   
		//Añadir parte estatica en la siguente linea
		server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("//web//frontend"),"/");
		System.out.println(String.format("Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...", properties.getProperty("server.baseUri")));
	}
}

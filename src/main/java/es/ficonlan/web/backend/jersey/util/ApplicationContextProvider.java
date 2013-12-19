package es.ficonlan.web.backend.jersey.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Daniel Gómez Silva
 */
public class ApplicationContextProvider implements ApplicationContextAware {
	
	private static ApplicationContext ctx = null;

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}
}

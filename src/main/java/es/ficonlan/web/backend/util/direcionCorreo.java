package es.ficonlan.web.backend.util;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class direcionCorreo {
	
	String contenido;
	
	public direcionCorreo() {};
	
	public direcionCorreo(String contenido) {
		this.contenido = contenido;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
}

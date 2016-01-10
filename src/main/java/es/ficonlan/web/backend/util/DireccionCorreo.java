package es.ficonlan.web.backend.util;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class DireccionCorreo {
	
	String contenido;
	
	public DireccionCorreo() {};
	
	public DireccionCorreo(String contenido) {
		this.contenido = contenido;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
}

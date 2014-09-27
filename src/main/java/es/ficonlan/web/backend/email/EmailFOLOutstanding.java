package es.ficonlan.web.backend.email;

public class EmailFOLOutstanding extends Email {

	private static String direccion = "patrocinio@ficonlan.es";
	private static String clave = "patrocinioficonlan";
	
	private static String nombreArchivo = "";
	private static String rutaArchivo = "";
	
	private static String asunto = "";
	private static String mensaje = "";

	public EmailFOLOutstanding(String destinatario) {
		super(direccion, clave, rutaArchivo, nombreArchivo, destinatario,
				asunto, mensaje);
	}


}

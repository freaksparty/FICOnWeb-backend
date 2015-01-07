package es.ficonlan.web.backend.jersey.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<Exception> {

	class ErrorMessage{
		private int exceptionCode;
		private String useCase;
		private String errorMessage;
		private String field;
		
		public ErrorMessage() {}

		public ErrorMessage(ServiceException e) {
			this.exceptionCode = e.getErrorCode();
			this.useCase = e.getUseCase();
			this.errorMessage = e.getMessage();
			this.field = e.getField();
		}
		
		public int getExceptionCode() {
			return exceptionCode;
		}

		public void setExceptionCode(int code) {
			this.exceptionCode = code;
		}

		public String getUseCase() {
			return useCase;
		}

		public void setUseCase(String useCase) {
			this.useCase = useCase;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String message) {
			this.errorMessage = message;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}	
	}
	
	public Response toResponse(final Exception exception) {
		
		if (exception instanceof ServiceException) 
		{
			//return Response.status(Status.BAD_REQUEST).entity(new ErrorMessage((ServiceException) exception)).type(MediaType.APPLICATION_JSON).build();
			System.out.println(exception.toString());
			return Response.status(Status.BAD_REQUEST).entity(exception.toString()).type("text/plain").build();
		}
		else if (exception instanceof WebApplicationException) 
		{
			WebApplicationException webEx = (WebApplicationException)exception;
		    System.out.println(webEx.toString());
		        
		    return webEx.getResponse();
		} else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal error").type("text/plain").build();		    
	}
}

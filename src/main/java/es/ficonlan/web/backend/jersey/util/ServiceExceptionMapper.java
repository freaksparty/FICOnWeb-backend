package es.ficonlan.web.backend.jersey.util;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Daniel Gómez Silva
 */
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

	class ErrorMessage{
		private String exception;
		private int code;
		private String useCase;
		private String message;
		private String field;
		
		public ErrorMessage() {}

		public ErrorMessage(ServiceException e) {
			super();
			this.exception = "ServiceException";
			this.code = e.getErrorCode();
			this.useCase = e.getUseCase();
			this.message = e.getMessage();
			this.field = e.getField();
		}
		
		public String getException() {
			return exception;
		}

		public void setException(String exception) {
			this.exception = exception;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getUseCase() {
			return useCase;
		}

		public void setUseCase(String useCase) {
			this.useCase = useCase;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}	
	}
	
	public Response toResponse(final ServiceException exception) {
		return Response.status(Status.BAD_REQUEST).entity(new ErrorMessage(exception)).type(MediaType.APPLICATION_JSON).build();
	}

}

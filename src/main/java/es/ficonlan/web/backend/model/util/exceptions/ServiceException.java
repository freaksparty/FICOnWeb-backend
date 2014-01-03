package es.ficonlan.web.backend.model.util.exceptions;


/**
 * Exception Codes:<br>
 * 01 - Invalid session<br>
 * 02 - Permission denied<br>
 * 03 - Duplicated unique field<br>
 * 04 - Incorrect field<br>
 * 05 - Missing required field<br>
 * 06 - Instance not found<br>
 * 07 - There is already a session<br>
 * 08 - Event maximum number of participants reached<br>
 * 99 - System unexpected error (RuntimeException)
 * 
 * @author Daniel Gómez Silva
 *
 */
public class ServiceException extends Exception{

		private static final long serialVersionUID = 1L;
		private int errorCode;
		private String useCase;
		private String field;
		
		private static String getMessage(int errorCode){
			switch (errorCode){
				case 01: return "Invalid session";
				case 02: return "Permission denied";
				case 03: return "Duplicated unique field";
				case 04: return "Incorrect field";
				case 05: return "Missing required field";
				case 06: return "Instance not found";
				case 07: return "There is already a session";
				case 99: return "System unexpected error";
			default: return null;
			}
		}	

		public ServiceException(int errorCode, String useCase) {
			super("ServiceException(Code = " + errorCode + ", UseCase = " + useCase + ", Message = " + getMessage(errorCode) + ")");
			this.errorCode = errorCode;
			this.useCase = useCase;
		}
		
		public ServiceException(int errorCode, String useCase, String field) {
			super("ServiceException(Code = " + errorCode + ", UseCase = " + useCase + ", Message = " + getMessage(errorCode) + ", Field:"+ field +")");
			this.errorCode = errorCode;
			this.useCase = useCase;
			this.field = field;
		}
		
		public String getField() {
			return field;
		}

		public String getMessage(){
			return getMessage(this.errorCode);
		}
		
		public int getErrorCode() {
			return errorCode;
		}

		public String getUseCase() {
			return useCase;
		}
	
}

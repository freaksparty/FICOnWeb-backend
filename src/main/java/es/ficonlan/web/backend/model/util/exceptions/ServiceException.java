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
 * 09 - Registration out of time<br>
 * 10 - User isn't registered in the event<br>
 * 11 - Activity maximum number of participants reached<br>
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
				case  1: return "Invalid session";
				case  2: return "Permission denied";
				case  3: return "Duplicated unique field";
				case  4: return "Incorrect field";
				case  5: return "Missing required field";
				case  6: return "Instance not found";
				case  7: return "There is already a session";
				case  8: return "Event maximum number of participants reached";
				case  9: return "Registration out of time";
				case 10: return "User isn't registered in the event";
				case 11: return "Activity maximum number of participants reached";
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

package es.ficonlan.web.backend.model.util.exceptions;

/**
 * Exception Codes:<br>
 * 01 - Invalid session<br>
 * 02 - Permission denied<br>
 * 03 - Duplicated login<br>
 * 04 - Incorrect login<br>
 * 05 - Incorrect password<br>
 * 06 - User Not Found<br>
 * 07 - Role Not Found<br>
 * 08 - User Case Not Found<br>
 * 09 - Language Not Found<br>
 * 10 - There is already a session.<br>
 * 
 * @author Daniel Gómez Silva
 *
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception{

		private int errorCode;
		private String useCase;
		private String specificMessage;

		public ServiceException(int errorCode, String useCase, String specificMessage) {

			super("ServiceException(Code = " + errorCode + ", UseCase = " + useCase + ", Message = " + specificMessage + ")");
			this.errorCode = errorCode;
			this.useCase = useCase;
			this.specificMessage = specificMessage;

		}

		public int getErrorCode() {
			return errorCode;
		}

		public String getUseCase() {
			return useCase;
		}

		public String getSpecificMessage() {
			return specificMessage;
		}

		
}

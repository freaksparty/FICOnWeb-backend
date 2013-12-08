package es.ficonlan.web.backend.model.util.exceptions;

/* Exception Codes:
 * 
 * 01 - Invalid session
 * 02 - Permission denied
 * 03 - Duplicated login
 * 04 - Incorrect login
 * 05 - Incorrect password
 * 
 * 
 * 
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

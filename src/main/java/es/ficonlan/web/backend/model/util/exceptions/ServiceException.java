/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * 12 - This field can´t be modified<br>
 * 13 - You must wait for send your email<br>
 * 14 - Don have enouth age for register in this event<br>
 * 99 - System unexpected error (RuntimeException)
 * 
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
public class ServiceException extends Exception {

	public static final int INVALID_SESSION = 1;
	public static final int PERMISSION_DENIED = 2;
	public static final int DUPLICATED_FIELD = 3;
	public static final int INCORRECT_FIELD = 4;
	public static final int MISSING_FIELD = 5;
	public static final int INSTANCE_NOT_FOUND = 6;
	public static final int SESSION_ALREADY_EXISTS = 7;
	public static final int MAX_NUM_PARTICIPANTS_REACHED = 8;
	public static final int REGISTRATION_OUT_OF_TIME = 9;
	public static final int USER_NOT_REGISTERED_IN_EVENT = 10;
	public static final int MISSING_CONFIG_FILE = 11;
	public static final int CANT_BE_MODIFIED = 12;
	public static final int WAIT_FOR_SEND = 13;
	public static final int YOUR_ARE_TOO_YOUNG = 14;
	public static final int OTHER = 99;
	
	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String useCase;
	private String field;

	public ServiceException(int errorCode) {
		this.errorCode = errorCode;
        this.useCase = Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	public ServiceException(int errorCode, String field) {
		this.errorCode = errorCode;
        this.useCase = Thread.currentThread().getStackTrace()[2].getMethodName();
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		switch (errorCode) {
		case 1:
			return "Invalid session";
		case 2:
			return "Permission denied";
		case 3:
			return "Duplicated unique field";
		case 4:
			return "Incorrect field";
		case 5:
			return "Missing required field";
		case 6:
			return "Instance not found";
		case 7:
			return "There is already a session";
		case 8:
			return "Maximum number of participants reached";
		case 9:
			return "Registration out of time";
		case 10:
			return "User isn't registered in the event";
		case 11:
			return "Missing config file";
		case 12:
			return "Cant be modified";
		case 13:
			return "Wait for send";
		case 14:
			return "You are too young";
		case 99:
			return "System unexpected error";
		default:
			return null;
		}
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getUseCase() {
		return useCase;
	}

	@Override
	public String toString() {
		return "ServiceException [errorCode=" + errorCode + ", useCase=" + useCase + ", field=" + field + ", message=" + this.getMessage() + "]";
	}
	
	

}

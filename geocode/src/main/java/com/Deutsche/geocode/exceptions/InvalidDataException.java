/**
 * 
 */
package com.Deutsche.geocode.exceptions;

/**
 * @author jangid_m
 *
 */
public class InvalidDataException extends RuntimeException {
   private String errorMessage;
	public InvalidDataException() {
		super();
	}

	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
		this.errorMessage = message;
	}

	public InvalidDataException(String message) {
		super(message);
		this.errorMessage = message;
	}

	@Override
	public String getMessage() {
		return errorMessage;
	}
	

}

package com.Deutsche.geocode.exceptions;

/**
 * Exception to handle invalid data
 * 
 * @author jangid_m
 * 
 */
public class InvalidDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;
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

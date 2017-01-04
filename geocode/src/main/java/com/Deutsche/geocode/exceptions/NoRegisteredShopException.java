/**
 * 
 */
package com.Deutsche.geocode.exceptions;

/**
 * Exception to handle no registered shop case
 * 
 * @author jangid_m
 * 
 */
public class NoRegisteredShopException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errMsg = "No shop details has been registered yet.Please register shops first";

	public NoRegisteredShopException() {
		super();
	}

	@Override
	public String getMessage() {
		return errMsg;
	}

}

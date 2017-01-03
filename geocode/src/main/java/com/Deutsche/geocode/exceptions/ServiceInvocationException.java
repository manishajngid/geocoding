/**
 * 
 */
package com.Deutsche.geocode.exceptions;

/**
 * Exception to handle any issues occurred during third external service call
 * 
 * @author jangid_m
 *
 */
public class ServiceInvocationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String serviceName;

	public ServiceInvocationException(String serviceName) {
		super();
		this.serviceName = serviceName;
	}

	@Override
	public String toString() {
		return "ServiceInvocationException [serviceName=" + serviceName + ", toString()=" + super.toString() + "]";
	}

}

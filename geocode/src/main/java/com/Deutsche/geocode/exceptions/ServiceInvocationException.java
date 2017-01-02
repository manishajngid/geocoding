/**
 * 
 */
package com.Deutsche.geocode.exceptions;

/**
 * @author jangid_m
 *
 */
public class ServiceInvocationException extends RuntimeException {
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

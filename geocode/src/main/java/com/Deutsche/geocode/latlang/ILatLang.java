/**
 * 
 */
package com.Deutsche.geocode.latlang;

/**
 * Interface used by service to get  latlang data
 * It provides abstraction over external third party service.
 * Also, coverts any external exception into internal custom exception
 * @author jangid_m
 *
 */
public interface ILatLang {
	/**
	 *  provided latland information for given postcode
	 * @param postCode
	 * @return double array where 0th element present latitude and 1st element present longitude
	 */
	double[] getLatlangFromPostCode(int postCode,Boolean status);

}

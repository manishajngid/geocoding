/**
 * 
 */
package com.Deutsche.geocode.latlang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.Deutsche.geocode.exceptions.ServiceInvocationException;
import com.Deutsche.geocode.latlang.google.GoogleLatLang;

/**
 * @author jangid_m
 *
 */
@Component("latlang")
public class LatLang implements ILatLang {
	@Autowired
	@Qualifier("googleLatLang")
	private GoogleLatLang googleLatLang;

	/* (non-Javadoc)
	 * @see com.Deutsche.geocode.latlang.ILatLang#getLatlangFromPostCode(int)
	 */
	public double[] getLatlangFromPostCode(int postCode) {
		 double[] latlang;
		 try {
			 latlang = googleLatLang.getResult(postCode);
		} catch (Exception e) {
			throw new ServiceInvocationException(googleLatLang.getServiceName());
		}
		return latlang;
	}

}

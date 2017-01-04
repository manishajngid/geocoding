/**
 * 
 */
package com.Deutsche.geocode.latlang;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Deutsche.geocode.exceptions.ServiceInvocationException;
import com.Deutsche.geocode.latlang.google.GoogleLatLang;
import com.Deutsche.geocode.service.GeoControllerService;


/**(non-Javadoc)
 *  @see com.Deutsche.geocode.latlang.ILatLang
 * @author jangid_m
 *
 */
@Component("latlang")
public class LatLang implements ILatLang {
	private transient static final Logger LOG = Logger.getLogger(LatLang.class);
	@Autowired
	@Qualifier("googleLatLang")
	private GoogleLatLang googleLatLang;

	/* (non-Javadoc)
	 * @see com.Deutsche.geocode.latlang.ILatLang#getLatlangFromPostCode(int)
	 */
	public double[] getLatlangFromPostCode(int postCode,Boolean status) {
		 double[] latlang;
		 try {
			 latlang = googleLatLang.getResult(postCode);
		} catch (Exception e) {
			status = Boolean.FALSE;
			LOG.warn("some error occured during  google service invoke");
			throw new ServiceInvocationException(googleLatLang.getServiceName());
		}
		return latlang;
	}

}

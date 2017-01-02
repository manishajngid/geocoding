/**
 * 
 */
package com.Deutsche.geocode.latlang.google;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.Deutsche.geocode.GeocodeApplication;
import com.Deutsche.geocode.exceptions.ServiceInvocationException;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * @author jangid_m
 *
 */
@Component("googleLatLang")
public class GoogleLatLang {
	private transient static final Logger LOG = Logger.getLogger(GeocodeApplication.class);
	GeoApiContext context = null;
	private String serviceName = "GoogleLatLang";
	@Value("${google.api.key}")
	private String apiKey;
	@PostConstruct
	public void init(){
		LOG.info("Initializing GoogleLatLang bean");
		context = new GeoApiContext().setApiKey(apiKey);
	}
	
	public double[] getResult(int postCode) throws Exception {
		double[] latlang = new double[2];
		GeocodingResult[] results =  GeocodingApi.geocode(context,String.valueOf(postCode)).await();
		if(null != results && results.length !=0){
			LatLng location = results[0].geometry.location;
			latlang[0] = location.lat;
			latlang[1] = location.lng;
		}else{
			new ServiceInvocationException(serviceName);
		}
		LOG.info(results[0].formattedAddress + "  latitude == " + latlang[0] + "  longitude == " + latlang[1] );
		return latlang;
	}

	public String getServiceName() {
		// TODO Auto-generated method stub
		return null;
	}

}

/**
 * 
 */
package com.Deutsche.geocode.latlang;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Deutsche.geocode.exceptions.ServiceInvocationException;
import com.Deutsche.geocode.latlang.google.GoogleLatLang;


/**(non-Javadoc)
 *  @see com.Deutsche.geocode.latlang.ILatLang
 * @author jangid_m
 *
 */
@Component("latlangtest")
public class LatLangTest implements ILatLang {
	private String serviceName = "TestService";
	
	private HashMap<Integer,double[]> postCodeCoordinatesMap= new HashMap<Integer,double[]>();
	@PostConstruct
	public void init(){
		postCodeCoordinatesMap.put(342001, new double[]{5,5});
		postCodeCoordinatesMap.put(411045, new double[]{4,4});
		postCodeCoordinatesMap.put(411021, new double[]{3,3});
		postCodeCoordinatesMap.put(342002, new double[]{2,2});
	}
	

	/* (non-Javadoc)
	 * @see com.Deutsche.geocode.latlang.ILatLang#getLatlangFromPostCode(int)
	 */
	public double[] getLatlangFromPostCode(int postCode, Boolean status) {
		 double[] latlang;
		 System.out.println("in test returning mock data");
		 try {
			 latlang = postCodeCoordinatesMap.get(postCode);
		} catch (Exception e) {
			throw new ServiceInvocationException(serviceName);
		}
		return latlang;
	}

}

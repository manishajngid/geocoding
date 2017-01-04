package com.Deutsche.geocode.controller;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON annotated POJO to capture customer position data Used in /getNearestShop
 * service
 * 
 * @author jangid_m
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerPositionReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("latitiude")
	private double latitiude;

	@JsonProperty("longitude")
	private double longitude;

	public double getLatitiude() {
		return latitiude;
	}

	public void setLatitiude(double latitiude) {
		this.latitiude = latitiude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "CustomerPosition [latitiude=" + latitiude + ", longitude="
				+ longitude + "]";
	}

}

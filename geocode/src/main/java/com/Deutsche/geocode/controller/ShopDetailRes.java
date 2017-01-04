package com.Deutsche.geocode.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON annotated POJO to for response Used in addShopDetails service
 * 
 * @author jangid_m
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopDetailRes {
	@JsonProperty("Message")
	private String msg;

	
	public ShopDetailRes(String msg) {
		super();
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ShopDetailRes [msg=" + msg + "]";
	}

	
}

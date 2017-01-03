/**
 * 
 */
package com.Deutsche.geocode.controller;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON annotated POJO to capture ShopAddress
 * 
 * @author jangid_m
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopAddress implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("number")

	private int number;

	@JsonProperty("postCode")

	private int postCode;

	public int getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return "ShopAddress [number=" + number + ", postCode=" + postCode + "]";
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}
}

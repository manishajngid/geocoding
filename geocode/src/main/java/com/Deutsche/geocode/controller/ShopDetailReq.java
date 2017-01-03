/**
 * 
 */
package com.Deutsche.geocode.controller;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON annotated POJO to capture shop details data Used in /addshopdetails
 * service
 * 
 * @author jangid_m
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopDetailReq implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("shopName")
	private String name;
	@JsonProperty("shopAddress")
	private ShopAddress shopAddress;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ShopAddress getShopAddress() {
		return shopAddress;
	}

	@Override
	public String toString() {
		return "ShopDetail [name=" + name + ", shopAddress=" + shopAddress + "]";
	}

	public void setShopAddress(ShopAddress shopAddress) {
		this.shopAddress = shopAddress;
	}
}

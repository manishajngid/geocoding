/**
 * 
 */
package com.Deutsche.geocode.service;

import java.util.List;

import com.Deutsche.geocode.model.ShopDetails;

/**
 * @author jangid_m
 *
 */
public interface IGeoControllerService {
	public boolean addShopDetail(String shopName, int shopNum, int postcode);

	public List<ShopDetails> getallShopData();
}

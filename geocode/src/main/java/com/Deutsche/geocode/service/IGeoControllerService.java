/**
 * 
 */
package com.Deutsche.geocode.service;

import java.util.List;

import com.Deutsche.geocode.controller.CustomerPositionReq;
import com.Deutsche.geocode.model.ShopDetails;

/**
 * Geo controller service interface
 * 
 * @author jangid_m
 * 
 */
public interface IGeoControllerService {
	/**
	 * Gets the latitude , longitude data from external service and stores shop
	 * details along with latitude,longitude date in to in memory list. This
	 * list is being used by other service to find out nearest shop for given
	 * coordinates
	 * 
	 * @param shopName
	 * @param shopNum
	 * @param postcode
	 * @return boolean status for staus success true fail false
	 */
	public boolean addShopDetail(String shopName, int shopNum, int postcode);

	/**
	 * takes coordinates in form of latitude and longitude and returns nearest
	 * shop with respect to given input. throws NoRegisteredShopException if
	 * shop list is empty finds out distance from customer position to each of
	 * registered shop position and returns shop details with minimum distance
	 * 
	 * @param customerPosition
	 * @return
	 */
	public ShopDetails getNearestShop(CustomerPositionReq customerPosition);

	/**
	 * returns all registered shops list
	 * 
	 * @return
	 */
	public List<ShopDetails> getAllShopData();
}
